package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.data.models.SoundBucket
import com.example.data.models.SoundSortGameItem
import com.example.data.models.SoundSortRound
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.components.TapStarBurstOverlay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SoundSortingGameScreen(
  totalStars: Int,
  learningStreak: Int = 0,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onBackClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val rounds = remember { GameContentRepository.getSoundSortingRounds() }
  var currentRoundIndex by remember { mutableIntStateOf(0) }
  val activeRound = rounds.getOrElse(currentRoundIndex) { rounds.first() }

  val coroutineScope = rememberCoroutineScope()
  val haptic = LocalHapticFeedback.current

  // Unplaced items in this round
  val remainingItems = remember { mutableStateListOf<SoundSortGameItem>() }
  val bucketAItems = remember { mutableStateListOf<SoundSortGameItem>() }
  val bucketBItems = remember { mutableStateListOf<SoundSortGameItem>() }

  var isRoundCompleted by remember { mutableStateOf(false) }
  var mascotState by remember { mutableStateOf(MascotState.IDLE_WAVING) }
  var burstTriggerKey by remember { mutableIntStateOf(0) }
  var activeDraggedItemId by remember { mutableStateOf<String?>(null) }
  var wrongFeedbackId by remember { mutableStateOf<String?>(null) }

  // Reset round state
  fun loadRound(round: SoundSortRound) {
    remainingItems.clear()
    remainingItems.addAll(round.items.shuffled())
    bucketAItems.clear()
    bucketBItems.clear()
    isRoundCompleted = false
    activeDraggedItemId = null
    wrongFeedbackId = null
    mascotState = MascotState.IDLE_WAVING
    audioController.speak("${round.title}. ${round.description}")
  }

  LaunchedEffect(currentRoundIndex) {
    loadRound(activeRound)
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        streak = learningStreak,
        title = "🎯 Sound Sorter Game",
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
              Color(0xFFE0F2F1)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Navigation Bar: Back button, Level Tabs, Sound Instruction
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 3.dp,
            modifier = Modifier
              .clickable {
                audioController.playTapSound()
                onBackClick()
              }
              .testTag("sound_sort_back_btn")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFFE65100),
              modifier = Modifier.padding(10.dp)
            )
          }

          // Level Switcher Pills
          Row(
            modifier = Modifier
              .horizontalScroll(rememberScrollState())
              .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            rounds.forEachIndexed { idx, round ->
              val isSelected = (idx == currentRoundIndex)
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) Color(0xFFFF8F00) else Color.White,
                border = androidx.compose.foundation.BorderStroke(
                  1.5.dp,
                  if (isSelected) Color(0xFFE65100) else Color(0xFFFFE082)
                ),
                shadowElevation = if (isSelected) 4.dp else 1.dp,
                modifier = Modifier
                  .clickable {
                    currentRoundIndex = idx
                    audioController.playTapSound()
                  }
                  .testTag("round_tab_$idx")
              ) {
                Text(
                  text = "Round ${idx + 1}: ${round.bucketA.sound} vs ${round.bucketB.sound}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else Color(0xFF5D4037),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Instruction Card with Mascot & Speaker Button
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.White,
          shadowElevation = 4.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            BoboMascot(
              size = 52.dp,
              state = mascotState
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = activeRound.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFE65100)
              )
              Text(
                text = "Drag or tap items into the matching beginning sound basket!",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF78909C)
              )
            }

            IconButton(
              onClick = {
                audioController.speak(activeRound.description)
                audioController.playTapSound()
              },
              modifier = Modifier
                .background(Color(0xFFFFF3E0), CircleShape)
                .size(38.dp)
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "Hear prompt",
                tint = Color(0xFFE65100),
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // --- TWO SOUND CONTAINERS / BASKETS ---
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          // Basket A (e.g. /m/)
          Box(modifier = Modifier.weight(1f)) {
            SoundBucketContainer(
              bucket = activeRound.bucketA,
              collectedItems = bucketAItems,
              isHovered = (activeDraggedItemId != null),
              audioController = audioController,
              onDropOrTapItem = { item ->
                handleItemPlacement(
                  item = item,
                  targetBucket = activeRound.bucketA,
                  otherBucket = activeRound.bucketB,
                  remainingItems = remainingItems,
                  bucketItems = bucketAItems,
                  audioController = audioController,
                  haptic = haptic,
                  onSuccess = {
                    burstTriggerKey++
                    onAwardStars(1)
                    if (remainingItems.isEmpty()) {
                      isRoundCompleted = true
                      mascotState = MascotState.CELEBRATING
                      audioController.playRewardSound()
                      audioController.speak("Super sorting! You sorted all words for ${activeRound.bucketA.sound} and ${activeRound.bucketB.sound}!")
                      onAwardStars(5)
                    }
                  },
                  onFail = {
                    wrongFeedbackId = item.id
                    coroutineScope.launch {
                      delay(1200)
                      wrongFeedbackId = null
                    }
                  }
                )
              }
            )
          }

          // Basket B (e.g. /s/)
          Box(modifier = Modifier.weight(1f)) {
            SoundBucketContainer(
              bucket = activeRound.bucketB,
              collectedItems = bucketBItems,
              isHovered = (activeDraggedItemId != null),
              audioController = audioController,
              onDropOrTapItem = { item ->
                handleItemPlacement(
                  item = item,
                  targetBucket = activeRound.bucketB,
                  otherBucket = activeRound.bucketA,
                  remainingItems = remainingItems,
                  bucketItems = bucketBItems,
                  audioController = audioController,
                  haptic = haptic,
                  onSuccess = {
                    burstTriggerKey++
                    onAwardStars(1)
                    if (remainingItems.isEmpty()) {
                      isRoundCompleted = true
                      mascotState = MascotState.CELEBRATING
                      audioController.playRewardSound()
                      audioController.speak("Super sorting! You sorted all words for ${activeRound.bucketA.sound} and ${activeRound.bucketB.sound}!")
                      onAwardStars(5)
                    }
                  },
                  onFail = {
                    wrongFeedbackId = item.id
                    coroutineScope.launch {
                      delay(1200)
                      wrongFeedbackId = null
                    }
                  }
                )
              }
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // --- UNSORTED DRAGGABLE ITEMS SECTION ---
        Surface(
          shape = RoundedCornerShape(22.dp),
          color = Color(0xFFFFFFFF),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFD54F)),
          shadowElevation = 4.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📦", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (remainingItems.isNotEmpty()) "Items to Sort (${remainingItems.size} remaining):" else "🎉 All Items Sorted!",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF5D4037)
                )
              }

              if (remainingItems.isNotEmpty()) {
                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = Color(0xFFFFF3E0)
                ) {
                  Text(
                    text = "Swipe Left/Right 👆",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (remainingItems.isEmpty()) {
              // Round Completion Banner
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text("🌟 Round Complete! 🌟", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF2E7D32))
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "You sorted all ${activeRound.bucketA.sound} and ${activeRound.bucketB.sound} words perfectly!",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium,
                  color = Color(0xFF388E3C),
                  textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                  horizontalArrangement = Arrangement.spacedBy(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Button(
                    onClick = {
                      loadRound(activeRound)
                      audioController.playTapSound()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00)),
                    shape = RoundedCornerShape(20.dp)
                  ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Replay Round 🔄", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }

                  if (currentRoundIndex < rounds.size - 1) {
                    Button(
                      onClick = {
                        currentRoundIndex++
                        audioController.playRewardSound()
                      },
                      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                      shape = RoundedCornerShape(20.dp)
                    ) {
                      Text("Next Challenge 🚀", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                  }
                }
              }
            } else {
              // Flow of Draggable Cards
              FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                remainingItems.forEach { item ->
                  val isWrong = (wrongFeedbackId == item.id)

                  DraggableSoundCard(
                    item = item,
                    isWrongFeedback = isWrong,
                    bucketA = activeRound.bucketA,
                    bucketB = activeRound.bucketB,
                    audioController = audioController,
                    onDragStart = { activeDraggedItemId = item.id },
                    onDragEnd = { activeDraggedItemId = null },
                    onDroppedLeft = {
                      handleItemPlacement(
                        item = item,
                        targetBucket = activeRound.bucketA,
                        otherBucket = activeRound.bucketB,
                        remainingItems = remainingItems,
                        bucketItems = bucketAItems,
                        audioController = audioController,
                        haptic = haptic,
                        onSuccess = {
                          burstTriggerKey++
                          onAwardStars(1)
                          if (remainingItems.isEmpty()) {
                            isRoundCompleted = true
                            mascotState = MascotState.CELEBRATING
                            audioController.playRewardSound()
                            audioController.speak("Super sorting! You sorted all words for ${activeRound.bucketA.sound} and ${activeRound.bucketB.sound}!")
                            onAwardStars(5)
                          }
                        },
                        onFail = {
                          wrongFeedbackId = item.id
                          coroutineScope.launch {
                            delay(1200)
                            wrongFeedbackId = null
                          }
                        }
                      )
                    },
                    onDroppedRight = {
                      handleItemPlacement(
                        item = item,
                        targetBucket = activeRound.bucketB,
                        otherBucket = activeRound.bucketA,
                        remainingItems = remainingItems,
                        bucketItems = bucketBItems,
                        audioController = audioController,
                        haptic = haptic,
                        onSuccess = {
                          burstTriggerKey++
                          onAwardStars(1)
                          if (remainingItems.isEmpty()) {
                            isRoundCompleted = true
                            mascotState = MascotState.CELEBRATING
                            audioController.playRewardSound()
                            audioController.speak("Super sorting! You sorted all words for ${activeRound.bucketA.sound} and ${activeRound.bucketB.sound}!")
                            onAwardStars(5)
                          }
                        },
                        onFail = {
                          wrongFeedbackId = item.id
                          coroutineScope.launch {
                            delay(1200)
                            wrongFeedbackId = null
                          }
                        }
                      )
                    }
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))
      }

      // Confetti celebration overlay on round complete
      CelebrationOverlay(
        visible = isRoundCompleted,
        message = "Awesome Sound Sorting! 🌟",
        starsAwarded = 5,
        onDismiss = { isRoundCompleted = false }
      )

      // Star Burst Particle Overlay on correct placements
      TapStarBurstOverlay(triggerKey = burstTriggerKey)
    }
  }
}

/**
 * Handles validation of whether dropped item belongs to the selected sound bucket
 */
private fun handleItemPlacement(
  item: SoundSortGameItem,
  targetBucket: SoundBucket,
  otherBucket: SoundBucket,
  remainingItems: MutableList<SoundSortGameItem>,
  bucketItems: MutableList<SoundSortGameItem>,
  audioController: AudioController,
  haptic: androidx.compose.ui.hapticfeedback.HapticFeedback,
  onSuccess: () -> Unit,
  onFail: () -> Unit
) {
  val isMatch = item.targetLetter.equals(targetBucket.letter, ignoreCase = true) ||
      item.targetSound.equals(targetBucket.sound, ignoreCase = true)

  if (isMatch) {
    // 🌟 CORRECT PLACEMENT
    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    audioController.playMatchSound()
    audioController.speak("Yes! ${item.word} starts with ${targetBucket.sound}!")
    remainingItems.remove(item)
    bucketItems.add(item)
    onSuccess()
  } else {
    // ❌ INCORRECT PLACEMENT
    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    audioController.playTapSound()
    audioController.speak("Oops! ${item.word} starts with ${otherBucket.sound}, not ${targetBucket.sound}! Try the ${otherBucket.letter} basket!")
    onFail()
  }
}

/**
 * Sound Bucket Container: visual drop target with 3D basket styling, counter, and list of collected items
 */
@Composable
fun SoundBucketContainer(
  bucket: SoundBucket,
  collectedItems: List<SoundSortGameItem>,
  isHovered: Boolean,
  audioController: AudioController,
  onDropOrTapItem: (SoundSortGameItem) -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "hoverGlow")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = if (isHovered) 1.04f else 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color(bucket.bgHex),
    border = androidx.compose.foundation.BorderStroke(
      width = if (isHovered) 3.dp else 2.dp,
      color = if (isHovered) Color(bucket.colorHex) else Color(bucket.colorHex).copy(alpha = 0.6f)
    ),
    shadowElevation = if (isHovered) 8.dp else 4.dp,
    modifier = Modifier
      .fillMaxWidth()
      .scale(if (isHovered) pulseScale else 1f)
      .clickable {
        audioController.playTapSound()
        audioController.speak("Letter ${bucket.letter} says ${bucket.sound}! Like ${bucket.name}")
      }
      .testTag("bucket_${bucket.letter}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Header: Emoji, Sound, Speaker
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = CircleShape,
          color = Color(bucket.colorHex),
          modifier = Modifier.size(34.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(bucket.emoji, fontSize = 16.sp)
          }
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color.White,
          shadowElevation = 2.dp
        ) {
          Text(
            text = "${collectedItems.size} in Basket",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(bucket.colorHex),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Big Letter & Phoneme Sound
      Text(
        text = bucket.letter,
        fontSize = 36.sp,
        fontWeight = FontWeight.Black,
        color = Color(bucket.colorHex)
      )

      Text(
        text = "Sound ${bucket.sound}",
        fontSize = 13.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(bucket.colorHex)
      )

      Text(
        text = bucket.name,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF5D4037)
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Basket Collection Shelf
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.85f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(bucket.colorHex).copy(alpha = 0.3f)),
        modifier = Modifier
          .fillMaxWidth()
          .height(72.dp)
      ) {
        if (collectedItems.isEmpty()) {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Drop /${bucket.sound}/ here 📥",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF9E9E9E),
              textAlign = TextAlign.Center
            )
          }
        } else {
          Row(
            modifier = Modifier
              .fillMaxSize()
              .horizontalScroll(rememberScrollState())
              .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            collectedItems.forEach { collected ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(bucket.colorHex).copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(bucket.colorHex)),
                modifier = Modifier.clickable {
                  audioController.playTapSound()
                  audioController.speak("${collected.word} starts with ${bucket.sound}!")
                }
              ) {
                Column(
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Text(collected.emoji, fontSize = 20.sp)
                  Text(
                    text = collected.word,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(bucket.colorHex)
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

/**
 * Draggable Sound Card with drag gestures, tap speech, and shake feedback on error
 */
@Composable
fun DraggableSoundCard(
  item: SoundSortGameItem,
  isWrongFeedback: Boolean,
  bucketA: SoundBucket,
  bucketB: SoundBucket,
  audioController: AudioController,
  onDragStart: () -> Unit,
  onDragEnd: () -> Unit,
  onDroppedLeft: () -> Unit,
  onDroppedRight: () -> Unit
) {
  val offsetX = remember { Animatable(0f) }
  val offsetY = remember { Animatable(0f) }
  val coroutineScope = rememberCoroutineScope()

  // Shake / Wobble animation when incorrect
  val shakeAnim = remember { Animatable(0f) }
  LaunchedEffect(isWrongFeedback) {
    if (isWrongFeedback) {
      shakeAnim.animateTo(
        targetValue = 1f,
        animationSpec = tween(100)
      )
      shakeAnim.animateTo(
        targetValue = -1f,
        animationSpec = tween(100)
      )
      shakeAnim.animateTo(
        targetValue = 0.5f,
        animationSpec = tween(100)
      )
      shakeAnim.animateTo(
        targetValue = 0f,
        animationSpec = tween(100)
      )
    }
  }

  val shakeOffset = shakeAnim.value * 16f

  // Card Appearance
  Surface(
    shape = RoundedCornerShape(18.dp),
    color = if (isWrongFeedback) Color(0xFFFFEBEE) else Color(0xFFFFF8E1),
    border = androidx.compose.foundation.BorderStroke(
      width = if (isWrongFeedback) 2.5.dp else 1.5.dp,
      color = if (isWrongFeedback) Color(0xFFE53935) else Color(0xFFFFB74D)
    ),
    shadowElevation = if (offsetX.value != 0f || offsetY.value != 0f) 10.dp else 4.dp,
    modifier = Modifier
      .size(width = 100.dp, height = 110.dp)
      .offset {
        IntOffset(
          x = (offsetX.value + shakeOffset).roundToInt(),
          y = offsetY.value.roundToInt()
        )
      }
      .zIndex(if (offsetX.value != 0f || offsetY.value != 0f) 99f else 1f)
      .pointerInput(item.id) {
        detectDragGestures(
          onDragStart = {
            onDragStart()
            audioController.playTapSound()
          },
          onDragEnd = {
            onDragEnd()
            val currentX = offsetX.value
            // Drag left vs drag right threshold check
            if (currentX < -70f) {
              onDroppedLeft()
            } else if (currentX > 70f) {
              onDroppedRight()
            }
            // Spring back
            coroutineScope.launch {
              offsetX.animateTo(0f, spring())
            }
            coroutineScope.launch {
              offsetY.animateTo(0f, spring())
            }
          },
          onDragCancel = {
            onDragEnd()
            coroutineScope.launch { offsetX.animateTo(0f, spring()) }
            coroutineScope.launch { offsetY.animateTo(0f, spring()) }
          },
          onDrag = { change, dragAmount ->
            change.consume()
            coroutineScope.launch {
              offsetX.snapTo(offsetX.value + dragAmount.x)
              offsetY.snapTo(offsetY.value + dragAmount.y)
            }
          }
        )
      }
      .clickable {
        audioController.playTapSound()
        audioController.speak(item.hint.ifEmpty { "${item.word}! Starts with ${item.targetSound}." })
      }
      .testTag("sort_item_${item.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = item.emoji, fontSize = 34.sp)
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = item.word,
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraBold,
        color = if (isWrongFeedback) Color(0xFFC62828) else Color(0xFF4E342E),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(4.dp))

      // Direct Quick Tap Target Buttons (👈 A or B 👉)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Surface(
          shape = CircleShape,
          color = Color(bucketA.colorHex),
          modifier = Modifier
            .size(22.dp)
            .clickable {
              audioController.playTapSound()
              onDroppedLeft()
            }
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(bucketA.letter, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
          }
        }

        Surface(
          shape = CircleShape,
          color = Color(bucketB.colorHex),
          modifier = Modifier
            .size(22.dp)
            .clickable {
              audioController.playTapSound()
              onDroppedRight()
            }
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(bucketB.letter, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
          }
        }
      }
    }
  }
}
