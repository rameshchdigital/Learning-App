package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.DifficultyLevel
import com.example.data.models.UserProgressEntity

@Composable
fun SettingsScreen(
  userProgress: UserProgressEntity,
  onToggleSound: (Boolean) -> Unit,
  onToggleVoice: (Boolean) -> Unit,
  onToggleAnimations: (Boolean) -> Unit,
  onToggleDailyReminder: (Boolean) -> Unit = {},
  onSelectDifficulty: (DifficultyLevel) -> Unit = {},
  onBackClick: () -> Unit
) {
  Scaffold(
    topBar = {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 4.dp
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(Color(0xFFECEFF1))
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFF546E7A)
            )
          }

          Text(
            text = "App Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F),
            modifier = Modifier.padding(start = 12.dp)
          )
        }
      }
    },
    containerColor = Color(0xFFF5F7FA)
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Text(
            text = "Audio & Sensory Settings 🔊",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F)
          )

          Spacer(modifier = Modifier.height(12.dp))

          ParentToggleRow(
            title = "Sound Effects & Notes",
            checked = userProgress.soundEnabled,
            onCheckedChange = onToggleSound,
            testTag = "settings_sound"
          )

          ParentToggleRow(
            title = "Spoken Voice Prompts",
            checked = userProgress.voiceEnabled,
            onCheckedChange = onToggleVoice,
            testTag = "settings_voice"
          )

          ParentToggleRow(
            title = "Smooth Mascot Animations",
            checked = userProgress.animationsEnabled,
            onCheckedChange = onToggleAnimations,
            testTag = "settings_animations"
          )

          ParentToggleRow(
            title = "Daily Vocabulary Practice Nudge 🔔",
            checked = userProgress.dailyReminderEnabled,
            onCheckedChange = onToggleDailyReminder,
            testTag = "settings_daily_reminder"
          )
        }
      }

      // Difficulty & Toddler Developmental Stage Card
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Text(
            text = "Toddler Stage & Quiz Complexity 👶",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F)
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Adjusts option count and challenge level for your child's age group.",
            fontSize = 13.sp,
            color = Color(0xFF78909C)
          )

          Spacer(modifier = Modifier.height(14.dp))

          DifficultyLevel.entries.forEach { level ->
            val isSelected = level.id == userProgress.difficultyLevel
            Surface(
              shape = RoundedCornerShape(18.dp),
              color = if (isSelected) Color(0xFFF3E5F5) else Color(0xFFFAFAFA),
              shadowElevation = if (isSelected) 2.dp else 0.dp,
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("settings_difficulty_${level.id}")
                .clickable { onSelectDifficulty(level) }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(level.iconEmoji, fontSize = 24.sp)

                Column(
                  modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                ) {
                  Text(
                    text = "${level.title} Stage (${level.ageRange})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (isSelected) Color(0xFF512DA8) else Color(0xFF37474F)
                  )
                  Text(
                    text = level.description,
                    fontSize = 12.sp,
                    color = Color(0xFF78909C)
                  )
                }

                Surface(
                  shape = CircleShape,
                  color = if (isSelected) Color(0xFF7E57C2) else Color(0xFFE0E0E0),
                  modifier = Modifier.size(24.dp)
                ) {
                  if (isSelected) {
                    Text(
                      text = "✓",
                      color = Color.White,
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp,
                      textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                      modifier = Modifier.padding(top = 1.dp)
                    )
                  }
                }
              }
            }
          }
        }
      }

      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Text(
            text = "About Baby World 🌈",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F)
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Designed specifically for toddlers & preschoolers to explore colors, animals, numbers, music, shapes & puzzles safely.",
            fontSize = 14.sp,
            color = Color(0xFF78909C)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "Version 1.0 • Child Safe & Local Storage",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF29B6F6)
          )
        }
      }
    }
  }
}
