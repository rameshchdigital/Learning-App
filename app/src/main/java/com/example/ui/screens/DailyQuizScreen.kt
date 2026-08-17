package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.models.GameOption
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.components.TapStarBurstOverlay
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

import com.example.ui.components.DailyQuizComponent

@Composable
fun DailyQuizScreen(
  viewModel: MainViewModel,
  audioController: AudioController,
  onBackClick: () -> Unit,
  onNavigate: (Screen) -> Unit,
  onParentLockClick: () -> Unit
) {
  val userProgress by viewModel.userProgress.collectAsState()
  val quizState by viewModel.dailyQuizUiState.collectAsState()

  var tapBurstKey = remember { mutableIntStateOf(0) }

  Scaffold(
    topBar = {
      StarsBar(
        stars = userProgress.totalStars,
        streak = userProgress.learningStreak,
        title = "📝 Daily Quiz",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFF3E5F5)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFF3E5F5),
              Color(0xFFEDE7F6),
              Color(0xFFE8EAF6)
            )
          )
        )
    ) {
      if (quizState.isQuizCompleted) {
        // Quiz Completion Results View
        DailyQuizCompletedView(
          quizState = quizState,
          learningStreak = userProgress.learningStreak,
          audioController = audioController,
          onPlayAgain = { viewModel.startDailyQuiz() },
          onGoHome = { onNavigate(Screen.HOME) }
        )
      } else if (quizState.questions.isNotEmpty() && quizState.currentIndex < quizState.questions.size) {
        Column(
          modifier = Modifier.fillMaxSize()
        ) {
          // Top Back Navigation Row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = CircleShape,
              color = Color.White,
              shadowElevation = 3.dp,
              modifier = Modifier
                .clickable { onBackClick() }
                .testTag("quiz_back_btn")
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF7E57C2),
                modifier = Modifier.padding(10.dp)
              )
            }
          }

          // Dedicated Daily Quiz Component
          DailyQuizComponent(
            quizState = quizState,
            audioController = audioController,
            onOptionSelected = { option ->
              tapBurstKey.intValue++
              viewModel.selectDailyQuizOption(option)
            },
            onNextQuestion = {
              viewModel.nextDailyQuizQuestion()
            },
            modifier = Modifier.weight(1f)
          )
        }
      }

      // Tap Star Burst Particle FX Overlay
      TapStarBurstOverlay(triggerKey = tapBurstKey.intValue)
    }
  }
}

@Composable
private fun DailyQuizCompletedView(
  quizState: com.example.data.models.DailyQuizUiState,
  learningStreak: Int,
  audioController: AudioController,
  onPlayAgain: () -> Unit,
  onGoHome: () -> Unit
) {
  CelebrationOverlay(
    visible = true,
    message = "Daily Quiz Complete! 🏆",
    starsAwarded = quizState.scoreStars + quizState.bonusStars,
    onDismiss = {}
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Card(
      shape = RoundedCornerShape(32.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      border = BorderStroke(3.dp, Color(0xFFB388FF)),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("quiz_completed_card")
    ) {
      Column(
        modifier = Modifier
          .padding(24.dp)
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        BoboMascot(
          size = 96.dp,
          state = MascotState.CELEBRATING
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Daily Quiz Complete! 🌟",
          fontSize = 22.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF4A148C),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = if (quizState.correctCount == 5) Color(0xFFE8F5E9) else Color(0xFFEDE7F6)
        ) {
          Text(
            text = if (quizState.correctCount == 5) "💯 PERFECT SCORE!" else "🎯 GREAT EFFORT!",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = if (quizState.correctCount == 5) Color(0xFF2E7D32) else Color(0xFF6A1B9A),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Daily Streak Banner Callout
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFFFF3E0),
          border = BorderStroke(1.5.dp, Color(0xFFFFB74D)),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("daily_streak_completed_banner")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Text("🔥", fontSize = 22.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "$learningStreak Day Streak Active!",
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFE65100)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Accuracy Score Box
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFF5F5F5),
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
              Text(
                text = "${quizState.correctCount} / 5",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF388E3C)
              )
              Text(
                text = "Correct Answers",
                fontSize = 11.sp,
                color = Color.Gray
              )
            }

            Box(
              modifier = Modifier
                .width(1.dp)
                .height(36.dp)
                .background(Color(0xFFE0E0E0))
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              val totalEarned = quizState.scoreStars + quizState.bonusStars
              Text(
                text = "+$totalEarned ⭐",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFF57C00)
              )
              Text(
                text = "Stars Earned",
                fontSize = 11.sp,
                color = Color.Gray
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Bonus breakdown text
        if (quizState.bonusStars > 0) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFFFF8E1),
            border = BorderStroke(1.dp, Color(0xFFFFB300))
          ) {
            Text(
              text = "🎁 Accuracy Bonus: +${quizState.bonusStars} Extra Stars!",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFE65100),
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = {
              audioController.playClickSound()
              onPlayAgain()
            },
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF7E57C2),
              contentColor = Color.White
            ),
            modifier = Modifier
              .weight(1f)
              .testTag("quiz_play_again_btn")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Play Again", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }

          Button(
            onClick = {
              audioController.playClickSound()
              onGoHome()
            },
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFFFF8F00),
              contentColor = Color.White
            ),
            modifier = Modifier
              .weight(1f)
              .testTag("quiz_go_home_btn")
          ) {
            Text("Home 🏠", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }
      }
    }
  }
}
