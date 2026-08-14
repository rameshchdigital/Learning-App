package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.models.DailyQuizUiState
import com.example.data.models.GameOption

@Composable
fun DailyQuizComponent(
  quizState: DailyQuizUiState,
  audioController: AudioController,
  onOptionSelected: (GameOption) -> Unit,
  onNextQuestion: () -> Unit,
  modifier: Modifier = Modifier
) {
  if (quizState.questions.isEmpty() || quizState.currentIndex >= quizState.questions.size) return

  val currentQuestion = quizState.questions[quizState.currentIndex]
  val totalQuestions = 5
  val progressFraction = (quizState.currentIndex + 1) / totalQuestions.toFloat()

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    // Header Info with Question Counter & Audio Trigger
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "Question ${quizState.currentIndex + 1} of $totalQuestions 📝",
          fontSize = 18.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF4A148C)
        )
        Text(
          text = "Practiced: ${quizState.practicedCategoriesText}",
          fontSize = 12.sp,
          color = Color(0xFF6A1B9A),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Surface(
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 3.dp,
        modifier = Modifier.clickable {
          audioController.speak(currentQuestion.voicePrompt)
        }
      ) {
        Icon(
          imageVector = Icons.Default.VolumeUp,
          contentDescription = "Read Question",
          tint = Color(0xFF7E57C2),
          modifier = Modifier.padding(10.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Animated Progress Indicator
    LinearProgressIndicator(
      progress = { progressFraction },
      modifier = Modifier
        .fillMaxWidth()
        .height(10.dp)
        .clip(RoundedCornerShape(5.dp)),
      color = Color(0xFF7E57C2),
      trackColor = Color(0xFFD1C4E9)
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Main Question Banner Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      border = BorderStroke(2.dp, Color(0xFFB388FF)),
      elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("quiz_question_card")
    ) {
      Row(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        BoboMascot(
          size = 64.dp,
          state = if (quizState.isAnswered) {
            if (quizState.isCorrect) MascotState.CELEBRATING else MascotState.ENCOURAGING
          } else MascotState.SPEAKING
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFEDE7F6)
          ) {
            Text(
              text = "${currentQuestion.category.iconEmoji} ${currentQuestion.category.title}",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF5E35B1),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = currentQuestion.questionText,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF3E2723)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Interactive Option Cards Grid
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.weight(1f)
    ) {
      items(currentQuestion.options, key = { it.id }) { option ->
        val isSelected = quizState.selectedOptionId == option.id
        val isCorrectOption = option.id == currentQuestion.correctAnswerId

        // Option scale animation on selection
        val scale by animateFloatAsState(
          targetValue = if (isSelected) 1.05f else 1f,
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
          ),
          label = "option_scale"
        )

        val cardBg = when {
          !quizState.isAnswered -> Color.White
          isCorrectOption -> Color(0xFFE8F5E9)
          isSelected -> Color(0xFFFFEBEE)
          else -> Color.White.copy(alpha = 0.6f)
        }

        val borderColor = when {
          !quizState.isAnswered -> if (isSelected) Color(0xFF7E57C2) else Color(0xFFD1C4E9)
          isCorrectOption -> Color(0xFF4CAF50)
          isSelected -> Color(0xFFEF5350)
          else -> Color(0xFFE0E0E0)
        }

        Card(
          shape = RoundedCornerShape(22.dp),
          colors = CardDefaults.cardColors(containerColor = cardBg),
          border = BorderStroke(3.dp, borderColor),
          elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected || !quizState.isAnswered) 4.dp else 1.dp
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .scale(scale)
            .clickable(enabled = !quizState.isAnswered) {
              onOptionSelected(option)
              if (option.id == currentQuestion.correctAnswerId) {
                audioController.playCorrectSound()
              } else {
                audioController.playTryAgainSound()
              }
            }
            .testTag("quiz_option_${option.id}")
        ) {
          Box(
            modifier = Modifier.fillMaxSize()
          ) {
            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = option.emoji,
                fontSize = 42.sp
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = option.label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF37474F),
                textAlign = TextAlign.Center
              )
            }

            // Visual Icon Feedback Overlay for Answered State
            if (quizState.isAnswered) {
              if (isCorrectOption) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Correct",
                  tint = Color(0xFF2E7D32),
                  modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                )
              } else if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Cancel,
                  contentDescription = "Incorrect",
                  tint = Color(0xFFC62828),
                  modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                )
              }
            }
          }
        }
      }
    }

    // Bottom Immediate Feedback Action Card
    AnimatedVisibility(
      visible = quizState.isAnswered,
      enter = fadeIn(animationSpec = tween(300)) + scaleIn(),
      exit = fadeOut(animationSpec = tween(200)) + scaleOut()
    ) {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (quizState.isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
        ),
        border = BorderStroke(
          2.dp,
          if (quizState.isCorrect) Color(0xFF81C784) else Color(0xFFFFB74D)
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Text(
              text = if (quizState.isCorrect) "🎉" else "💡",
              fontSize = 28.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (quizState.isCorrect) "Correct! +2 Stars ⭐" else "Nice try!",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (quizState.isCorrect) Color(0xFF2E7D32) else Color(0xFFE65100)
              )
              Text(
                text = if (quizState.isCorrect) "You got it right!" else "Keep practicing to master it!",
                fontSize = 11.sp,
                color = Color(0xFF424242)
              )
            }
          }

          Button(
            onClick = {
              audioController.playClickSound()
              onNextQuestion()
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF7E57C2),
              contentColor = Color.White
            ),
            modifier = Modifier.testTag("quiz_next_btn")
          ) {
            Text(
              text = if (quizState.currentIndex == totalQuestions - 1) "Finish 🏆" else "Next ➡️",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }
      }
    }
  }
}
