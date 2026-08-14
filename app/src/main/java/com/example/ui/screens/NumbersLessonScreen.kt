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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.data.models.NumberCard
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar

@Composable
fun NumbersLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val numbers = remember { GameContentRepository.getNumbers() }
  var selectedIndex by remember { mutableIntStateOf(0) }
  val currentNumber = numbers[selectedIndex]

  LaunchedEffect(selectedIndex) {
    audioController.speak("${currentNumber.number}! ${currentNumber.word}! ${currentNumber.countText}")
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🔢 Let's Count!",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFFFF8E1)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFF8E1),
              Color(0xFFFFFDE7),
              Color(0xFFFFE0B2)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
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
              tint = Color(0xFFFFB300),
              modifier = Modifier.padding(10.dp)
            )
          }
        }

        // Hero Number Display Card
        AnimatedContent(
          targetState = selectedIndex,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "numberCard"
        ) { idx ->
          val item = numbers[idx]
          Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 8.dp)
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "${item.number}",
                fontSize = 72.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFF8F00)
              )

              Text(
                text = item.word,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF37474F)
              )

              Spacer(modifier = Modifier.height(16.dp))

              // Objects row
              Text(
                text = item.emoji,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
              )

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = item.countText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00897B)
              )

              Spacer(modifier = Modifier.height(16.dp))

              Button(
                onClick = {
                  audioController.speak("Let's count! ${item.number}! ${item.word}! ${item.countText}")
                  onAwardStars(1)
                },
                modifier = Modifier
                  .height(48.dp)
                  .testTag("count_audio_button"),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFFFFB300),
                  contentColor = Color.White
                )
              ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Count")
                Spacer(modifier = Modifier.width(8.dp))
                Text("TAP TO COUNT 🔊", fontWeight = FontWeight.Bold)
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
              color = if (selectedIndex > 0) Color(0xFFFFB300) else Color.LightGray
            ) {
              Icon(Icons.Default.ChevronLeft, contentDescription = "Prev", tint = Color.White, modifier = Modifier.size(36.dp))
            }
          }

          Text(
            text = "${selectedIndex + 1} / ${numbers.size}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100)
          )

          IconButton(
            onClick = { if (selectedIndex < numbers.size - 1) selectedIndex++ },
            enabled = selectedIndex < numbers.size - 1
          ) {
            Surface(
              shape = CircleShape,
              color = if (selectedIndex < numbers.size - 1) Color(0xFFFFB300) else Color.LightGray
            ) {
              Icon(Icons.Default.ChevronRight, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(36.dp))
            }
          }
        }

        // Horizontal Number Picker
        LazyRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
          itemsIndexed(numbers) { idx, item ->
            val isSelected = idx == selectedIndex
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isSelected) Color(0xFFFFB300) else Color.White,
              shadowElevation = if (isSelected) 4.dp else 1.dp,
              modifier = Modifier
                .size(52.dp)
                .clickable { selectedIndex = idx }
                .testTag("select_number_${item.number}")
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  text = "${item.number}",
                  fontSize = 22.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = if (isSelected) Color.White else Color(0xFFFF8F00)
                )
              }
            }
          }
        }
      }
    }
  }
}
