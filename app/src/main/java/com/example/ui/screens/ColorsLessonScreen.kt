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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.models.ColorCard
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar

@Composable
fun ColorsLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val colors = remember { GameContentRepository.getColors() }
  var isGameMode by remember { mutableStateOf(false) }
  var targetColor by remember { mutableStateOf(colors.random()) }
  var gameScore by remember { mutableStateOf(0) }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🎨 Learn Colors",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFFFEBEE)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFEBEE),
              Color(0xFFFFFDE7),
              Color(0xFFE3F2FD)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Back Header & Mode Switcher
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
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
              tint = Color(0xFFEF5350),
              modifier = Modifier.padding(10.dp)
            )
          }

          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(Color.White)
              .padding(4.dp)
          ) {
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (!isGameMode) Color(0xFFEF5350) else Color.Transparent,
              modifier = Modifier.clickable { isGameMode = false }
            ) {
              Text(
                text = "Explore 🎨",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (!isGameMode) Color.White else Color(0xFFEF5350),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isGameMode) Color(0xFFEF5350) else Color.Transparent,
              modifier = Modifier.clickable {
                isGameMode = true
                targetColor = colors.random()
                audioController.speak("Find the ${targetColor.name} object!")
              }
            ) {
              Text(
                text = "Play Game 🎯",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (isGameMode) Color.White else Color(0xFFEF5350),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isGameMode) {
          // Explore Colors Grid
          LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
          ) {
            items(colors) { colorCard ->
              Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier
                  .fillMaxWidth()
                  .aspectRatio(1.1f)
                  .clickable {
                    audioController.speak("${colorCard.name}! ${colorCard.exampleObject}")
                    onAwardStars(1)
                  }
                  .testTag("color_card_${colorCard.name}")
              ) {
                Column(
                  modifier = Modifier.padding(12.dp),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  Box(
                    modifier = Modifier
                      .size(56.dp)
                      .clip(CircleShape)
                      .background(Color(colorCard.colorHex)),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(colorCard.emoji, fontSize = 28.sp)
                  }

                  Spacer(modifier = Modifier.height(8.dp))

                  Text(
                    text = colorCard.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF263238)
                  )

                  Text(
                    text = colorCard.exampleObject,
                    fontSize = 12.sp,
                    color = Color(0xFF546E7A)
                  )
                }
              }
            }
          }
        } else {
          // Find Color Game Screen
          Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 12.dp)
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "FIND THE COLOR!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFEF5350)
              )

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = "Tap the ${targetColor.name.uppercase()} object!",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(targetColor.colorHex)
              )

              Spacer(modifier = Modifier.height(16.dp))

              // 4 choices grid
              val options = remember(targetColor) {
                (colors.filter { it.name != targetColor.name }.shuffled().take(3) + targetColor).shuffled()
              }

              LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(220.dp)
              ) {
                items(options) { opt ->
                  Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(opt.colorHex).copy(alpha = 0.2f),
                    shadowElevation = 2.dp,
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(90.dp)
                      .clickable {
                        if (opt.name == targetColor.name) {
                          audioController.playSuccessSound()
                          audioController.speak("Correct! ${opt.name}!")
                          gameScore++
                          onAwardStars(1)
                          targetColor = colors.random()
                        } else {
                          audioController.playTryAgainSound()
                          audioController.speak("Try again! Find ${targetColor.name}!")
                        }
                      }
                      .testTag("color_game_option_${opt.name}")
                  ) {
                    Column(
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center
                    ) {
                      Text(opt.emoji, fontSize = 32.sp)
                      Text(
                        text = opt.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F)
                      )
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(16.dp))

              BoboMascot(size = 80.dp, state = MascotState.CELEBRATING)
            }
          }
        }
      }
    }
  }
}
