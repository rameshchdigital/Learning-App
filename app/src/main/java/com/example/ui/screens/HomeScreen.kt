package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.CategoryGroup
import com.example.data.models.DifficultyLevel
import com.example.data.models.GameCategory
import com.example.ui.components.BoboMascot
import com.example.ui.components.LittleBuddyBottomBar
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
  totalStars: Int,
  learningStreak: Int = 0,
  currentDifficulty: DifficultyLevel = DifficultyLevel.MEDIUM,
  onCategoryClick: (GameCategory) -> Unit,
  onDifficultyClick: (DifficultyLevel) -> Unit = {},
  onStartDailyQuiz: () -> Unit = {},
  onNavigate: (Screen) -> Unit = {},
  onParentLockClick: () -> Unit
) {
  val featuredCategories = listOf(
    GameCategory.ALPHABETS,
    GameCategory.PHONICS,
    GameCategory.NUMBERS,
    GameCategory.COLORS,
    GameCategory.SHAPES,
    GameCategory.ANIMALS,
    GameCategory.FRUITS_VEG,
    GameCategory.BODY_PARTS,
    GameCategory.FEELINGS,
    GameCategory.SENTENCES,
    GameCategory.SPELLING,
    GameCategory.MUSIC
  )

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        streak = learningStreak,
        title = "🐘 Little English Buddy",
        onParentLockClick = onParentLockClick
      )
    },
    bottomBar = {
      LittleBuddyBottomBar(
        currentScreen = Screen.HOME,
        onNavigate = onNavigate
      )
    },
    containerColor = Color(0xFFFFFDE7)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE3F2FD),
              Color(0xFFFFFDE7),
              Color(0xFFF1F8E9)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(bottom = 16.dp)
      ) {
        // Mascot Greeting Banner
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          shape = RoundedCornerShape(24.dp),
          color = Color.White,
          shadowElevation = 3.dp
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            BoboMascot(
              size = 72.dp,
              state = MascotState.IDLE_WAVING
            )

            Column(modifier = Modifier.padding(start = 12.dp)) {
              Text(
                text = "Hello Little Buddy! 👋",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1E88E5)
              )
              Text(
                text = "Stage: ${currentDifficulty.title} (${currentDifficulty.ageRange})",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF78909C)
              )
            }
          }
        }

        // Daily Vocabulary Habit Nudge Banner
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onNavigate(Screen.VOCABULARY_CARDS) }
            .testTag("home_daily_habit_banner"),
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFFFF3E0),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFB74D)),
          shadowElevation = 2.dp
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("🔔", fontSize = 24.sp)
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Daily Practice Habit! 🔥",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFE65100)
                )
                Text(
                  text = "Practice vocabulary cards today to keep your streak active!",
                  fontSize = 11.sp,
                  color = Color(0xFFBF360C)
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(14.dp),
              color = Color(0xFFFF9800)
            ) {
              Text(
                text = "Practice 🚀",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }

        // Today's Lesson Featured Card
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          shape = RoundedCornerShape(28.dp),
          color = Color(0xFFFFF3E0),
          shadowElevation = 4.dp
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "🌟 TODAY'S LESSON",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Black,
                  color = Color(0xFFE65100)
                )
                Text(
                  text = "Learn 5 New Words!",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFBF360C)
                )
              }
              Text("🍎 🐱 ⚽", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
              progress = { 0.4f },
              modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(CircleShape),
              color = Color(0xFFFF9800),
              trackColor = Color(0xFFFFE0B2)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
              onClick = { onCategoryClick(GameCategory.ALPHABETS) },
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("start_today_lesson"),
              shape = RoundedCornerShape(24.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9800),
                contentColor = Color.White
              )
            ) {
              Text("START TODAY'S LESSON 🚀", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
          }
        }

        // Vocabulary Cards Feature Card
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          shape = RoundedCornerShape(28.dp),
          color = Color(0xFFF3E5F5),
          shadowElevation = 4.dp
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onNavigate(Screen.VOCABULARY_CARDS) }
              .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "🎴 NEW FEATURE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF7E57C2)
              )
              Text(
                text = "Vocabulary Cards 🎨",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A148C)
              )
              Text(
                text = "Explore cheerful cards for colors, animals, shapes & more!",
                fontSize = 12.sp,
                color = Color(0xFF6A1B9A)
              )
            }

            Text("🦁🎨📐", fontSize = 28.sp, modifier = Modifier.padding(start = 8.dp))
          }
        }

        // Developmental Stage Switcher Row
        Text(
          text = "Select Age Stage",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF546E7A),
          modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 6.dp)
        )

        LazyRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
          items(DifficultyLevel.entries) { diff ->
            val isSelected = diff == currentDifficulty
            Surface(
              shape = RoundedCornerShape(18.dp),
              color = if (isSelected) Color(0xFF1E88E5) else Color.White,
              shadowElevation = if (isSelected) 3.dp else 1.dp,
              modifier = Modifier
                .testTag("home_difficulty_${diff.id}")
                .clickable { onDifficultyClick(diff) }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(diff.iconEmoji, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "${diff.title} (${diff.ageRange})",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = if (isSelected) Color.White else Color(0xFF37474F)
                )
              }
            }
          }
        }

        // Daily Quiz Showcase Banner
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable {
              onStartDailyQuiz()
              onNavigate(Screen.DAILY_QUIZ)
            }
            .testTag("home_daily_quiz_banner"),
          shape = RoundedCornerShape(24.dp),
          color = Color(0xFFFFF3E0),
          border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFB74D)),
          shadowElevation = 3.dp
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("📝", fontSize = 32.sp)
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "Daily Quiz 🎯",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFFE65100)
                )
                Text(
                  text = "5 random questions from your practiced categories! Earn bonus stars!",
                  fontSize = 11.sp,
                  color = Color(0xFFF57C00)
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(16.dp),
              color = Color(0xFFFF8F00)
            ) {
              Text(
                text = "Start Quiz 🚀",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }

        // Visual Badge Collection Showcase Banner
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onNavigate(Screen.BADGE_COLLECTION) }
            .testTag("home_badge_collection_banner"),
          shape = RoundedCornerShape(24.dp),
          color = Color(0xFFEDE7F6),
          border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFB388FF)),
          shadowElevation = 3.dp
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("🏆", fontSize = 32.sp)
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "Visual Badge Collection 🎖️",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF4A148C)
                )
                Text(
                  text = "Unlock rewards for animal, color & star milestones!",
                  fontSize = 11.sp,
                  color = Color(0xFF6A1B9A)
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(16.dp),
              color = Color(0xFF7E57C2)
            ) {
              Text(
                text = "View Badges 🌟",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categories grouped by Learning Group
        CategoryGroup.entries.forEach { group ->
          val groupCategories = GameCategory.entries.filter { it.group == group }
          if (groupCategories.isNotEmpty()) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 20.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = group.iconEmoji,
                  fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = group.title,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(group.colorHex)
                )
              }

              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
              ) {
                groupCategories.chunked(2).forEach { rowList ->
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                  ) {
                    rowList.forEach { cat ->
                      Box(modifier = Modifier.weight(1f)) {
                        CategoryCardItem(
                          category = cat,
                          onClick = { onCategoryClick(cat) }
                        )
                      }
                    }
                    if (rowList.size == 1) {
                      Spacer(modifier = Modifier.weight(1f))
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun CategoryCardItem(
  category: GameCategory,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    shape = RoundedCornerShape(26.dp),
    color = Color(category.bgHex),
    shadowElevation = 4.dp,
    modifier = Modifier
      .scale(scaleAnim.value)
      .fillMaxWidth()
      .aspectRatio(1.15f)
      .testTag("category_card_${category.id}")
      .clickable {
        scope.launch {
          scaleAnim.animateTo(0.92f, spring(dampingRatio = 0.4f))
          scaleAnim.animateTo(1.0f, spring(dampingRatio = 0.6f))
          onClick()
        }
      }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(56.dp)
          .clip(CircleShape)
          .background(Color.White.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = category.iconEmoji,
          fontSize = 32.sp
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = category.title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color(category.colorHex),
        textAlign = TextAlign.Center,
        maxLines = 1
      )

      Text(
        text = category.subTitle,
        fontSize = 10.sp,
        color = Color(0xFF607D8B),
        textAlign = TextAlign.Center,
        maxLines = 1
      )
    }
  }
}

