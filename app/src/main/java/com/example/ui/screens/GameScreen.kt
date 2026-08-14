package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.data.models.DifficultyLevel
import com.example.data.models.GameCategory
import com.example.data.models.GameOption
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.viewmodel.GameUiState
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
  state: GameUiState,
  totalStars: Int,
  onOptionSelected: (GameOption) -> Unit,
  onCategorySelected: (GameCategory) -> Unit = {},
  onDifficultySelected: (DifficultyLevel) -> Unit = {},
  onSpeakOption: ((GameOption) -> Unit)? = null,
  onReplayVoiceClick: () -> Unit,
  onRestartClick: () -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val currentQuestion = state.questions.getOrNull(state.currentQuestionIndex)
  val animatedBgColor by animateColorAsState(
    targetValue = Color(state.currentCategory.bgHex),
    animationSpec = spring(stiffness = Spring.StiffnessLow),
    label = "animatedBgColor"
  )

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = state.currentCategory.title,
        showHome = true,
        onHomeClick = onHomeClick,
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = animatedBgColor
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              animatedBgColor,
              Color.White
            )
          )
        )
    ) {
      if (state.isSessionCompleted) {
        SessionCompletedView(
          starsEarned = state.starsEarnedInSession,
          onPlayAgain = onRestartClick,
          onHome = onHomeClick
        )
      } else if (currentQuestion != null) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Category Selector Chips Bar
          LazyRow(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
          ) {
            items(GameCategory.entries) { cat ->
              val isSelected = cat == state.currentCategory
              val chipColor by animateColorAsState(
                targetValue = if (isSelected) Color(cat.colorHex) else Color.White.copy(alpha = 0.9f),
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                label = "chipColor"
              )
              val chipScale by animateFloatAsState(
                targetValue = if (isSelected) 1.06f else 1.0f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
                label = "chipScale"
              )
              val chipElevation by animateDpAsState(
                targetValue = if (isSelected) 5.dp else 1.dp,
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                label = "chipElevation"
              )

              Surface(
                shape = RoundedCornerShape(20.dp),
                color = chipColor,
                shadowElevation = chipElevation,
                modifier = Modifier
                  .scale(chipScale)
                  .testTag("category_chip_${cat.id}")
                  .clickable { onCategorySelected(cat) }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(cat.iconEmoji, fontSize = 15.sp)
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = cat.title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = if (isSelected) Color.White else Color(0xFF37474F)
                  )
                }
              }
            }
          }

          // Toddler Developmental Stage / Difficulty Switcher Bar
          LazyRow(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
          ) {
            items(DifficultyLevel.entries) { diff ->
              val isSelected = diff == state.currentDifficulty
              Surface(
                shape = RoundedCornerShape(18.dp),
                color = if (isSelected) Color(0xFF7E57C2) else Color(0xFFF3E5F5),
                shadowElevation = if (isSelected) 3.dp else 0.dp,
                modifier = Modifier
                  .testTag("difficulty_chip_${diff.id}")
                  .clickable { onDifficultySelected(diff) }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(diff.iconEmoji, fontSize = 14.sp)
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "${diff.title} (${diff.ageRange})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = if (isSelected) Color.White else Color(0xFF512DA8)
                  )
                }
              }
            }
          }

          AnimatedContent(
            targetState = Pair(state.currentCategory, state.currentQuestionIndex),
            transitionSpec = {
              val (prevCat, _) = initialState
              val (newCat, _) = targetState

              if (prevCat != newCat) {
                // Playful category transition: spring bounce slide & scale transition
                (slideInHorizontally(
                  animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                  ),
                  initialOffsetX = { fullWidth -> fullWidth }
                ) + fadeIn(animationSpec = tween(350)) + scaleIn(
                  animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                  initialScale = 0.82f
                )) togetherWith (slideOutHorizontally(
                  animationSpec = spring(stiffness = Spring.StiffnessLow),
                  targetOffsetX = { fullWidth -> -fullWidth }
                ) + fadeOut(animationSpec = tween(250)) + scaleOut(targetScale = 0.82f))
              } else {
                // Smooth question slide transition inside category
                (slideInHorizontally(
                  animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                  initialOffsetX = { fullWidth -> fullWidth / 2 }
                ) + fadeIn()) togetherWith (slideOutHorizontally(
                  animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                  targetOffsetX = { fullWidth -> -fullWidth / 2 }
                ) + fadeOut())
              }
            },
            label = "CategoryQuizTransition",
            modifier = Modifier.fillMaxSize()
          ) { (cat, qIndex) ->
            val q = state.questions.getOrNull(qIndex)
            if (q != null) {
              Column(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
              ) {
                Surface(
                  shape = RoundedCornerShape(32.dp),
                  color = Color.White,
                  shadowElevation = 6.dp,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    BoboMascot(
                      size = 80.dp,
                      state = if (state.isAnswerCorrect == false) MascotState.ENCOURAGING else MascotState.IDLE_WAVING
                    )

                    Column(
                      modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                    ) {
                      Text(
                        text = q.questionText,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF37474F)
                      )
                    }

                    IconButton(
                      onClick = onReplayVoiceClick,
                      modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(cat.bgHex))
                    ) {
                      Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Replay Voice",
                        tint = Color(cat.colorHex)
                      )
                    }
                  }
                }

                Column(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                  verticalArrangement = Arrangement.spacedBy(16.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  q.options.forEach { option ->
                    val isSelected = state.selectedOptionId == option.id
                    val isWrong = isSelected && state.isAnswerCorrect == false

                    OptionButton(
                      option = option,
                      categoryColorHex = option.colorHex ?: cat.colorHex,
                      isWrong = isWrong,
                      difficulty = state.currentDifficulty,
                      onSpeakOption = onSpeakOption,
                      onClick = { onOptionSelected(option) }
                    )
                  }
                }

                Spacer(modifier = Modifier.height(16.dp))
              }
            }
          }
        }
      }

      CelebrationOverlay(
        visible = state.isCelebrationVisible,
        message = state.celebrationMessage,
        starsAwarded = 1
      )
    }
  }
}

@Composable
fun OptionButton(
  option: GameOption,
  categoryColorHex: Long,
  isWrong: Boolean,
  difficulty: DifficultyLevel = DifficultyLevel.MEDIUM,
  onSpeakOption: ((GameOption) -> Unit)? = null,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  val buttonColor = if (isWrong) Color(0xFFFFCDD2) else Color.White

  val height = when (difficulty) {
    DifficultyLevel.EASY -> 94.dp
    DifficultyLevel.MEDIUM -> 82.dp
    DifficultyLevel.HARD -> 70.dp
  }
  val emojiSize = when (difficulty) {
    DifficultyLevel.EASY -> 38.sp
    DifficultyLevel.MEDIUM -> 32.sp
    DifficultyLevel.HARD -> 28.sp
  }
  val labelSize = when (difficulty) {
    DifficultyLevel.EASY -> 24.sp
    DifficultyLevel.MEDIUM -> 22.sp
    DifficultyLevel.HARD -> 19.sp
  }

  Surface(
    shape = RoundedCornerShape(28.dp),
    color = buttonColor,
    shadowElevation = 6.dp,
    modifier = Modifier
      .scale(scaleAnim.value)
      .fillMaxWidth(0.92f)
      .height(height)
      .testTag("option_${option.id}")
      .clickable {
        scope.launch {
          scaleAnim.animateTo(0.92f, spring(dampingRatio = 0.4f))
          scaleAnim.animateTo(1.0f, spring(dampingRatio = 0.6f))
          onClick()
        }
      }
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(if (difficulty == DifficultyLevel.EASY) 66.dp else 54.dp)
            .clip(CircleShape)
            .background(Color(categoryColorHex).copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = option.emoji,
            fontSize = emojiSize
          )
        }

        Text(
          text = option.label,
          fontSize = labelSize,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF37474F),
          modifier = Modifier.padding(start = 14.dp)
        )
      }

      if (onSpeakOption != null) {
        IconButton(
          onClick = { onSpeakOption(option) },
          modifier = Modifier
            .size(40.dp)
            .testTag("speak_option_${option.id}")
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Read option ${option.label}",
            tint = Color(categoryColorHex),
            modifier = Modifier.size(22.dp)
          )
        }
      }
    }
  }
}

@Composable
fun SessionCompletedView(
  starsEarned: Int,
  onPlayAgain: () -> Unit,
  onHome: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White)
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      BoboMascot(
        size = 140.dp,
        state = MascotState.CELEBRATING
      )

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "🎉 Amazing Playing!",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF37474F),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "You earned $starsEarned stars! ⭐",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFF9800)
      )

      Spacer(modifier = Modifier.height(32.dp))

      Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Button(
          onClick = onPlayAgain,
          shape = RoundedCornerShape(28.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)),
          modifier = Modifier.height(60.dp)
        ) {
          Icon(imageVector = Icons.Default.Refresh, contentDescription = "Play Again")
          Text(
            text = " PLAY AGAIN",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
        }

        Button(
          onClick = onHome,
          shape = RoundedCornerShape(28.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5)),
          modifier = Modifier.height(60.dp)
        ) {
          Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
          Text(
            text = " HOME",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
        }
      }
    }
  }
}
