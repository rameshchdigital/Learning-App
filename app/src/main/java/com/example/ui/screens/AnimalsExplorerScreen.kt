package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.data.models.AnimalCard
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.components.TapStarBurstOverlay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimalsExplorerScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onStartQuizMode: () -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val allAnimals = remember { GameContentRepository.getAnimalCards() }
  var selectedGroup by remember { mutableStateOf("All") }
  var activeAnimal by remember { mutableStateOf<AnimalCard?>(null) }
  var mascotState by remember { mutableStateOf(MascotState.IDLE_WAVING) }
  var sessionScore by remember { mutableIntStateOf(0) }
  var burstTrigger by remember { mutableIntStateOf(0) }
  var showMilestone by remember { mutableStateOf(false) }
  var milestoneCount by remember { mutableIntStateOf(0) }
  val scoreScaleAnim = remember { Animatable(1f) }
  val milestoneScaleAnim = remember { Animatable(0f) }
  val scope = rememberCoroutineScope()

  val haptic = LocalHapticFeedback.current
  val groups = listOf("All", "Pets", "Farm", "Jungle", "Water")

  val filteredAnimals = remember(selectedGroup) {
    if (selectedGroup == "All") allAnimals
    else allAnimals.filter { it.group.equals(selectedGroup, ignoreCase = true) }
  }

  fun triggerAnimalSound(animal: AnimalCard) {
    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    activeAnimal = animal
    mascotState = MascotState.CELEBRATING
    sessionScore++
    burstTrigger++

    // Animate score counter bounce
    scope.launch {
      scoreScaleAnim.animateTo(1.35f, spring(dampingRatio = 0.3f))
      scoreScaleAnim.animateTo(1.0f, spring(dampingRatio = 0.5f))
    }

    // Check for milestone (every 5 taps)
    val isMilestone = sessionScore > 0 && sessionScore % 5 == 0
    if (isMilestone) {
      milestoneCount = sessionScore
      showMilestone = true
      scope.launch {
        milestoneScaleAnim.snapTo(0f)
        milestoneScaleAnim.animateTo(1.1f, spring(dampingRatio = 0.5f, stiffness = 300f))
        milestoneScaleAnim.animateTo(1.0f, spring(dampingRatio = 0.6f))
        delay(2200)
        milestoneScaleAnim.animateTo(0f, spring(dampingRatio = 0.7f))
        showMilestone = false
      }
    }

    // Play synthesized animal sound effect
    audioController.playAnimalSound(animal.id)

    // Voice prompt
    scope.launch {
      if (isMilestone) {
        delay(250)
        audioController.playSuccessSound()
        audioController.speak("Awesome job! You explored $sessionScore animals!")
      } else {
        delay(200)
        audioController.speak("${animal.name}! ${animal.name} says ${animal.soundOnomatopoeia}")
      }
    }

    onAwardStars(1)

    scope.launch {
      delay(1500)
      if (!showMilestone) {
        mascotState = MascotState.IDLE_WAVING
      }
    }
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🐶 Animal World",
        showHome = true,
        onHomeClick = onHomeClick,
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFE3F2FD)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE3F2FD),
              Color(0xFFFFFDE7),
              Color.White
            )
          )
        )
    ) {
      Column(
        modifier = Modifier.fillMaxSize()
      ) {
        // Banner Header with Mascot & Quiz Toggle
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          shape = RoundedCornerShape(28.dp),
          color = Color.White,
          shadowElevation = 4.dp
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              BoboMascot(
                size = 76.dp,
                state = mascotState
              )

              Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                  text = activeAnimal?.let { "${it.emoji} ${it.name}" } ?: "Tap an Animal! 🐾",
                  style = MaterialTheme.typography.titleLarge,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF37474F)
                )

                Text(
                  text = activeAnimal?.let { "Says ${it.soundOnomatopoeia}" } ?: "Listen to names & sounds",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Medium,
                  color = Color(0xFF42A5F5)
                )
              }
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              // Score Counter Badge
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFE8F5E9),
                border = BorderStroke(1.5.dp, Color(0xFF66BB6A)),
                shadowElevation = 2.dp,
                modifier = Modifier
                  .scale(scoreScaleAnim.value)
                  .testTag("animal_score_counter")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "⭐ $sessionScore",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = Color(0xFF2E7D32)
                  )
                }
              }

              // Quiz mode switch button
              Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFFFF8E1),
                border = BorderStroke(1.5.dp, Color(0xFFFFB300)),
                shadowElevation = 2.dp,
                modifier = Modifier
                  .clip(RoundedCornerShape(20.dp))
                  .clickable { onStartQuizMode() }
                  .testTag("animal_quiz_toggle")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = "Quiz Mode",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(18.dp)
                  )
                  Text(
                    text = " Quiz",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFFFF9800)
                  )
                }
              }
            }
          }
        }

        // Category Filter Chips
        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(groups) { group ->
            val isSelected = selectedGroup == group
            FilterChip(
              selected = isSelected,
              onClick = { selectedGroup = group },
              label = {
                Text(
                  text = group,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color(0xFF42A5F5),
                selectedLabelColor = Color.White,
                containerColor = Color.White,
                labelColor = Color(0xFF546E7A)
              ),
              shape = RoundedCornerShape(20.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Animal Cards Grid
        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(16.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp),
          modifier = Modifier.fillMaxSize()
        ) {
          items(filteredAnimals) { animal ->
            AnimalCardItem(
              animal = animal,
              isActive = activeAnimal?.id == animal.id,
              onClick = { triggerAnimalSound(animal) }
            )
          }
        }
      }

      // Visual reward star/confetti particle burst overlay on tap
      TapStarBurstOverlay(triggerKey = burstTrigger)

      // Milestone Celebration Overlay
      if (showMilestone) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable { showMilestone = false },
          contentAlignment = Alignment.Center
        ) {
          Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            border = BorderStroke(3.dp, Color(0xFFFFB300)),
            shadowElevation = 16.dp,
            modifier = Modifier
              .scale(milestoneScaleAnim.value)
              .padding(24.dp)
              .testTag("milestone_celebration_card")
          ) {
            Column(
              modifier = Modifier
                .padding(horizontal = 28.dp, vertical = 24.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = "🎉 ⭐ 🎈",
                fontSize = 40.sp,
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = "SUPER EXPLORER!",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFF9800),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = "You've tapped $milestoneCount animals!",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF37474F),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(16.dp))

              Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFFFF8E1),
                border = BorderStroke(1.dp, Color(0xFFFFE082))
              ) {
                Text(
                  text = "⭐ Keep Exploring! ⭐",
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 14.sp,
                  color = Color(0xFFF57C00),
                  modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun AnimalCardItem(
  animal: AnimalCard,
  isActive: Boolean,
  onClick: () -> Unit
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scope = rememberCoroutineScope()
  val tapScaleAnim = remember { Animatable(1f) }
  val haptic = LocalHapticFeedback.current

  val pressScale by animateFloatAsState(
    targetValue = if (isPressed) 0.92f else 1f,
    animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
    label = "PressScale"
  )

  val currentScale = pressScale * tapScaleAnim.value
  val currentElevation = if (isPressed || isActive) 12.dp else 4.dp
  val cardBorder = if (isPressed || isActive) {
    BorderStroke(2.5.dp, Color(animal.cardColorHex))
  } else {
    BorderStroke(1.dp, Color(animal.cardColorHex).copy(alpha = 0.2f))
  }

  Surface(
    shape = RoundedCornerShape(28.dp),
    color = Color(animal.bgLightHex),
    shadowElevation = currentElevation,
    border = cardBorder,
    modifier = Modifier
      .scale(currentScale)
      .aspectRatio(0.95f)
      .testTag("animal_card_${animal.id}")
      .clickable(
        interactionSource = interactionSource,
        indication = null
      ) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        scope.launch {
          tapScaleAnim.animateTo(0.88f, spring(dampingRatio = 0.3f))
          tapScaleAnim.animateTo(1.0f, spring(dampingRatio = 0.5f))
          onClick()
        }
      }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color(animal.cardColorHex).copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Sound",
            tint = Color(animal.cardColorHex),
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Box(
        modifier = Modifier
          .size(72.dp)
          .clip(CircleShape)
          .background(Color.White),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = animal.emoji,
          fontSize = 44.sp
        )
      }

      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = animal.name,
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF37474F)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(animal.cardColorHex).copy(alpha = 0.15f)
        ) {
          Text(
            text = animal.soundOnomatopoeia,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(animal.cardColorHex),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
          )
        }
      }
    }
  }
}
