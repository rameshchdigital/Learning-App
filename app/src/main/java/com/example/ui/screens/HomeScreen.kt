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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.data.models.DifficultyLevel
import com.example.data.models.GameCategory
import com.example.ui.components.BoboMascot
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
  val categories = listOf(
    GameCategory.ALPHABETS,
    GameCategory.PHONICS,
    GameCategory.NUMBERS,
    GameCategory.COLORS,
    GameCategory.SHAPES,
    GameCategory.ANIMALS,
    GameCategory.FRUITS,
    GameCategory.VEGETABLES,
    GameCategory.VEHICLES,
    GameCategory.BODY_PARTS,
    GameCategory.ACTION_WORDS,
    GameCategory.SENTENCES,
    GameCategory.SPELLING,
    GameCategory.MUSIC,
    GameCategory.PUZZLES
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
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        // Simple, Cheerful Hero Card
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
          shape = RoundedCornerShape(24.dp),
          color = Color.White,
          shadowElevation = 3.dp
        ) {
          Column(
            modifier = Modifier.padding(16.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              BoboMascot(
                size = 64.dp,
                state = MascotState.IDLE_WAVING
              )

              Spacer(modifier = Modifier.width(12.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Hello Little Buddy! 👋",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                  ),
                  color = Color(0xFF1E88E5)
                )
                Text(
                  text = "Ready to learn & play today?",
                  fontSize = 13.sp,
                  color = Color(0xFF546E7A)
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
              onClick = { onCategoryClick(GameCategory.ALPHABETS) },
              modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .testTag("start_today_lesson"),
              shape = RoundedCornerShape(23.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9800),
                contentColor = Color.White
              )
            ) {
              Text("▶ Start Today's Lesson 🚀", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
          }
        }

        // Quick Activities (Flashcards, Quiz, Badges)
        Text(
          text = "Quick Fun",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF37474F),
          modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          QuickActionCard(
            title = "Flashcards",
            emoji = "🎴",
            bgColor = Color(0xFFEDE7F6),
            accentColor = Color(0xFF5E35B1),
            modifier = Modifier
              .weight(1f)
              .testTag("home_quick_cards"),
            onClick = { onNavigate(Screen.VOCABULARY_CARDS) }
          )

          QuickActionCard(
            title = "Daily Quiz",
            emoji = "🎯",
            bgColor = Color(0xFFFFF3E0),
            accentColor = Color(0xFFE65100),
            modifier = Modifier
              .weight(1f)
              .testTag("home_quick_quiz"),
            onClick = {
              onStartDailyQuiz()
              onNavigate(Screen.DAILY_QUIZ)
            }
          )

          QuickActionCard(
            title = "Badges",
            emoji = "🏆",
            bgColor = Color(0xFFE8F5E9),
            accentColor = Color(0xFF2E7D32),
            modifier = Modifier
              .weight(1f)
              .testTag("home_quick_badges"),
            onClick = { onNavigate(Screen.BADGE_COLLECTION) }
          )
        }

        // Learning Categories Section
        Text(
          text = "Explore & Learn",
          fontSize = 17.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF263238),
          modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
        )

        // 2-Column Category Grid
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          categories.chunked(2).forEach { rowCategories ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              rowCategories.forEach { category ->
                Box(modifier = Modifier.weight(1f)) {
                  CategoryCardItem(
                    category = category,
                    onClick = { onCategoryClick(category) }
                  )
                }
              }
              if (rowCategories.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}

@Composable
fun QuickActionCard(
  title: String,
  emoji: String,
  bgColor: Color,
  accentColor: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    shape = RoundedCornerShape(18.dp),
    color = bgColor,
    shadowElevation = 2.dp,
    modifier = modifier
      .scale(scaleAnim.value)
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
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(emoji, fontSize = 24.sp)
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = accentColor,
        maxLines = 1
      )
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
    shape = RoundedCornerShape(22.dp),
    color = Color(category.bgHex),
    shadowElevation = 3.dp,
    modifier = Modifier
      .scale(scaleAnim.value)
      .fillMaxWidth()
      .aspectRatio(1.18f)
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
        .padding(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(50.dp)
          .clip(CircleShape)
          .background(Color.White.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = category.iconEmoji,
          fontSize = 28.sp
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = category.title,
        fontSize = 15.sp,
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

