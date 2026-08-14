package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Interactive Tactile Blend Slider for early readers & toddlers.
 * Visually contracts phoneme letters from separated blocks into a unified word card
 * as the child drags or swipes across the tactile slider track.
 */
@Composable
fun BlendSlider(
  letters: List<String>,
  sounds: List<String>,
  targetWord: String,
  targetEmoji: String = "✨",
  audioController: AudioController,
  onMergedSuccess: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val phonemeCount = letters.size.coerceAtLeast(1)
  val blendProgress = remember { Animatable(0f) }
  val coroutineScope = rememberCoroutineScope()
  var lastSpokenSegment by remember { mutableIntStateOf(-1) }
  var isCompleted by remember { mutableStateOf(false) }
  var isAutoPlaying by remember { mutableStateOf(false) }
  var autoPlayJob by remember { mutableStateOf<Job?>(null) }
  var isSlowMode by remember { mutableStateOf(false) }

  // Reset when word changes
  LaunchedEffect(targetWord) {
    autoPlayJob?.cancel()
    blendProgress.snapTo(0f)
    lastSpokenSegment = -1
    isCompleted = false
    isAutoPlaying = false
  }

  // Active highlighted phoneme index based on slider position
  val activePhonemeIndex by remember(phonemeCount) {
    derivedStateOf {
      val prog = blendProgress.value
      if (prog <= 0.05f) {
        -1
      } else if (prog >= 0.95f) {
        phonemeCount // Fully merged
      } else {
        val segmentSize = 0.9f / phonemeCount
        val seg = ((prog - 0.05f) / segmentSize).toInt()
        seg.coerceIn(0, phonemeCount - 1)
      }
    }
  }

  // Speak sound transitions as slider crosses milestones
  LaunchedEffect(activePhonemeIndex) {
    if (activePhonemeIndex in 0 until phonemeCount && activePhonemeIndex != lastSpokenSegment) {
      lastSpokenSegment = activePhonemeIndex
      val letter = letters[activePhonemeIndex]
      val sound = sounds.getOrElse(activePhonemeIndex) { letter }
      audioController.playTapSound()
      audioController.speak(sound)
    } else if (activePhonemeIndex == phonemeCount && !isCompleted) {
      isCompleted = true
      audioController.playRewardSound()
      audioController.speak("$targetWord! $targetWord!")
      onMergedSuccess()
    }
  }

  // Pulsing animation for active glow
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val glowPulse by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glowPulse"
  )

  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color(0xFFFFFDF5),
    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFD54F)),
    shadowElevation = 6.dp,
    modifier = modifier
      .fillMaxWidth()
      .testTag("blend_slider_container")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Header Label
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFF8F00),
            modifier = Modifier.size(28.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("🧩", fontSize = 14.sp)
            }
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "Tactile Blend Slider",
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFE65100)
            )
            Text(
              text = if (isCompleted) "🎉 Merged! Tap to hear word" else "Swipe right to fuse sounds! ➡️",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (isCompleted) Color(0xFF2E7D32) else Color(0xFF8D6E63)
            )
          }
        }

        // Speed Mode Toggle (Turtle vs Rabbit)
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = if (isSlowMode) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
          border = androidx.compose.foundation.BorderStroke(1.dp, if (isSlowMode) Color(0xFF81C784) else Color(0xFFFFB74D)),
          modifier = Modifier
            .clickable {
              isSlowMode = !isSlowMode
              audioController.playTapSound()
            }
            .testTag("blend_speed_toggle")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(if (isSlowMode) "🐢 Slow" else "⚡ Fast", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5D4037))
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // --- VISUAL LETTERS MERGING STAGE ---
      // When blendProgress is 0: spread out with wide gaps.
      // When blendProgress approaches 1.0: gaps shrink to 0 and blocks merge into a golden single card!
      val currentProgress = blendProgress.value
      val maxGapDp = 20.dp
      val currentGapDp = maxGapDp * (1f - currentProgress).coerceIn(0f, 1f)

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(110.dp)
          .clip(RoundedCornerShape(20.dp))
          .background(
            Brush.verticalGradient(
              colors = if (isCompleted) {
                listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))
              } else {
                listOf(Color(0xFFFFF8E1), Color(0xFFFFECB3))
              }
            )
          )
          .border(
            width = if (isCompleted) 2.5.dp else 1.dp,
            color = if (isCompleted) Color(0xFF4CAF50) else Color(0xFFFFE082),
            shape = RoundedCornerShape(20.dp)
          )
          .clickable {
            if (isCompleted) {
              audioController.playRewardSound()
              audioController.speak("$targetWord! $targetEmoji")
            } else {
              audioController.speak("Slide the star to blend the sounds into $targetWord!")
            }
          },
        contentAlignment = Alignment.Center
      ) {
        // Background Connection Bridge Waves when separated
        if (currentProgress < 0.95f) {
          Canvas(modifier = Modifier.fillMaxSize()) {
            val waveY = size.height * 0.78f
            val path = Path()
            path.moveTo(size.width * 0.15f, waveY)
            path.quadraticTo(
              size.width * 0.5f,
              waveY + 12f * (1f - currentProgress),
              size.width * 0.85f,
              waveY
            )
            drawPath(
              path = path,
              color = Color(0xFFFFB300).copy(alpha = 0.6f * (1f - currentProgress)),
              style = Stroke(
                width = 4f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
              )
            )
          }
        }

        // Letter Blocks Row
        Row(
          horizontalArrangement = Arrangement.spacedBy(currentGapDp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          letters.forEachIndexed { idx, letter ->
            val phoneme = sounds.getOrElse(idx) { letter }
            val isActive = (activePhonemeIndex == idx)
            val isMerged = (currentProgress >= 0.95f)

            val tileScale = if (isActive) glowPulse else 1f
            val tileBgColor = when {
              isMerged -> Color(0xFF43A047)
              isActive -> Color(0xFFFF6F00)
              else -> Color(0xFFFF9800)
            }

            Surface(
              shape = if (isMerged) {
                when (idx) {
                  0 -> RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                  letters.lastIndex -> RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                  else -> RoundedCornerShape(0.dp)
                }
              } else {
                RoundedCornerShape(16.dp)
              },
              color = tileBgColor,
              shadowElevation = if (isActive || isMerged) 6.dp else 3.dp,
              modifier = Modifier
                .size(width = 56.dp, height = 72.dp)
                .scale(tileScale)
                .clickable {
                  audioController.playTapSound()
                  audioController.speak("$letter says $phoneme")
                }
                .testTag("blend_tile_$letter")
            ) {
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
              ) {
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = letter.uppercase(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                  )
                  Text(
                    text = "/$phoneme/",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFF9C4)
                  )
                }
              }
            }
          }
        }

        // Celebratory target emoji and word badge when fully blended
        if (isCompleted) {
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(8.dp)
          ) {
            Surface(
              shape = CircleShape,
              color = Color.White,
              shadowElevation = 3.dp
            ) {
              Text(
                text = targetEmoji,
                fontSize = 20.sp,
                modifier = Modifier.padding(4.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // --- TACTILE DRAG SLIDER TRACK ---
      BoxWithConstraints(
        modifier = Modifier
          .fillMaxWidth()
          .height(64.dp)
          .testTag("blend_slider_track")
      ) {
        val density = LocalDensity.current
        val trackWidthPx = with(density) { maxWidth.toPx() }
        val thumbSizePx = with(density) { 56.dp.toPx() }
        val maxDragPx = (trackWidthPx - thumbSizePx).coerceAtLeast(1f)

        // Track Background
        Surface(
          shape = RoundedCornerShape(32.dp),
          color = Color(0xFFFFECB3),
          border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFCA28)),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .align(Alignment.Center)
        ) {
          Box(modifier = Modifier.fillMaxSize()) {
            // Filled progress portion
            Box(
              modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = blendProgress.value.coerceIn(0f, 1f))
                .background(
                  Brush.horizontalGradient(
                    colors = listOf(
                      Color(0xFFFF9800),
                      Color(0xFFFFB74D),
                      if (isCompleted) Color(0xFF4CAF50) else Color(0xFFFF7043)
                    )
                  )
                )
            )

            // Milestone sound dots along the track
            Row(
              modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              letters.forEachIndexed { i, l ->
                val isReached = (blendProgress.value >= (i.toFloat() / (phonemeCount - 1).coerceAtLeast(1)))
                Surface(
                  shape = CircleShape,
                  color = if (isReached) Color.White else Color(0xFFFFD54F),
                  modifier = Modifier.size(14.dp)
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    if (isReached) {
                      Box(
                        modifier = Modifier
                          .size(6.dp)
                          .clip(CircleShape)
                          .background(Color(0xFFE65100))
                      )
                    }
                  }
                }
              }
            }

            // Instructional Track Text
            if (blendProgress.value < 0.2f) {
              Row(
                modifier = Modifier
                  .align(Alignment.CenterEnd)
                  .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Slide to Blend 👉",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFFBF360C)
                )
              }
            }
          }
        }

        // Draggable Tactile Thumb (Friendly Star / Caterpillar bead)
        val thumbOffsetPx = (blendProgress.value * maxDragPx).coerceIn(0f, maxDragPx)
        val thumbOffsetDp = with(density) { thumbOffsetPx.toDp() }

        Surface(
          shape = CircleShape,
          color = if (isCompleted) Color(0xFF43A047) else Color(0xFFFF6F00),
          border = androidx.compose.foundation.BorderStroke(3.dp, Color.White),
          shadowElevation = 8.dp,
          modifier = Modifier
            .size(56.dp)
            .offset(x = thumbOffsetDp)
            .align(Alignment.CenterStart)
            .testTag("blend_slider_thumb")
            .pointerInput(targetWord, maxDragPx) {
              detectDragGestures(
                onDragStart = {
                  autoPlayJob?.cancel()
                  isAutoPlaying = false
                },
                onDragEnd = {
                  // Snap to completed if near end, or stay in place
                  if (blendProgress.value > 0.82f) {
                    coroutineScope.launch {
                      blendProgress.animateTo(1f, tween(200, easing = FastOutSlowInEasing))
                    }
                  }
                },
                onDragCancel = {},
                onDrag = { change, dragAmount ->
                  change.consume()
                  val deltaFraction = dragAmount.x / maxDragPx
                  val newProgress = (blendProgress.value + deltaFraction).coerceIn(0f, 1f)
                  coroutineScope.launch {
                    blendProgress.snapTo(newProgress)
                  }
                }
              )
            }
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
          ) {
            if (isCompleted) {
              Icon(
                Icons.Default.AutoAwesome,
                contentDescription = "Merged!",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
              )
            } else {
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
              ) {
                Text("⭐", fontSize = 18.sp)
                Text(
                  "SLIDE",
                  fontSize = 8.sp,
                  fontWeight = FontWeight.Black,
                  color = Color.White
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // --- ACTION CONTROLS: Auto-Blend, Reset, Hear Sounds ---
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Auto-Blend Animated Play Button
        Button(
          onClick = {
            if (isAutoPlaying) {
              autoPlayJob?.cancel()
              isAutoPlaying = false
            } else {
              isAutoPlaying = true
              isCompleted = false
              autoPlayJob = coroutineScope.launch {
                // Reset to start first
                blendProgress.snapTo(0f)
                lastSpokenSegment = -1
                delay(150)

                val stepDuration = if (isSlowMode) 900L else 550L
                val segmentFraction = 0.85f / phonemeCount

                // Step through each phoneme segment smoothly
                for (i in 0 until phonemeCount) {
                  val target = (i + 1) * segmentFraction
                  blendProgress.animateTo(
                    targetValue = target,
                    animationSpec = tween(
                      durationMillis = stepDuration.toInt(),
                      easing = LinearEasing
                    )
                  )
                  delay(stepDuration)
                }

                // Final fusion to 1.0f
                blendProgress.animateTo(
                  targetValue = 1f,
                  animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
                isAutoPlaying = false
              }
            }
          },
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
            .testTag("blend_autoplay_button"),
          shape = RoundedCornerShape(22.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isAutoPlaying) Color(0xFFEF6C00) else Color(0xFFFF8F00),
            contentColor = Color.White
          )
        ) {
          Icon(
            if (isAutoPlaying) Icons.Default.Refresh else Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isAutoPlaying) "Blending..." else "Auto-Blend 🚀",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
          )
        }

        // Reset Button
        Button(
          onClick = {
            autoPlayJob?.cancel()
            isAutoPlaying = false
            isCompleted = false
            lastSpokenSegment = -1
            coroutineScope.launch {
              blendProgress.animateTo(0f, spring())
            }
            audioController.playTapSound()
            audioController.speak("Let's blend again!")
          },
          modifier = Modifier
            .height(44.dp)
            .testTag("blend_reset_button"),
          shape = RoundedCornerShape(22.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFF3E0),
            contentColor = Color(0xFFE65100)
          ),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFB74D))
        ) {
          Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Reset", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // Read Word Button
        IconButton(
          onClick = {
            val soundSeq = sounds.joinToString(" ... ")
            audioController.speak("$soundSeq ... $targetWord!")
            audioController.playMatchSound()
          },
          modifier = Modifier
            .size(44.dp)
            .background(Color(0xFFE8F5E9), CircleShape)
            .border(1.5.dp, Color(0xFF81C784), CircleShape)
            .testTag("blend_speaker_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = "Hear whole blend",
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(22.dp)
          )
        }
      }
    }
  }
}
