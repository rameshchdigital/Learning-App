package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.audio.AudioController
import com.example.ui.components.BoboMascot
import com.example.ui.components.LessonNavigationControls
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import kotlinx.coroutines.delay

data class SpeakingPrompt(
  val word: String,
  val emoji: String,
  val sentence: String
)

@Composable
fun SpeakingPracticeScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val prompts = remember {
    listOf(
      SpeakingPrompt("Apple", "🍎", "This is an apple."),
      SpeakingPrompt("Cat", "🐱", "The cat says meow."),
      SpeakingPrompt("Dog", "🐶", "The dog is friendly."),
      SpeakingPrompt("Sun", "☀️", "The sun is yellow."),
      SpeakingPrompt("Ball", "⚽", "I like to bounce the ball.")
    )
  }

  var currentIndex by remember { mutableIntStateOf(0) }
  var isListening by remember { mutableStateOf(false) }
  var feedbackMessage by remember { mutableStateOf<String?>(null) }
  val micScale = remember { Animatable(1f) }

  val currentPrompt = prompts[currentIndex]

  LaunchedEffect(isListening) {
    if (isListening) {
      micScale.animateTo(
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
          animation = tween(400),
          repeatMode = RepeatMode.Reverse
        )
      )
    } else {
      micScale.snapTo(1f)
    }
  }

  LaunchedEffect(currentIndex) {
    audioController.speak("Say: ${currentPrompt.word}")
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🗣️ Let's Speak!",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFE0F2F1)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE0F2F1),
              Color(0xFFFFFDE7),
              Color(0xFFFFF3E0)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        // Back Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Start
        ) {
          Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.clickable { onHomeClick() }
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFF00897B),
              modifier = Modifier.padding(10.dp)
            )
          }
        }

        // Active Speaking Card
        AnimatedContent(
          targetState = currentIndex,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "speakingCard"
        ) { idx ->
          val item = prompts[idx]
          Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "LISTEN & SAY ALOUD:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF00897B)
              )

              Spacer(modifier = Modifier.height(12.dp))

              Box(
                modifier = Modifier
                  .size(100.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFE0F2F1)),
                contentAlignment = Alignment.Center
              ) {
                Text(item.emoji, fontSize = 56.sp)
              }

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = item.word,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF004D40)
              )

              Text(
                text = item.sentence,
                fontSize = 15.sp,
                color = Color(0xFF546E7A),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(20.dp))

              // Big Mic Button
              Surface(
                shape = CircleShape,
                color = if (isListening) Color(0xFFFF5252) else Color(0xFF00897B),
                shadowElevation = 8.dp,
                modifier = Modifier
                  .scale(micScale.value)
                  .size(88.dp)
                  .clickable {
                    if (!isListening) {
                      isListening = true
                      audioController.speak("Listening... speak now!")
                    }
                  }
                  .testTag("mic_speak_button")
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Mic",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = if (isListening) "Ellie is listening... 🐘" else "Tap Mic to Speak! 🎤",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isListening) Color(0xFFFF5252) else Color(0xFF00897B)
              )

              feedbackMessage?.let { msg ->
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = Color(0xFFDCEDC8)
                ) {
                  Text(
                    text = msg,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                  )
                }
              }
            }
          }
        }

        // Simulating speech detection success after 2 seconds
        LaunchedEffect(isListening) {
          if (isListening) {
            delay(2200)
            isListening = false
            feedbackMessage = "🌟 Great Speaking! Perfect!"
            audioController.playSuccessSound()
            audioController.speak("Great speaking! You earned a star!")
            onAwardStars(1)
            delay(1500)
            feedbackMessage = null
            if (currentIndex < prompts.size - 1) {
              currentIndex++
            }
          }
        }

        // Unified Previous / Play (Hear Model) / Next Controls
        LessonNavigationControls(
          onPrevious = { if (currentIndex > 0) currentIndex-- },
          onPlay = {
            audioController.speak("Say: ${currentPrompt.word}. ${currentPrompt.sentence}")
          },
          onNext = {
            if (currentIndex < prompts.size - 1) {
              currentIndex++
            }
          },
          canPrevious = currentIndex > 0,
          canNext = currentIndex < prompts.size - 1,
          playLabel = "HEAR PROMPT 🔊",
          currentIndex = currentIndex,
          totalCount = prompts.size,
          accentColor = Color(0xFF00897B)
        )

        BoboMascot(
          size = 72.dp,
          state = if (isListening) MascotState.SPEAKING else MascotState.IDLE_WAVING
        )
      }
    }
  }
}
