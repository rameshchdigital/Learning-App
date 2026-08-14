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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.models.PhonicsWord
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar

@Composable
fun PhonicsLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val phonicsWords = remember { GameContentRepository.getPhonicsWords() }
  var selectedIndex by remember { mutableIntStateOf(0) }
  val currentWord = phonicsWords[selectedIndex]

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🔠 Phonics & Blending",
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
              Color(0xFFE8F5E9)
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
        // Back Navigation
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

        // Active Phonics Display
        AnimatedContent(
          targetState = selectedIndex,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "phonicsCard"
        ) { idx ->
          val word = phonicsWords[idx]
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
                text = word.categoryLevel,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
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
                Text(word.emoji, fontSize = 56.sp)
              }

              Spacer(modifier = Modifier.height(16.dp))

              Text(
                text = "Tap letters to hear phoneme sounds:",
                fontSize = 13.sp,
                color = Color(0xFF546E7A)
              )

              Spacer(modifier = Modifier.height(12.dp))

              // Interactive Letter Tiles (Tap to hear sound)
              Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                word.letters.forEachIndexed { i, letter ->
                  val sound = word.sounds.getOrElse(i) { letter }
                  Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF26A69A),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                      .size(64.dp)
                      .clickable {
                        audioController.speak("$letter says $sound")
                      }
                      .testTag("phonics_letter_$letter")
                  ) {
                    Box(contentAlignment = Alignment.Center) {
                      Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                          text = letter,
                          fontSize = 24.sp,
                          fontWeight = FontWeight.Black,
                          color = Color.White
                        )
                        Text(
                          text = sound,
                          fontSize = 10.sp,
                          color = Color(0xFFE0F2F1)
                        )
                      }
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(20.dp))

              Button(
                onClick = {
                  val soundSeq = word.sounds.joinToString(" ... ")
                  audioController.speak("$soundSeq ... ${word.word}!")
                  onAwardStars(1)
                },
                modifier = Modifier
                  .height(54.dp)
                  .fillMaxWidth(0.9f)
                  .testTag("blend_phonics_word"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFF00897B),
                  contentColor = Color.White
                )
              ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Blend")
                Spacer(modifier = Modifier.width(8.dp))
                Text("BLEND & SPEAK: ${word.word} 🚀", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
              }
            }
          }
        }

        // Navigation
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = { if (selectedIndex > 0) selectedIndex-- },
            enabled = selectedIndex > 0
          ) {
            Surface(
              shape = CircleShape,
              color = if (selectedIndex > 0) Color(0xFF00897B) else Color.LightGray
            ) {
              Icon(Icons.Default.ChevronLeft, contentDescription = "Prev", tint = Color.White, modifier = Modifier.size(36.dp))
            }
          }

          Text(
            text = "${selectedIndex + 1} / ${phonicsWords.size}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004D40)
          )

          IconButton(
            onClick = { if (selectedIndex < phonicsWords.size - 1) selectedIndex++ },
            enabled = selectedIndex < phonicsWords.size - 1
          ) {
            Surface(
              shape = CircleShape,
              color = if (selectedIndex < phonicsWords.size - 1) Color(0xFF00897B) else Color.LightGray
            ) {
              Icon(Icons.Default.ChevronRight, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(36.dp))
            }
          }
        }

        BoboMascot(size = 80.dp, state = MascotState.IDLE_WAVING)
      }
    }
  }
}
