package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.data.models.PhonicsMinimalPair
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.components.TapStarBurstOverlay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ListeningChallengeScreen(
  totalStars: Int,
  learningStreak: Int = 0,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onBackClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val allPairs = remember { GameContentRepository.getPhonicsMinimalPairs() }
  val categories = remember {
    listOf("All", "Consonants", "Vowels", "Digraphs", "Rhyming", "Blends")
  }
  var selectedCategory by remember { mutableStateOf("All") }

  val filteredPairs = remember(selectedCategory) {
    if (selectedCategory == "All") allPairs
    else allPairs.filter { it.category.contains(selectedCategory, ignoreCase = true) }
      .ifEmpty { allPairs }
  }

  var currentIndex by remember { mutableIntStateOf(0) }
  val activePair = filteredPairs.getOrElse(currentIndex) { filteredPairs.first() }

  var selectedChoice by remember { mutableStateOf<String?>(null) }
  var isCorrectAnswer by remember { mutableStateOf<Boolean?>(null) }
  var wrongTappedWord by remember { mutableStateOf<String?>(null) }
  var isCompletedSession by remember { mutableStateOf(false) }

  var burstTriggerKey by remember { mutableIntStateOf(0) }
  var mascotState by remember { mutableStateOf(MascotState.IDLE_WAVING) }
  val coroutineScope = rememberCoroutineScope()
  val haptic = LocalHapticFeedback.current

  // Function to play current target prompt
  fun playCurrentWord(slow: Boolean = false) {
    audioController.playTapSound()
    if (slow) {
      audioController.speak("Listen slowly: ${activePair.spokenWord}. Which word is it?")
    } else {
      audioController.speak("Listen carefully: ${activePair.spokenWord}! Which word did you hear?")
    }
  }

  // Auto-play sound whenever the active pair changes
  LaunchedEffect(currentIndex, selectedCategory) {
    selectedChoice = null
    isCorrectAnswer = null
    wrongTappedWord = null
    mascotState = MascotState.SPEAKING
    delay(400)
    playCurrentWord(slow = false)
    delay(1000)
    mascotState = MascotState.IDLE_WAVING
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        streak = learningStreak,
        title = "🎧 Listening Challenge",
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
              Color(0xFFEDE7F6),
              Color(0xFFF3E5F5),
              Color(0xFFE8EAF6)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Top Row: Back button, Category Chips, Index
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 3.dp,
            modifier = Modifier
              .clickable {
                audioController.playTapSound()
                onBackClick()
              }
              .testTag("listening_challenge_back_btn")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFF5E35B1),
              modifier = Modifier.padding(10.dp)
            )
          }

          // Category Chips
          Row(
            modifier = Modifier
              .weight(1f)
              .padding(horizontal = 8.dp)
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            categories.forEach { cat ->
              val isSelected = (cat == selectedCategory)
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) Color(0xFF7E57C2) else Color.White,
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isSelected) Color(0xFF5E35B1) else Color(0xFFD1C4E9)
                ),
                shadowElevation = if (isSelected) 3.dp else 1.dp,
                modifier = Modifier.clickable {
                  selectedCategory = cat
                  currentIndex = 0
                  audioController.playTapSound()
                }
              ) {
                Text(
                  text = cat,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else Color(0xFF5E35B1),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
              }
            }
          }

          // Progress badge
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF5E35B1),
            shadowElevation = 2.dp
          ) {
            Text(
              text = "${currentIndex + 1}/${filteredPairs.size}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mascot & Audio Speaker Card
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = Color.White,
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFD1C4E9)),
          shadowElevation = 4.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              BoboMascot(
                size = 48.dp,
                state = mascotState
              )

              Column(
                modifier = Modifier
                  .weight(1f)
                  .padding(horizontal = 8.dp)
              ) {
                Text(
                  text = "Sound Contrast Challenge",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF5E35B1)
                )
                Text(
                  text = activePair.contrastDescription,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF7E57C2)
                )
              }

              Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFEDE7F6)
              ) {
                Text(
                  text = "🎯 Listen",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF5E35B1),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Big Pulsing Audio Wave & Speaker
            PulsingAudioSpeakerButton(
              onClick = { playCurrentWord(slow = false) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "Tap speaker to hear the word again 🔊",
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = Color(0xFF78909C)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Sound Support Buttons (Slow Replay & Contrast Comparison)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center
            ) {
              // Slow Motion Play
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFF3E0),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFB74D)),
                modifier = Modifier
                  .clickable { playCurrentWord(slow = true) }
                  .testTag("listen_slow_btn")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text("🐢", fontSize = 14.sp)
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Play Slowly",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100)
                  )
                }
              }

              Spacer(modifier = Modifier.width(10.dp))

              // Compare Both Words
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFE0F2F1),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF80CBC4)),
                modifier = Modifier
                  .clickable {
                    audioController.playTapSound()
                    audioController.speak("Word 1 is ${activePair.word1}. Word 2 is ${activePair.word2}. Which one was played?")
                  }
                  .testTag("compare_both_btn")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text("🔀", fontSize = 14.sp)
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Compare Both",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00695C)
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // --- TWO MINIMAL PAIR OPTION CARDS ---
        Text(
          text = "Which picture matches what you heard? 👇",
          fontSize = 14.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF4A148C)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Card 1 (word1, e.g. BAT)
          val isChoice1Correct = activePair.spokenWord.equals(activePair.word1, ignoreCase = true)
          val isChoice1Selected = (selectedChoice == activePair.word1)
          val isChoice1Wrong = (wrongTappedWord == activePair.word1)

          Box(modifier = Modifier.weight(1f)) {
            MinimalPairChoiceCard(
              word = activePair.word1,
              emoji = activePair.emoji1,
              isSelected = isChoice1Selected,
              isCorrect = isChoice1Correct,
              isWrong = isChoice1Wrong,
              onTap = {
                selectedChoice = activePair.word1
                if (isChoice1Correct) {
                  isCorrectAnswer = true
                  wrongTappedWord = null
                  mascotState = MascotState.CELEBRATING
                  burstTriggerKey++
                  haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                  audioController.playMatchSound()
                  audioController.speak("Awesome! You heard ${activePair.word1}!")
                  onAwardStars(3)
                } else {
                  isCorrectAnswer = false
                  wrongTappedWord = activePair.word1
                  mascotState = MascotState.ENCOURAGING
                  haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                  audioController.playTapSound()
                  audioController.speak("That was ${activePair.word1}! Listen again for ${activePair.spokenWord}.")
                  coroutineScope.launch {
                    delay(1400)
                    wrongTappedWord = null
                    mascotState = MascotState.IDLE_WAVING
                  }
                }
              }
            )
          }

          // Card 2 (word2, e.g. CAT)
          val isChoice2Correct = activePair.spokenWord.equals(activePair.word2, ignoreCase = true)
          val isChoice2Selected = (selectedChoice == activePair.word2)
          val isChoice2Wrong = (wrongTappedWord == activePair.word2)

          Box(modifier = Modifier.weight(1f)) {
            MinimalPairChoiceCard(
              word = activePair.word2,
              emoji = activePair.emoji2,
              isSelected = isChoice2Selected,
              isCorrect = isChoice2Correct,
              isWrong = isChoice2Wrong,
              onTap = {
                selectedChoice = activePair.word2
                if (isChoice2Correct) {
                  isCorrectAnswer = true
                  wrongTappedWord = null
                  mascotState = MascotState.CELEBRATING
                  burstTriggerKey++
                  haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                  audioController.playMatchSound()
                  audioController.speak("Awesome! You heard ${activePair.word2}!")
                  onAwardStars(3)
                } else {
                  isCorrectAnswer = false
                  wrongTappedWord = activePair.word2
                  mascotState = MascotState.ENCOURAGING
                  haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                  audioController.playTapSound()
                  audioController.speak("That was ${activePair.word2}! Listen again for ${activePair.spokenWord}.")
                  coroutineScope.launch {
                    delay(1400)
                    wrongTappedWord = null
                    mascotState = MascotState.IDLE_WAVING
                  }
                }
              }
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fun Fact / Sound Contrast Explanation Card (when answered correctly)
        AnimatedVisibility(
          visible = (isCorrectAnswer == true),
          enter = fadeIn() + scaleIn()
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFE8F5E9),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("💡", fontSize = 24.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Sound Secret:",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF2E7D32)
                )
                Text(
                  text = activePair.funFact.ifEmpty { "${activePair.word1} vs ${activePair.word2}: Practice noticing the difference!" },
                  fontSize = 11.sp,
                  color = Color(0xFF1B5E20)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Next / Navigation Controls
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = {
              if (currentIndex > 0) {
                currentIndex--
                audioController.playTapSound()
              }
            },
            enabled = currentIndex > 0,
            modifier = Modifier
              .weight(1f)
              .height(48.dp),
            shape = RoundedCornerShape(24.dp)
          ) {
            Text("Previous", fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }

          Button(
            onClick = {
              if (currentIndex < filteredPairs.size - 1) {
                currentIndex++
                audioController.playTapSound()
              } else {
                isCompletedSession = true
                audioController.playRewardSound()
                audioController.speak("Fantastic listening! You completed all sound pairs in this challenge!")
                onAwardStars(5)
              }
            },
            modifier = Modifier
              .weight(1.5f)
              .height(48.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isCorrectAnswer == true) Color(0xFF43A047) else Color(0xFF5E35B1)
            ),
            shape = RoundedCornerShape(24.dp)
          ) {
            Text(
              text = if (currentIndex < filteredPairs.size - 1) "Next Pair 🚀" else "Complete Challenge 🏆",
              fontWeight = FontWeight.Black,
              fontSize = 13.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Dots
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          filteredPairs.forEachIndexed { index, _ ->
            val isCurrent = (index == currentIndex)
            val isPast = (index < currentIndex)
            Box(
              modifier = Modifier
                .padding(3.dp)
                .size(if (isCurrent) 10.dp else 7.dp)
                .background(
                  color = when {
                    isCurrent -> Color(0xFF5E35B1)
                    isPast -> Color(0xFF4CAF50)
                    else -> Color(0xFFD1C4E9)
                  },
                  shape = CircleShape
                )
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))
      }

      // Confetti & Victory Overlay
      CelebrationOverlay(
        visible = isCompletedSession,
        message = "Super Ear Detective! 🎧🌟",
        starsAwarded = 5,
        onDismiss = {
          isCompletedSession = false
          currentIndex = 0
        }
      )

      // Star Burst Particle Overlay
      TapStarBurstOverlay(triggerKey = burstTriggerKey)
    }
  }
}

/**
 * Big pulsing speaker button that ripples with acoustic soundwaves
 */
@Composable
fun PulsingAudioSpeakerButton(
  onClick: () -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "audioWave")
  val waveScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.25f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "wave"
  )

  val waveAlpha by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "waveAlpha"
  )

  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.size(90.dp)
  ) {
    // Outer ripple circle
    Canvas(modifier = Modifier.size(85.dp)) {
      drawCircle(
        color = Color(0xFF7E57C2).copy(alpha = waveAlpha),
        radius = (size.minDimension / 2f) * waveScale,
        style = Stroke(width = 4.dp.toPx())
      )
    }

    // Interactive Speaker Button
    Surface(
      shape = CircleShape,
      color = Color(0xFF5E35B1),
      shadowElevation = 6.dp,
      modifier = Modifier
        .size(68.dp)
        .clickable { onClick() }
        .testTag("hear_target_word_speaker_btn")
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.VolumeUp,
          contentDescription = "Listen again",
          tint = Color.White,
          modifier = Modifier.size(36.dp)
        )
      }
    }
  }
}

/**
 * Minimal Pair Choice Card with bouncy animations, emoji, typography and shake feedback
 */
@Composable
fun MinimalPairChoiceCard(
  word: String,
  emoji: String,
  isSelected: Boolean,
  isCorrect: Boolean,
  isWrong: Boolean,
  onTap: () -> Unit
) {
  val shakeAnim = remember { Animatable(0f) }

  LaunchedEffect(isWrong) {
    if (isWrong) {
      shakeAnim.animateTo(1f, tween(80))
      shakeAnim.animateTo(-1f, tween(80))
      shakeAnim.animateTo(0.5f, tween(80))
      shakeAnim.animateTo(0f, tween(80))
    }
  }

  val shakeOffset = shakeAnim.value * 14f

  val cardBg = when {
    isSelected && isCorrect -> Color(0xFFE8F5E9)
    isSelected && !isCorrect -> Color(0xFFFFEBEE)
    isWrong -> Color(0xFFFFEBEE)
    else -> Color.White
  }

  val borderColor = when {
    isSelected && isCorrect -> Color(0xFF4CAF50)
    isSelected && !isCorrect -> Color(0xFFE53935)
    isWrong -> Color(0xFFE53935)
    else -> Color(0xFFB39DDB)
  }

  Surface(
    shape = RoundedCornerShape(22.dp),
    color = cardBg,
    border = androidx.compose.foundation.BorderStroke(
      width = if (isSelected || isWrong) 3.dp else 2.dp,
      color = borderColor
    ),
    shadowElevation = if (isSelected) 8.dp else 4.dp,
    modifier = Modifier
      .fillMaxWidth()
      .height(150.dp)
      .offset { IntOffset(x = shakeOffset.roundToInt(), y = 0) }
      .scale(if (isSelected && isCorrect) 1.04f else 1f)
      .clickable { onTap() }
      .testTag("pair_choice_$word")
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
      // Status indicator badge in top right
      if (isSelected && isCorrect) {
        Surface(
          shape = CircleShape,
          color = Color(0xFF4CAF50),
          modifier = Modifier
            .size(24.dp)
            .align(Alignment.TopEnd)
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Correct",
            tint = Color.White,
            modifier = Modifier.padding(3.dp)
          )
        }
      } else if (isWrong) {
        Surface(
          shape = CircleShape,
          color = Color(0xFFE53935),
          modifier = Modifier
            .size(24.dp)
            .align(Alignment.TopEnd)
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Wrong",
            tint = Color.White,
            modifier = Modifier.padding(3.dp)
          )
        }
      }

      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = emoji,
          fontSize = 46.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = word,
          fontSize = 20.sp,
          fontWeight = FontWeight.Black,
          color = if (isSelected && isCorrect) Color(0xFF2E7D32) else Color(0xFF4A148C),
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
