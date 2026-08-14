package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.data.models.CategoryStatEntity
import com.example.data.models.GameCategory
import com.example.data.models.UserProgressEntity
import com.example.ui.theme.StarGold

@Composable
fun ParentDashboardScreen(
  userProgress: UserProgressEntity,
  categoryStats: List<CategoryStatEntity>,
  onToggleSound: (Boolean) -> Unit,
  onToggleVoice: (Boolean) -> Unit,
  onToggleAnimations: (Boolean) -> Unit,
  onToggleNightMode: (Boolean) -> Unit,
  onToggleDailyReminder: (Boolean) -> Unit = {},
  onTestNotification: () -> Unit = {},
  onResetProgress: () -> Unit,
  onOpenSettings: () -> Unit,
  onBackToGame: () -> Unit
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
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = onBackToGame,
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3E5F5))
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFAB47BC)
              )
            }

            Spacer(modifier = Modifier.padding(start = 8.dp))

            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = null,
              tint = Color(0xFFAB47BC),
              modifier = Modifier.size(24.dp)
            )

            Text(
              text = "Parent Dashboard",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF37474F),
              modifier = Modifier.padding(start = 8.dp)
            )
          }

          IconButton(
            onClick = onOpenSettings,
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(Color(0xFFECEFF1))
          ) {
            Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = "Settings",
              tint = Color(0xFF546E7A)
            )
          }
        }
      }
    },
    containerColor = Color(0xFFF5F7FA)
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      item {
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Child Learning Progress 📈",
              style = MaterialTheme.typography.titleLarge,
              color = Color(0xFF37474F)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              ProgressStatBox(
                label = "Total Stars",
                value = "${userProgress.totalStars} ⭐",
                bgColor = Color(0xFFFFF8E1),
                textColor = StarGold
              )

              ProgressStatBox(
                label = "Completed",
                value = "${userProgress.activitiesCompleted} 🎉",
                bgColor = Color(0xFFE8F5E9),
                textColor = Color(0xFF2E7D32)
              )
            }
          }
        }
      }

      item {
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Category Breakdown",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF37474F)
            )

            Spacer(modifier = Modifier.height(12.dp))

            GameCategory.entries.forEach { cat ->
              val stat = categoryStats.find { it.categoryId == cat.id }
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(text = cat.iconEmoji, fontSize = 22.sp)
                  Text(
                    text = cat.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(start = 12.dp)
                  )
                }

                Text(
                  text = "${stat?.starsEarned ?: 0} Stars",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(cat.colorHex)
                )
              }
            }
          }
        }
      }

      item {
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Parental Controls & Options",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF37474F)
            )

            Spacer(modifier = Modifier.height(12.dp))

            ParentToggleRow(
              title = "Sound Effects",
              checked = userProgress.soundEnabled,
              onCheckedChange = onToggleSound,
              testTag = "toggle_sound"
            )

            ParentToggleRow(
              title = "Voice Prompts",
              checked = userProgress.voiceEnabled,
              onCheckedChange = onToggleVoice,
              testTag = "toggle_voice"
            )

            ParentToggleRow(
              title = "Animations",
              checked = userProgress.animationsEnabled,
              onCheckedChange = onToggleAnimations,
              testTag = "toggle_animations"
            )

            ParentToggleRow(
              title = "Night Mode Canvas",
              checked = userProgress.nightMode,
              onCheckedChange = onToggleNightMode,
              testTag = "toggle_night_mode"
            )
          }
        }
      }

      // Daily Vocabulary Habit & Push Notification Nudge Card
      item {
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Daily Vocabulary Habit Nudge 🔔",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF37474F)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "Sends a daily push notification at 5:00 PM to build consistent vocabulary learning habits.",
                  fontSize = 12.sp,
                  color = Color(0xFF78909C)
                )
              }

              Switch(
                checked = userProgress.dailyReminderEnabled,
                onCheckedChange = onToggleDailyReminder,
                colors = SwitchDefaults.colors(
                  checkedThumbColor = Color.White,
                  checkedTrackColor = Color(0xFF43A047)
                ),
                modifier = Modifier.testTag("toggle_daily_reminder")
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = onTestNotification,
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8F5E9),
                contentColor = Color(0xFF2E7D32)
              ),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("test_push_notification_btn")
            ) {
              Text("🔔 Trigger Push Notification Nudge Now", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }
        }
      }

      item {
        Button(
          onClick = onResetProgress,
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
          shape = RoundedCornerShape(20.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("reset_progress_button")
        ) {
          Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            tint = Color(0xFFD32F2F)
          )
          Text(
            text = "Reset All Progress Data",
            color = Color(0xFFD32F2F),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
          )
        }

        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
fun ProgressStatBox(
  label: String,
  value: String,
  bgColor: Color,
  textColor: Color
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(20.dp))
      .background(bgColor)
      .padding(horizontal = 24.dp, vertical = 16.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = value,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = textColor
      )
      Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF78909C)
      )
    }
  }
}

@Composable
fun ParentToggleRow(
  title: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  testTag: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = title,
      fontSize = 16.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF37474F)
    )

    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        checkedTrackColor = Color(0xFFAB47BC)
      ),
      modifier = Modifier.testTag(testTag)
    )
  }
}
