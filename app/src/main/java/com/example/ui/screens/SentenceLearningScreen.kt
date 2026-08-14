package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.ui.components.BoboMascot
import com.example.ui.components.LessonNavigationControls
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import kotlinx.coroutines.delay

@Composable
fun SentenceLearningScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val sentenceCards = remember { GameContentRepository.getSentenceCards() }
  var selectedIndex by remember { mutableIntStateOf(0) }
  var activeWordIndex by remember { mutableStateOf(-1) }

  val currentCard = sentenceCards[selectedIndex]
  val words = currentCard.first.split(" ")

  LaunchedEffect(selectedIndex) {
    audioController.speak(currentCard.first)
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🗣️ Sentence Reader",
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
              Color(0xFFE3F2FD)
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

        // Active Sentence Card
        AnimatedContent(
          targetState = selectedIndex,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "sentenceCard"
        ) { idx ->
          val (sentenceText, emoji) = sentenceCards[idx]
          val cardWords = sentenceText.split(" ")

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
              Box(
                modifier = Modifier
                  .size(100.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFE0F2F1)),
                contentAlignment = Alignment.Center
              ) {
                Text(emoji, fontSize = 56.sp)
              }

              Spacer(modifier = Modifier.height(20.dp))

              // Word by Word Sentence Display
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
              ) {
                cardWords.forEachIndexed { wordIdx, word ->
                  val isHighlighted = wordIdx == activeWordIndex
                  Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isHighlighted) Color(0xFFFFB300) else Color.Transparent,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = word,
                      fontSize = 24.sp,
                      fontWeight = if (isHighlighted) FontWeight.Black else FontWeight.Bold,
                      color = if (isHighlighted) Color.White else Color(0xFF263238),
                      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                  }
                }
              }
            }
          }
        }

        // Unified Previous / Play / Next Controls
        LessonNavigationControls(
          onPrevious = { if (selectedIndex > 0) selectedIndex-- },
          onPlay = {
            audioController.speak(currentCard.first)
            onAwardStars(1)
          },
          onNext = {
            if (selectedIndex < sentenceCards.size - 1) {
              selectedIndex++
              onAwardStars(1)
            }
          },
          canPrevious = selectedIndex > 0,
          canNext = selectedIndex < sentenceCards.size - 1,
          playLabel = "LISTEN SENTENCE 🔊",
          currentIndex = selectedIndex,
          totalCount = sentenceCards.size,
          accentColor = Color(0xFF00897B)
        )

        BoboMascot(size = 72.dp, state = MascotState.IDLE_WAVING)
      }
    }
  }
}
