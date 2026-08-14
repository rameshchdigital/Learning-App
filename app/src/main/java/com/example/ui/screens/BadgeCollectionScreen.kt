package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.AudioController
import com.example.data.models.BadgeEntity
import com.example.data.models.CategoryStatEntity
import com.example.data.models.UserProgressEntity
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.components.TapStarBurstOverlay
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

enum class BadgeCategoryFilter(val label: String, val icon: String) {
  ALL("All", "🌟"),
  CATEGORIES("Categories", "🦁"),
  STREAKS_STARS("Streaks & Stars", "🔥"),
  UNLOCKED("Unlocked", "🏆"),
  LOCKED("Locked", "🔒")
}

data class BadgeDefinition(
  val id: String,
  val title: String,
  val desc: String,
  val iconEmoji: String,
  val filterCategory: BadgeCategoryFilter,
  val targetScreen: Screen?,
  val goalText: String,
  val checkUnlocked: (UserProgressEntity, List<CategoryStatEntity>, List<BadgeEntity>, Int) -> Boolean,
  val calculateProgress: (UserProgressEntity, List<CategoryStatEntity>, Int) -> Pair<Float, String>
)

@Composable
fun BadgeCollectionScreen(
  viewModel: MainViewModel,
  audioController: AudioController,
  onBackClick: () -> Unit,
  onNavigate: (Screen) -> Unit,
  onParentLockClick: () -> Unit
) {
  val userProgress by viewModel.userProgress.collectAsState()
  val unlockedBadges by viewModel.badges.collectAsState()
  val categoryStats by viewModel.categoryStats.collectAsState()
  val learnedWords by viewModel.learnedWords.collectAsState()

  var activeFilter by remember { mutableStateOf(BadgeCategoryFilter.ALL) }
  var selectedBadgeForDialog by remember { mutableStateOf<BadgeDefinition?>(null) }
  var isSelectedBadgeUnlocked by remember { mutableStateOf(false) }

  var burstKey by remember { mutableIntStateOf(0) }
  var showCelebration by remember { mutableStateOf(false) }
  var celebrationMsg by remember { mutableStateOf("Milestone Achieved!") }

  val totalStars = userProgress.totalStars
  val learnedWordCount = learnedWords.size

  // Master definitions of all visual badges in the app
  val badgeDefinitions = remember {
    listOf(
      BadgeDefinition(
        id = "badge_animals",
        title = "Animal Safari",
        desc = "Explored cute pets, farm animals & wild safari friends!",
        iconEmoji = "🦁",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.ANIMALS_EXPLORER,
        goalText = "Complete 1 Animal learning activity",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_animals" } ||
              stats.any { it.categoryId.equals("animals", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("animals", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_colors",
        title = "Rainbow Master",
        desc = "Identified bright red, blue, green, yellow and rainbow shades!",
        iconEmoji = "🎨",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.COLORS_LESSON,
        goalText = "Complete 1 Color learning activity",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_colors" } ||
              stats.any { it.categoryId.equals("colors", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("colors", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_shapes",
        title = "Shape Wizard",
        desc = "Mastered circles, squares, triangles, stars and hearts!",
        iconEmoji = "🔺",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.SHAPES_LESSON,
        goalText = "Complete 1 Shape learning activity",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_shapes" } ||
              stats.any { it.categoryId.equals("shapes", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("shapes", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_alphabets",
        title = "ABC Legend",
        desc = "Learned English letters A to Z with phonic pronunciation!",
        iconEmoji = "🔤",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.ALPHABET_LESSON,
        goalText = "Complete 1 Alphabet lesson",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_alphabets" } ||
              stats.any { it.categoryId.equals("alphabets", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("alphabets", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_numbers",
        title = "Math Marvel",
        desc = "Counted numbers 1 to 20 with friendly objects and balloons!",
        iconEmoji = "🔢",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.NUMBERS_LESSON,
        goalText = "Complete 1 Number counting lesson",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_numbers" } ||
              stats.any { it.categoryId.equals("numbers", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("numbers", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_phonics",
        title = "Phonics Pioneer",
        desc = "Blended letter sounds and pronounced early English words!",
        iconEmoji = "🔠",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.PHONICS_LESSON,
        goalText = "Complete 1 Phonics sound activity",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_phonics" } ||
              stats.any { it.categoryId.equals("phonics", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("phonics", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_fruits",
        title = "Healthy Eater",
        desc = "Discovered tasty apples, bananas, berries and veggies!",
        iconEmoji = "🍎",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.FRUITS_VEG_LESSON,
        goalText = "Complete 1 Fruit & Veggie lesson",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_fruits" } ||
              stats.any { (it.categoryId.equals("fruits_veg", ignoreCase = true) || it.categoryId.equals("fruits", ignoreCase = true)) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("fruits_veg", ignoreCase = true) || it.categoryId.equals("fruits", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_body",
        title = "Body Explorer",
        desc = "Identified eyes, ears, nose, hands, feet and smile!",
        iconEmoji = "👁️",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.BODY_PARTS_LESSON,
        goalText = "Complete 1 Body Parts activity",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_body" } ||
              stats.any { it.categoryId.equals("body_parts", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("body_parts", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_sentences",
        title = "Storyteller",
        desc = "Read simple 3-word sentences aloud with Bobo!",
        iconEmoji = "🗣️",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.SENTENCE_LEARNING,
        goalText = "Complete 1 Sentence practice lesson",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_sentences" } ||
              stats.any { it.categoryId.equals("sentences", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("sentences", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_spelling",
        title = "Spelling Bee",
        desc = "Arranged scrambled letter blocks into correct words!",
        iconEmoji = "✏️",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.SPELLING_LESSON,
        goalText = "Complete 1 Spelling puzzle",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_spelling" } ||
              stats.any { it.categoryId.equals("spelling", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("spelling", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Completed"
        }
      ),
      BadgeDefinition(
        id = "badge_music",
        title = "Maestro Bobo",
        desc = "Played playful piano, drums, flutes and xylophone keys!",
        iconEmoji = "🎵",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.MUSIC_PLAYGROUND,
        goalText = "Play musical instruments",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_music" } ||
              stats.any { it.categoryId.equals("music", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("music", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Played"
        }
      ),
      BadgeDefinition(
        id = "badge_puzzles",
        title = "Puzzle Genius",
        desc = "Fit geometric and animal picture puzzle tiles together!",
        iconEmoji = "🧩",
        filterCategory = BadgeCategoryFilter.CATEGORIES,
        targetScreen = Screen.PUZZLE_PLAYGROUND,
        goalText = "Complete 1 puzzle playground challenge",
        checkUnlocked = { _, stats, unlocked, _ ->
          unlocked.any { it.badgeId == "badge_puzzles" } ||
              stats.any { it.categoryId.equals("puzzles", ignoreCase = true) && (it.completedCount >= 1 || it.starsEarned >= 2) }
        },
        calculateProgress = { _, stats, _ ->
          val stat = stats.find { it.categoryId.equals("puzzles", ignoreCase = true) }
          val count = stat?.completedCount ?: 0
          val frac = (count / 1f).coerceAtMost(1f)
          frac to "$count / 1 Solved"
        }
      ),
      BadgeDefinition(
        id = "streak_3",
        title = "3-Day Flame",
        desc = "Maintained a 3-day consecutive learning streak!",
        iconEmoji = "🔥",
        filterCategory = BadgeCategoryFilter.STREAKS_STARS,
        targetScreen = Screen.HOME,
        goalText = "Practice 3 days in a row",
        checkUnlocked = { user, _, unlocked, _ ->
          unlocked.any { it.badgeId == "streak_3" } || user.learningStreak >= 3
        },
        calculateProgress = { user, _, _ ->
          val frac = (user.learningStreak / 3f).coerceAtMost(1f)
          frac to "${user.learningStreak} / 3 Days"
        }
      ),
      BadgeDefinition(
        id = "streak_7",
        title = "7-Day Super",
        desc = "Maintained a full 7-day weekly streak of practicing!",
        iconEmoji = "⚡",
        filterCategory = BadgeCategoryFilter.STREAKS_STARS,
        targetScreen = Screen.HOME,
        goalText = "Practice 7 days in a row",
        checkUnlocked = { user, _, unlocked, _ ->
          unlocked.any { it.badgeId == "streak_7" } || user.learningStreak >= 7
        },
        calculateProgress = { user, _, _ ->
          val frac = (user.learningStreak / 7f).coerceAtMost(1f)
          frac to "${user.learningStreak} / 7 Days"
        }
      ),
      BadgeDefinition(
        id = "streak_14",
        title = "Streak Master",
        desc = "Achieved 14 consecutive days of daily English learning!",
        iconEmoji = "👑",
        filterCategory = BadgeCategoryFilter.STREAKS_STARS,
        targetScreen = Screen.HOME,
        goalText = "Practice 14 days in a row",
        checkUnlocked = { user, _, unlocked, _ ->
          unlocked.any { it.badgeId == "streak_14" } || user.learningStreak >= 14
        },
        calculateProgress = { user, _, _ ->
          val frac = (user.learningStreak / 14f).coerceAtMost(1f)
          frac to "${user.learningStreak} / 14 Days"
        }
      ),
      BadgeDefinition(
        id = "star_10",
        title = "Star Starter",
        desc = "Collected 10 bright stars across learning games!",
        iconEmoji = "⭐",
        filterCategory = BadgeCategoryFilter.STREAKS_STARS,
        targetScreen = Screen.PLAY_GAMES,
        goalText = "Earn 10 Stars in games",
        checkUnlocked = { user, _, unlocked, _ ->
          unlocked.any { it.badgeId == "star_10" } || user.totalStars >= 10
        },
        calculateProgress = { user, _, _ ->
          val frac = (user.totalStars / 10f).coerceAtMost(1f)
          frac to "${user.totalStars} / 10 Stars"
        }
      ),
      BadgeDefinition(
        id = "star_50",
        title = "Star Collector",
        desc = "Gathered 50 shiny stars! A true English learning star!",
        iconEmoji = "🌟",
        filterCategory = BadgeCategoryFilter.STREAKS_STARS,
        targetScreen = Screen.PLAY_GAMES,
        goalText = "Earn 50 Stars in games",
        checkUnlocked = { user, _, unlocked, _ ->
          unlocked.any { it.badgeId == "star_50" } || user.totalStars >= 50
        },
        calculateProgress = { user, _, _ ->
          val frac = (user.totalStars / 50f).coerceAtMost(1f)
          frac to "${user.totalStars} / 50 Stars"
        }
      ),
      BadgeDefinition(
        id = "star_100",
        title = "English Champ",
        desc = "Earned 100 stars! Ultimate vocabulary champion!",
        iconEmoji = "🏆",
        filterCategory = BadgeCategoryFilter.STREAKS_STARS,
        targetScreen = Screen.PLAY_GAMES,
        goalText = "Earn 100 Stars in games",
        checkUnlocked = { user, _, unlocked, _ ->
          unlocked.any { it.badgeId == "star_100" } || user.totalStars >= 100
        },
        calculateProgress = { user, _, _ ->
          val frac = (user.totalStars / 100f).coerceAtMost(1f)
          frac to "${user.totalStars} / 100 Stars"
        }
      )
    )
  }

  // Count unlocked badges
  val unlockedCount = remember(badgeDefinitions, userProgress, categoryStats, unlockedBadges, learnedWordCount) {
    badgeDefinitions.count { badge ->
      badge.checkUnlocked(userProgress, categoryStats, unlockedBadges, learnedWordCount)
    }
  }

  // Filter badges according to user selection
  val filteredBadges = remember(badgeDefinitions, activeFilter, userProgress, categoryStats, unlockedBadges, learnedWordCount) {
    badgeDefinitions.filter { badge ->
      val isUnlocked = badge.checkUnlocked(userProgress, categoryStats, unlockedBadges, learnedWordCount)
      when (activeFilter) {
        BadgeCategoryFilter.ALL -> true
        BadgeCategoryFilter.CATEGORIES -> badge.filterCategory == BadgeCategoryFilter.CATEGORIES
        BadgeCategoryFilter.STREAKS_STARS -> badge.filterCategory == BadgeCategoryFilter.STREAKS_STARS
        BadgeCategoryFilter.UNLOCKED -> isUnlocked
        BadgeCategoryFilter.LOCKED -> !isUnlocked
      }
    }
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🏆 Badge Collection",
        onParentLockClick = onParentLockClick
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
              Color(0xFFFFFDE7),
              Color(0xFFFFF3E0),
              Color(0xFFEDE7F6)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        // Top Navigation Header
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
            modifier = Modifier
              .clickable { onBackClick() }
              .testTag("badge_back_btn")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFF7E57C2),
              modifier = Modifier.padding(10.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Visual Badge Collection 🏆",
              fontSize = 18.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF4A148C)
            )
            Text(
              text = "Earn shiny milestone badges as you learn & play!",
              fontSize = 11.sp,
              color = Color(0xFF6A1B9A)
            )
          }

          // Voice Audio Prompt Button
          Surface(
            shape = CircleShape,
            color = Color(0xFFEDE7F6),
            modifier = Modifier.clickable {
              audioController.speak("Badge Collection! You have unlocked $unlockedCount out of ${badgeDefinitions.size} badges! Tap any badge to see its story!")
            }
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Read Aloud",
              tint = Color(0xFF7E57C2),
              modifier = Modifier.padding(8.dp)
            )
          }
        }

        // Hero Milestone Progress Card Showcase
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(2.dp, Color(0xFFB388FF)),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              burstKey++
              celebrationMsg = "Collection Progress: $unlockedCount / ${badgeDefinitions.size} Badges!"
              showCelebration = true
              audioController.playBadgeUnlockSound()
              audioController.speak("Super work! Keep completing categories to collect all badges!")
            }
            .testTag("badge_summary_card")
        ) {
          Row(
            modifier = Modifier
              .padding(16.dp)
              .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            BoboMascot(
              size = 64.dp,
              state = MascotState.CELEBRATING
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Milestone Collection",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF4A148C)
                )

                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = Color(0xFF7E57C2)
                ) {
                  Text(
                    text = "$unlockedCount / ${badgeDefinitions.size} Badges",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              val totalProgressFraction = (unlockedCount.toFloat() / badgeDefinitions.size.toFloat()).coerceIn(0.05f, 1.0f)
              LinearProgressIndicator(
                progress = { totalProgressFraction },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(10.dp)
                  .clip(RoundedCornerShape(5.dp)),
                color = Color(0xFF7E57C2),
                trackColor = Color(0xFFEDE7F6)
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = if (unlockedCount == badgeDefinitions.size) {
                  "🎉 ALL BADGES UNLOCKED! You are an Master English Scholar!"
                } else {
                  "Tap badges below to view unlock stories & requirements!"
                },
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6A1B9A)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Chips Row
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          BadgeCategoryFilter.entries.forEach { filter ->
            val isSelected = activeFilter == filter
            FilterChip(
              selected = isSelected,
              onClick = {
                activeFilter = filter
                audioController.playClickSound()
              },
              label = {
                Text(
                  text = "${filter.icon} ${filter.label}",
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              },
              shape = RoundedCornerShape(16.dp),
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color(0xFF7E57C2),
                selectedLabelColor = Color.White,
                containerColor = Color.White,
                labelColor = Color(0xFF512DA8)
              ),
              border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isSelected,
                borderColor = Color(0xFFD1C4E9),
                selectedBorderColor = Color(0xFF7E57C2)
              ),
              modifier = Modifier.testTag("badge_filter_${filter.name.lowercase()}")
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Grid of Badges
        if (filteredBadges.isEmpty()) {
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "No badges match this filter yet! 🌟",
              fontSize = 14.sp,
              color = Color.Gray
            )
          }
        } else {
          LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 12.dp)
          ) {
            items(filteredBadges, key = { it.id }) { badge ->
              val isUnlocked = badge.checkUnlocked(userProgress, categoryStats, unlockedBadges, learnedWordCount)
              val (progressFrac, progressText) = badge.calculateProgress(userProgress, categoryStats, learnedWordCount)

              BadgeItemCard(
                badge = badge,
                isUnlocked = isUnlocked,
                progressFrac = progressFrac,
                progressText = progressText,
                onClick = {
                  burstKey++
                  isSelectedBadgeUnlocked = isUnlocked
                  selectedBadgeForDialog = badge
                  if (isUnlocked) {
                    audioController.playBadgeUnlockSound()
                    audioController.speak("Badge unlocked! ${badge.title}. ${badge.desc}")
                  } else {
                    audioController.playClickSound()
                    audioController.speak("${badge.title}. How to unlock: ${badge.goalText}")
                  }
                }
              )
            }
          }
        }
      }

      // Tap Star Burst Particle FX Overlay
      TapStarBurstOverlay(triggerKey = burstKey)

      // Celebration Confetti Overlay
      CelebrationOverlay(
        visible = showCelebration,
        message = celebrationMsg,
        starsAwarded = 5,
        onDismiss = { showCelebration = false }
      )

      // Interactive Badge Detail Dialog Modal
      selectedBadgeForDialog?.let { badge ->
        BadgeDetailDialog(
          badge = badge,
          isUnlocked = isSelectedBadgeUnlocked,
          audioController = audioController,
          onPracticeClick = { screen ->
            selectedBadgeForDialog = null
            screen?.let { onNavigate(it) }
          },
          onDismiss = { selectedBadgeForDialog = null }
        )
      }
    }
  }
}

@Composable
private fun BadgeItemCard(
  badge: BadgeDefinition,
  isUnlocked: Boolean,
  progressFrac: Float,
  progressText: String,
  onClick: () -> Unit
) {
  val cardBg = if (isUnlocked) {
    Brush.verticalGradient(
      colors = listOf(
        Color(0xFFFFF8E1),
        Color(0xFFFFF3E0),
        Color(0xFFFFECB3)
      )
    )
  } else {
    Brush.verticalGradient(
      colors = listOf(
        Color(0xFFF5F5F5),
        Color(0xFFEEEEEE)
      )
    )
  }

  val borderStroke = if (isUnlocked) {
    BorderStroke(2.dp, Color(0xFFFFB300))
  } else {
    BorderStroke(1.dp, Color(0xFFE0E0E0))
  }

  Card(
    shape = RoundedCornerShape(20.dp),
    border = borderStroke,
    elevation = CardDefaults.cardElevation(
      defaultElevation = if (isUnlocked) 4.dp else 1.dp
    ),
    modifier = Modifier
      .fillMaxWidth()
      .alpha(if (isUnlocked) 1.0f else 0.7f)
      .clickable { onClick() }
      .testTag("badge_card_${badge.id}")
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(cardBg)
        .padding(10.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Icon Frame with Circular Container
        Box(
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(if (isUnlocked) Color.White else Color(0xFFE0E0E0)),
          contentAlignment = Alignment.Center
        ) {
          if (isUnlocked) {
            Text(
              text = badge.iconEmoji,
              fontSize = 32.sp
            )
          } else {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = badge.iconEmoji,
                fontSize = 28.sp,
                modifier = Modifier.alpha(0.3f)
              )
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = Color(0xFF757575),
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = badge.title,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          color = if (isUnlocked) Color(0xFF3E2723) else Color(0xFF616161)
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (isUnlocked) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF4CAF50)
          ) {
            Text(
              text = "UNLOCKED 🏆",
              fontSize = 9.sp,
              fontWeight = FontWeight.Black,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        } else {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
          ) {
            LinearProgressIndicator(
              progress = { progressFrac },
              modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = Color(0xFF7E57C2),
              trackColor = Color(0xFFE0E0E0)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = progressText,
              fontSize = 9.sp,
              color = Color.Gray,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }
  }
}

@Composable
private fun BadgeDetailDialog(
  badge: BadgeDefinition,
  isUnlocked: Boolean,
  audioController: AudioController,
  onPracticeClick: (Screen?) -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(28.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .testTag("badge_detail_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Top Emblem Banner
        Box(
          modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(if (isUnlocked) Color(0xFFFFF8E1) else Color(0xFFF5F5F5))
            .border(
              3.dp,
              if (isUnlocked) Color(0xFFFFB300) else Color(0xFFBDBDBD),
              CircleShape
            ),
          contentAlignment = Alignment.Center
        ) {
          if (isUnlocked) {
            Text(text = badge.iconEmoji, fontSize = 52.sp)
          } else {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = badge.iconEmoji,
                fontSize = 44.sp,
                modifier = Modifier.alpha(0.25f)
              )
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = Color(0xFF616161),
                modifier = Modifier.size(36.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Badge Title
        Text(
          text = badge.title,
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold,
          textAlign = TextAlign.Center,
          color = Color(0xFF3E2723)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Status Tag
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = if (isUnlocked) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
        ) {
          Text(
            text = if (isUnlocked) "🏆 BADGE UNLOCKED!" else "🔒 LOCKED MILESTONE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = if (isUnlocked) Color(0xFF2E7D32) else Color(0xFFE65100),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(
          text = badge.desc,
          fontSize = 13.sp,
          textAlign = TextAlign.Center,
          color = Color(0xFF424242),
          modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // How to unlock details
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFF5F5F5),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "🎯 How to Unlock:",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color.Gray
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = badge.goalText,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF37474F),
              textAlign = TextAlign.Center
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFFEEEEEE),
              contentColor = Color(0xFF424242)
            ),
            modifier = Modifier.weight(1f)
          ) {
            Text("Close", fontWeight = FontWeight.Bold)
          }

          if (isUnlocked) {
            Button(
              onClick = {
                audioController.playBadgeUnlockSound()
                audioController.speak("${badge.title}! ${badge.desc}")
              },
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF8F00),
                contentColor = Color.White
              ),
              modifier = Modifier.weight(1.2f)
            ) {
              Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Hear Story", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          } else if (badge.targetScreen != null) {
            Button(
              onClick = {
                audioController.playClickSound()
                onPracticeClick(badge.targetScreen)
              },
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7E57C2),
                contentColor = Color.White
              ),
              modifier = Modifier.weight(1.3f)
            ) {
              Text("Practice Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
            }
          }
        }
      }
    }
  }
}
