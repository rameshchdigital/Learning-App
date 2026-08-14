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
import androidx.compose.runtime.mutableStateListOf
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

@Composable
fun SpellingLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val spellingList = remember { GameContentRepository.getSpellingWords() }
  var selectedIndex by remember { mutableIntStateOf(0) }

  val targetPair = spellingList[selectedIndex]
  val targetWord = targetPair.first
  val targetEmoji = targetPair.second

  var userLetters = remember(targetWord) { mutableStateListOf<String>() }
  var scrambledLetters = remember(targetWord) {
    targetWord.map { it.toString() }.shuffled().toMutableList()
  }

  var isSuccess by remember { mutableStateOf(false) }

  LaunchedEffect(selectedIndex) {
    audioController.speak("Spell the word ${targetWord}!")
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "✏️ Spelling Fun",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFEDE7F6)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFEDE7F6),
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
              tint = Color(0xFF7E57C2),
              modifier = Modifier.padding(10.dp)
            )
          }
        }

        // Active Spelling Card
        AnimatedContent(
          targetState = selectedIndex,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "spellingCard"
        ) { idx ->
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
                  .size(90.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFEDE7F6)),
                contentAlignment = Alignment.Center
              ) {
                Text(targetEmoji, fontSize = 52.sp)
              }

              Spacer(modifier = Modifier.height(16.dp))

              Text(
                text = "Tap letters to spell the word:",
                fontSize = 13.sp,
                color = Color(0xFF546E7A)
              )

              Spacer(modifier = Modifier.height(16.dp))

              // User Selected Letter Slots
              Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                targetWord.indices.forEach { i ->
                  val char = userLetters.getOrNull(i) ?: ""
                  Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (char.isNotEmpty()) Color(0xFF7E57C2) else Color(0xFFF3E5F5),
                    modifier = Modifier.size(54.dp)
                  ) {
                    Box(contentAlignment = Alignment.Center) {
                      Text(
                        text = char,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                      )
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(24.dp))

              // Available Scrambled Letter Choices
              Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                scrambledLetters.forEachIndexed { i, letter ->
                  Surface(
                    shape = CircleShape,
                    color = Color(0xFFFFB300),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                      .size(56.dp)
                      .clickable {
                        audioController.speak(letter)
                        userLetters.add(letter)
                        scrambledLetters.removeAt(i)

                        // Check word completion
                        if (userLetters.joinToString("") == targetWord) {
                          isSuccess = true
                          audioController.playSuccessSound()
                          audioController.speak("Correct! $targetWord!")
                          onAwardStars(1)
                        }
                      }
                      .testTag("scrambled_letter_$letter")
                  ) {
                    Box(contentAlignment = Alignment.Center) {
                      Text(
                        text = letter,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                      )
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              Button(
                onClick = {
                  userLetters.clear()
                  scrambledLetters = targetWord.map { it.toString() }.shuffled().toMutableList()
                  isSuccess = false
                },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFFAB47BC),
                  contentColor = Color.White
                )
              ) {
                Text("RESET LETTERS 🔄", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              }
            }
          }
        }

        // Unified Previous / Play / Next Controls
        LessonNavigationControls(
          onPrevious = {
            if (selectedIndex > 0) {
              isSuccess = false
              selectedIndex--
            }
          },
          onPlay = {
            audioController.speak("Spell the word $targetWord!")
          },
          onNext = {
            if (selectedIndex < spellingList.size - 1) {
              isSuccess = false
              selectedIndex++
            }
          },
          canPrevious = selectedIndex > 0,
          canNext = selectedIndex < spellingList.size - 1,
          playLabel = "HEAR WORD 🔊",
          currentIndex = selectedIndex,
          totalCount = spellingList.size,
          accentColor = Color(0xFF7E57C2)
        )

        BoboMascot(
          size = 72.dp,
          state = if (isSuccess) MascotState.CELEBRATING else MascotState.IDLE_WAVING
        )
      }
    }
  }
}
