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
import com.example.data.content.GameContentRepository
import com.example.data.models.ShapeCard
import com.example.ui.components.StarsBar

@Composable
fun ShapesLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val shapes = remember { GameContentRepository.getShapes() }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🔺 Learn Shapes",
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
              Color(0xFFF3E5F5),
              Color(0xFFFFFDE7),
              Color(0xFFE8EAF6)
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
              tint = Color(0xFFAB47BC),
              modifier = Modifier.padding(10.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxSize()
        ) {
          items(shapes) { shapeCard ->
            Surface(
              shape = RoundedCornerShape(24.dp),
              color = Color.White,
              shadowElevation = 4.dp,
              modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f)
                .clickable {
                  audioController.speak("${shapeCard.name}! ${shapeCard.sidesDescription}. ${shapeCard.exampleObject}")
                  onAwardStars(1)
                }
                .testTag("shape_card_${shapeCard.name}")
            ) {
              Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
              ) {
                Box(
                  modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF3E5F5)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(shapeCard.emoji, fontSize = 32.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                  text = shapeCard.name,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF8E24AA)
                )

                Text(
                  text = shapeCard.sidesDescription,
                  fontSize = 10.sp,
                  color = Color(0xFF546E7A),
                  textAlign = TextAlign.Center,
                  maxLines = 2
                )
              }
            }
          }
        }
      }
    }
  }
}
