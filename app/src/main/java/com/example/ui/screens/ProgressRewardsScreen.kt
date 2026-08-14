package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.models.CategoryStatEntity
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.components.TapStarBurstOverlay
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

private data class BadgeItem(
  val id: String,
  val title: String,
  val desc: String,
  val unlocked: Boolean,
  val icon: String
)

private data class CategoryProgressItem(
  val id: String,
  val title: String,
  val iconEmoji: String,
  val cardColor: Color,
  val targetScreen: Screen,
  val starsEarned: Int,
  val completedCount: Int,
  val starRating: Int // 1 to 5
)

@Composable
fun ProgressRewardsScreen(
  viewModel: MainViewModel,
  audioController: AudioController,
  onHomeClick: () -> Unit,
  onNavigate: (Screen) -> Unit,
  onParentLockClick: () -> Unit
) {
  val userProgress by viewModel.userProgress.collectAsState()
  val badges by viewModel.badges.collectAsState()
  val learnedWords by viewModel.learnedWords.collectAsState()
  val categoryStats by viewModel.categoryStats.collectAsState()

  // Tab State: 0 = Learning Progress Dashboard, 1 = Badges & Achievements
  var selectedTab by remember { mutableIntStateOf(0) }
  var milestoneBurstKey by remember { androidx.compose.runtime.mutableStateOf(0) }
  var showMilestoneCelebration by remember { androidx.compose.runtime.mutableStateOf(false) }
  var celebrationMessage by remember { androidx.compose.runtime.mutableStateOf("Milestone Achieved!") }

  val totalStars = userProgress.totalStars

  // Map category stats to display items
  val categoryItems = remember(categoryStats) {
    val rawCategories = listOf(
      Triple("animals", "Animals & Pets", "🦁") to (Color(0xFFFB8C00) to Screen.ANIMALS_EXPLORER),
      Triple("colors", "Colors & Art", "🎨") to (Color(0xFFE53935) to Screen.COLORS_LESSON),
      Triple("shapes", "Fun Shapes", "🔺") to (Color(0xFF3949AB) to Screen.SHAPES_LESSON),
      Triple("fruits", "Fruits & Veggies", "🍎") to (Color(0xFF43A047) to Screen.FRUITS_VEG_LESSON),
      Triple("numbers", "123 Numbers", "🔢") to (Color(0xFF8E24AA) to Screen.NUMBERS_LESSON),
      Triple("vocab", "Word Cards", "🎴") to (Color(0xFF7E57C2) to Screen.VOCABULARY_CARDS),
      Triple("alphabet", "ABC Alphabet", "🔤") to (Color(0xFF009688) to Screen.ALPHABET_LESSON),
      Triple("body_parts", "Body Parts", "👁️") to (Color(0xFFD81B60) to Screen.BODY_PARTS_LESSON)
    )

    rawCategories.map { (info, styling) ->
      val (id, title, emoji) = info
      val (color, screen) = styling
      val stat = categoryStats.find { it.categoryId.equals(id, ignoreCase = true) }
      val stars = stat?.starsEarned ?: 0
      val count = stat?.completedCount ?: 0

      val rating = when {
        count >= 10 || stars >= 25 -> 5
        count >= 6 || stars >= 15 -> 4
        count >= 3 || stars >= 8 -> 3
        count >= 1 || stars >= 2 -> 2
        else -> 1
      }

      CategoryProgressItem(
        id = id,
        title = title,
        iconEmoji = emoji,
        cardColor = color,
        targetScreen = screen,
        starsEarned = stars,
        completedCount = count,
        starRating = rating
      )
    }.sortedByDescending { it.completedCount * 10 + it.starsEarned }
  }

  // Find most practiced category
  val mostPracticedCategory = remember(categoryItems) {
    categoryItems.firstOrNull { it.completedCount > 0 || it.starsEarned > 0 } ?: categoryItems.firstOrNull()
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "📊 Learning Progress",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFFFF8E1)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFF8E1),
              Color(0xFFFFF3E0),
              Color(0xFFE8F5E9)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        // Back Header & Title
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 3.dp,
            modifier = Modifier.clickable { onHomeClick() }
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFFFF8F00),
              modifier = Modifier.padding(10.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = "Child Learning Progress Dashboard 📈",
              fontSize = 17.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF4E342E)
            )
            Text(
              text = "Track star ratings & practice milestones per category!",
              fontSize = 11.sp,
              color = Color(0xFF6D4C41)
            )
          }
        }

        // Segmented Tab Selector
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(4.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (selectedTab == 0) Color(0xFFFF8F00) else Color.Transparent,
            modifier = Modifier
              .weight(1f)
              .clickable {
                selectedTab = 0
                audioController.speak("Learning Progress Dashboard")
              }
              .testTag("tab_learning_progress")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                Icons.Default.Leaderboard,
                contentDescription = null,
                tint = if (selectedTab == 0) Color.White else Color.Gray,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Progress",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (selectedTab == 0) Color.White else Color.Gray
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (selectedTab == 1) Color(0xFF8E24AA) else Color.Transparent,
            modifier = Modifier
              .weight(1f)
              .clickable {
                audioController.speak("Opening Badge Collection!")
                onNavigate(Screen.BADGE_COLLECTION)
              }
              .testTag("tab_badges")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = if (selectedTab == 1) Color.White else Color.Gray,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Badges (${badges.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (selectedTab == 1) Color.White else Color.Gray
              )
            }
          }
        }

        if (selectedTab == 0) {
          // ================= LEARNING PROGRESS DASHBOARD =================
          LazyColumn(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
          ) {
            // Daily Streak Banner Showcase
            item {
              val streak = userProgress.learningStreak
              val nextStreakGoal = when {
                streak < 3 -> 3
                streak < 7 -> 7
                streak < 14 -> 14
                else -> 30
              }
              val streakProgressFraction = (streak.toFloat() / nextStreakGoal.toFloat()).coerceIn(0.1f, 1.0f)

              Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFF9800)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable {
                    milestoneBurstKey++
                    celebrationMessage = "$streak-Day Daily Learning Streak! 🔥"
                    showMilestoneCelebration = true
                    audioController.speak("Awesome! You have a $streak day learning streak! Keep practicing vocabulary every day!")
                  }
                  .testTag("daily_streak_card")
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Surface(
                        shape = CircleShape,
                        color = Color(0xFFFF6D00),
                        modifier = Modifier.size(44.dp)
                      ) {
                        Box(contentAlignment = Alignment.Center) {
                          Text("🔥", fontSize = 24.sp)
                        }
                      }
                      Spacer(modifier = Modifier.width(12.dp))
                      Column {
                        Text(
                          text = "$streak-Day Daily Streak! 🔥",
                          fontSize = 17.sp,
                          fontWeight = FontWeight.Black,
                          color = Color(0xFFE65100)
                        )
                        Text(
                          text = "Consecutive days practicing vocabulary",
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Medium,
                          color = Color(0xFFBF360C)
                        )
                      }
                    }

                    Surface(
                      shape = RoundedCornerShape(16.dp),
                      color = Color(0xFFFFE0B2)
                    ) {
                      Text(
                        text = "$streak / $nextStreakGoal Days",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFE65100),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(10.dp))

                  LinearProgressIndicator(
                    progress = { streakProgressFraction },
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(8.dp)
                      .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFFF6D00),
                    trackColor = Color(0xFFFFCC80)
                  )
                }
              }
            }

            // Overall Stats Summary Box
            item {
              Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceAround,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⭐ Total Stars", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                    Text(
                      "$totalStars",
                      fontSize = 22.sp,
                      fontWeight = FontWeight.Black,
                      color = Color(0xFFFF8F00)
                    )
                  }

                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📚 Words Learned", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                    Text(
                      "${learnedWords.size}",
                      fontSize = 22.sp,
                      fontWeight = FontWeight.Black,
                      color = Color(0xFF43A047)
                    )
                  }

                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔥 Daily Streak", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                    Text(
                      "${userProgress.learningStreak} Days",
                      fontSize = 22.sp,
                      fontWeight = FontWeight.Black,
                      color = Color(0xFFFF6D00)
                    )
                  }
                }
              }
            }

            // Most Practiced Banner Showcase
            mostPracticedCategory?.let { topCat ->
              item {
                Surface(
                  shape = RoundedCornerShape(24.dp),
                  color = Color(0xFFFFF3E0),
                  border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFB74D)),
                  shadowElevation = 3.dp,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    modifier = Modifier
                      .padding(16.dp)
                      .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Column(modifier = Modifier.weight(1f)) {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                          shape = RoundedCornerShape(12.dp),
                          color = Color(0xFFFF8F00)
                        ) {
                          Text(
                            text = "👑 MOST PRACTICED",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                          )
                        }
                      }

                      Spacer(modifier = Modifier.height(6.dp))

                      Text(
                        text = "${topCat.iconEmoji} ${topCat.title}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFE65100)
                      )

                      Spacer(modifier = Modifier.height(4.dp))

                      // Star Rating Bar
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { starIdx ->
                          Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (starIdx < topCat.starRating) Color(0xFFFFB300) else Color(0xFFE0E0E0),
                            modifier = Modifier.size(18.dp)
                          )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                          text = "${topCat.completedCount} Sessions • ${topCat.starsEarned} Stars",
                          fontSize = 12.sp,
                          fontWeight = FontWeight.Bold,
                          color = Color(0xFFBF360C)
                        )
                      }
                    }

                    BoboMascot(
                      size = 56.dp,
                      state = MascotState.CELEBRATING,
                      modifier = Modifier.clickable {
                        milestoneBurstKey++
                        celebrationMessage = "Most Practiced: ${topCat.title}!"
                        showMilestoneCelebration = true
                        audioController.speak("Awesome job! ${topCat.title} is your most practiced category!")
                      }
                    )
                  }
                }
              }
            }

            // Category Rating Breakdown Title
            item {
              Text(
                text = "VOCABULARY CATEGORY RATINGS ⭐",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF5D4037),
                modifier = Modifier.padding(top = 4.dp)
              )
            }

            // Category Progress Cards List
            items(categoryItems, key = { it.id }) { item ->
              CategoryProgressCard(
                item = item,
                audioController = audioController,
                onPracticeClick = {
                  audioController.speak("Let me take you to ${item.title}!")
                  onNavigate(item.targetScreen)
                }
              )
            }

            // Parent Learning Insight Recommendation
            item {
              val recommended = categoryItems.minByOrNull { it.completedCount }
              Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 8.dp)
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Text(
                    text = "💡 Parent Learning Tip",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = if (recommended != null) {
                      "Try practicing '${recommended.title}' next (${recommended.iconEmoji}) to unlock more stars and balance vocabulary expansion!"
                    } else {
                      "Great consistency! Keep practicing across all categories to reinforce language retention."
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF1B5E20)
                  )
                }
              }
            }
          }
        } else {
          // ================= BADGES & ACHIEVEMENTS TAB =================
          val allBadges = remember(totalStars, badges, userProgress.learningStreak) {
            val streak = userProgress.learningStreak
            listOf(
              Triple("streak_3", "3-Day Flame", "Practiced 3 days in a row • 🔥"),
              Triple("streak_7", "7-Day Super", "Practiced 7 days in a row • ⚡"),
              Triple("streak_14", "Streak Master", "Practiced 14 consecutive days • 👑"),
              Triple("star_10", "Star Starter", "Earn 10 Stars • ⭐"),
              Triple("star_50", "Star Collector", "Earn 50 Stars • 🌟"),
              Triple("star_100", "English Champ", "Earn 100 Stars • 🏆"),
              Triple("abc_master", "ABC Master", "Learn Alphabets • 🔤"),
              Triple("num_hero", "Number Hero", "Count 1-10 • 🔢"),
              Triple("animal_buddy", "Animal Buddy", "Explore Pets & Wild • 🐶")
            ).map { (id, title, desc) ->
              val unlocked = badges.any { it.badgeId == id } ||
                  (id == "star_10" && totalStars >= 10) ||
                  (id == "star_50" && totalStars >= 50) ||
                  (id == "star_100" && totalStars >= 100) ||
                  (id == "streak_3" && streak >= 3) ||
                  (id == "streak_7" && streak >= 7) ||
                  (id == "streak_14" && streak >= 14)
              val icon = when (id) {
                "streak_3" -> "🔥"
                "streak_7" -> "⚡"
                "streak_14" -> "👑"
                "star_10" -> "⭐"
                "star_50" -> "🌟"
                "star_100" -> "🏆"
                "abc_master" -> "🔤"
                "num_hero" -> "🔢"
                else -> "🐶"
              }
              BadgeItem(id, title, desc, unlocked, icon)
            }
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "UNLOCKED BADGES & MILESTONES 🎖️",
              fontSize = 13.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFF5D4037),
              modifier = Modifier.padding(vertical = 4.dp)
            )

            LazyVerticalGrid(
              columns = GridCells.Fixed(3),
              horizontalArrangement = Arrangement.spacedBy(12.dp),
              verticalArrangement = Arrangement.spacedBy(12.dp),
              modifier = Modifier.weight(1f)
            ) {
              items(allBadges) { b ->
                Card(
                  shape = RoundedCornerShape(20.dp),
                  colors = CardDefaults.cardColors(
                    containerColor = if (b.unlocked) Color.White else Color(0xFFE0E0E0)
                  ),
                  elevation = CardDefaults.cardElevation(
                    defaultElevation = if (b.unlocked) 4.dp else 0.dp
                  ),
                  modifier = Modifier
                    .alpha(if (b.unlocked) 1.0f else 0.5f)
                    .clickable {
                      milestoneBurstKey++
                      if (b.unlocked) {
                        celebrationMessage = "Unlocked Badge: ${b.title}! 🏆"
                        showMilestoneCelebration = true
                        audioController.speak("${b.title}! ${b.desc}")
                      } else {
                        audioController.speak("Locked badge! Earn more stars to unlock ${b.title}!")
                      }
                    }
                    .testTag("badge_card_${b.id}")
                ) {
                  Column(
                    modifier = Modifier
                      .padding(12.dp)
                      .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                    Text(
                      text = if (b.unlocked) b.icon else "🔒",
                      fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                      text = b.title,
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold,
                      textAlign = TextAlign.Center,
                      color = Color(0xFF37474F)
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  milestoneBurstKey++
                  celebrationMessage = "Badge Collection Milestone!"
                  showMilestoneCelebration = true
                  audioController.speak("Keep practicing to collect all 6 badges!")
                },
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              BoboMascot(
                size = 60.dp,
                state = MascotState.CELEBRATING
              )
            }
          }
        }
      }

      // Particle Star Burst Overlay
      TapStarBurstOverlay(triggerKey = milestoneBurstKey)

      // Particle Confetti Celebration Overlay
      CelebrationOverlay(
        visible = showMilestoneCelebration,
        message = celebrationMessage,
        starsAwarded = 5,
        onDismiss = { showMilestoneCelebration = false }
      )
    }
  }
}

@Composable
private fun CategoryProgressCard(
  item: CategoryProgressItem,
  audioController: AudioController,
  onPracticeClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("category_progress_card_${item.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Icon & Category Title
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(item.cardColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Text(text = item.iconEmoji, fontSize = 24.sp)
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = item.title,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF212121)
            )

            // Star Rating (1 to 5 Stars)
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(top = 2.dp)
            ) {
              repeat(5) { index ->
                Icon(
                  imageVector = Icons.Default.Star,
                  contentDescription = null,
                  tint = if (index < item.starRating) Color(0xFFFFB300) else Color(0xFFE0E0E0),
                  modifier = Modifier.size(16.dp)
                )
              }
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = when (item.starRating) {
                  5 -> "Mastery ⭐"
                  4 -> "Super ⭐"
                  3 -> "Good ⭐"
                  2 -> "Learner ⭐"
                  else -> "Starter ⭐"
                },
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = item.cardColor
              )
            }
          }
        }

        // Practice Action Button
        Button(
          onClick = onPracticeClick,
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = item.cardColor,
            contentColor = Color.White
          ),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.testTag("practice_now_btn_${item.id}")
        ) {
          Text("Practice", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.width(4.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Practice Stats Row & Progress Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Practiced: ${item.completedCount} times",
          fontSize = 11.sp,
          color = Color.Gray
        )

        Text(
          text = "Earned: ${item.starsEarned} Stars",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFFFF8F00)
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Linear Progress Bar for category mastery
      val progressFraction = (item.completedCount * 0.1f + item.starsEarned * 0.03f).coerceIn(0.08f, 1.0f)
      LinearProgressIndicator(
        progress = { progressFraction },
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp)),
        color = item.cardColor,
        trackColor = item.cardColor.copy(alpha = 0.15f)
      )
    }
  }
}
