package com.example.ui.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.data.models.GameCategory
import com.example.data.models.GameOption
import com.example.data.repository.CategoryRepository
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar

@Composable
fun CategoryLessonScreen(
  category: GameCategory,
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val options = remember(category) {
    val structured = CategoryRepository.getCategoryContent(category)
    if (structured != null && structured.items.isNotEmpty()) {
      structured.items.map { GameOption(it.id, it.name, it.emoji) }
    } else {
      listOf(
        GameOption("star", "Star", "⭐"),
        GameOption("sun", "Sun", "☀️"),
        GameOption("moon", "Moon", "🌙"),
        GameOption("flower", "Flower", "🌸"),
        GameOption("tree", "Tree", "🌳"),
        GameOption("rainbow", "Rainbow", "🌈")
      )
    }
  }


  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "${category.iconEmoji} ${category.title}",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(category.bgHex)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(category.bgHex),
              Color(0xFFFFFDE7),
              Color.White
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
        // Back Header
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
              tint = Color(category.colorHex),
              modifier = Modifier.padding(10.dp)
            )
          }

          BoboMascot(size = 52.dp, state = MascotState.IDLE_WAVING)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Tap any word to hear it spoken! 🔊",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF37474F)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxSize()
        ) {
          items(options) { item ->
            Surface(
              shape = RoundedCornerShape(24.dp),
              color = Color.White,
              shadowElevation = 4.dp,
              modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f)
                .clickable {
                  audioController.speak(item.label)
                  onAwardStars(1)
                }
                .testTag("lesson_card_${item.id}")
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
                    .background(Color(category.bgHex)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(item.emoji, fontSize = 32.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = item.label,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(category.colorHex),
                  textAlign = TextAlign.Center
                )
              }
            }
          }
        }
      }
    }
  }
}
