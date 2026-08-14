package com.example.ui.components

import androidx.annotation.RawRes
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
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SoundButtonSize(
  val height: Dp,
  val iconSize: Dp,
  val fontSize: TextUnit,
  val cornerRadius: Dp,
  val paddingHorizontal: Dp
) {
  COMPACT(36.dp, 18.dp, 13.sp, 18.dp, 10.dp),
  SMALL(44.dp, 20.dp, 14.sp, 22.dp, 14.dp),
  MEDIUM(52.dp, 24.dp, 16.sp, 26.dp, 18.dp),
  LARGE(64.dp, 30.dp, 19.sp, 32.dp, 22.dp),
  HERO(80.dp, 36.dp, 22.sp, 40.dp, 26.dp)
}

enum class SoundButtonVariant {
  STANDARD,         // Rounded button with icon & label
  LETTER_TILE,      // Chunky square/rounded tile for alphabet & phonemes
  CVC_BLOCK,        // 3-part consonant/vowel/consonant block
  PHONEME_CHIP,     // Small pill chip for individual sounds
  FLOATING_SPEAKER, // Circular floating speaker icon button
  HERO_CARD         // Full width card with emoji, text, and wave animation
}

/**
 * Reusable SoundButton Composable
 * Plays audio corresponding to a sound resource ID or text when tapped.
 * Essential for Phonics modules, Letter Sounds, and CVC Words lessons.
 *
 * @param text The text label to display on the button.
 * @param soundResId Optional raw sound resource ID (e.g. R.raw.letter_sound).
 * @param audioController Optional AudioController instance (defaults to context controller).
 */
@Composable
fun SoundButton(
  text: String,
  modifier: Modifier = Modifier,
  @RawRes soundResId: Int? = null,
  audioController: AudioController? = null,
  phoneticSound: String? = null,
  subtext: String? = null,
  emoji: String? = null,
  variant: SoundButtonVariant = SoundButtonVariant.STANDARD,
  size: SoundButtonSize = SoundButtonSize.MEDIUM,
  accentColor: Color = Color(0xFF00897B),
  backgroundColor: Color? = null,
  enabled: Boolean = true,
  showRipples: Boolean = true,
  hapticFeedbackEnabled: Boolean = true,
  onClick: (() -> Unit)? = null
) {
  val context = LocalContext.current
  val controller = audioController ?: remember(context) { AudioController(context) }

  SoundButton(
    audioController = controller,
    modifier = modifier,
    soundResId = soundResId,
    label = text,
    phoneticSound = phoneticSound,
    subtext = subtext,
    emoji = emoji,
    variant = variant,
    size = size,
    accentColor = accentColor,
    backgroundColor = backgroundColor,
    enabled = enabled,
    showRipples = showRipples,
    hapticFeedbackEnabled = hapticFeedbackEnabled,
    onClick = onClick
  )
}

/**
 * Reusable SoundButton Composable (Full Configuration)
 * Plays audio corresponding to a sound resource ID, letter, word, or sound prompt when tapped.
 */
@Composable
fun SoundButton(
  audioController: AudioController,
  modifier: Modifier = Modifier,
  @RawRes soundResId: Int? = null,
  letter: String? = null,
  word: String? = null,
  soundPrompt: String? = null,
  phoneticSound: String? = null,
  emoji: String? = null,
  label: String? = null,
  subtext: String? = null,
  variant: SoundButtonVariant = SoundButtonVariant.STANDARD,
  size: SoundButtonSize = SoundButtonSize.MEDIUM,
  accentColor: Color = Color(0xFF00897B),
  backgroundColor: Color? = null,
  enabled: Boolean = true,
  showRipples: Boolean = true,
  hapticFeedbackEnabled: Boolean = true,
  onClick: (() -> Unit)? = null
) {
  val coroutineScope = rememberCoroutineScope()
  var isPlayingAnimation by remember { mutableStateOf(false) }
  val scaleAnim = remember { Animatable(1f) }
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()

  // Press reaction scale
  LaunchedEffect(isPressed) {
    if (isPressed && enabled) {
      scaleAnim.animateTo(0.92f, animationSpec = tween(80))
    } else {
      scaleAnim.animateTo(1f, animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f))
    }
  }

  val playAction = {
    if (enabled) {
      coroutineScope.launch {
        isPlayingAnimation = true
        scaleAnim.animateTo(1.08f, animationSpec = tween(120, easing = FastOutSlowInEasing))
        scaleAnim.animateTo(1.0f, animationSpec = spring(dampingRatio = 0.5f, stiffness = 350f))
        delay(900)
        isPlayingAnimation = false
      }

      // Play audio based on provided parameters: Sound Resource ID first, then specific phonics/word/speech fallback
      when {
        soundResId != null && soundResId != 0 -> {
          audioController.playSoundResource(
            soundResId = soundResId,
            fallbackText = soundPrompt ?: label ?: word ?: letter ?: phoneticSound
          )
        }
        letter != null -> {
          audioController.playLetterSound(
            letter = letter,
            phoneme = phoneticSound,
            exampleWord = word
          )
        }
        word != null -> {
          audioController.playCvcSound(
            word = word,
            sounds = if (phoneticSound != null) listOf(phoneticSound) else emptyList()
          )
        }
        soundPrompt != null -> {
          audioController.speak(soundPrompt)
        }
        label != null -> {
          audioController.speak(label)
        }
      }

      onClick?.invoke()
    }
  }

  when (variant) {
    SoundButtonVariant.STANDARD -> {
      StandardSoundButton(
        modifier = modifier
          .scale(scaleAnim.value)
          .testTag("sound_button_${letter ?: word ?: label ?: "action"}"),
        label = label ?: word ?: letter ?: "Play Sound",
        subtext = subtext ?: phoneticSound,
        emoji = emoji,
        size = size,
        accentColor = accentColor,
        backgroundColor = backgroundColor,
        enabled = enabled,
        isPlaying = isPlayingAnimation,
        onClick = playAction
      )
    }

    SoundButtonVariant.LETTER_TILE -> {
      LetterTileSoundButton(
        modifier = modifier
          .scale(scaleAnim.value)
          .testTag("letter_tile_${letter ?: "unknown"}"),
        letter = letter ?: "A",
        phoneticSound = phoneticSound ?: "/æ/",
        emoji = emoji,
        size = size,
        accentColor = accentColor,
        isPlaying = isPlayingAnimation,
        showRipples = showRipples,
        onClick = playAction
      )
    }

    SoundButtonVariant.CVC_BLOCK -> {
      CvcBlockSoundButton(
        modifier = modifier
          .scale(scaleAnim.value)
          .testTag("cvc_block_${word ?: "word"}"),
        word = word ?: "CAT",
        phoneticSound = phoneticSound,
        emoji = emoji,
        accentColor = accentColor,
        isPlaying = isPlayingAnimation,
        onClick = playAction
      )
    }

    SoundButtonVariant.PHONEME_CHIP -> {
      PhonemeChipSoundButton(
        modifier = modifier
          .scale(scaleAnim.value)
          .testTag("phoneme_chip_${phoneticSound ?: letter ?: "sound"}"),
        text = phoneticSound ?: letter ?: word ?: "sound",
        size = size,
        accentColor = accentColor,
        isPlaying = isPlayingAnimation,
        onClick = playAction
      )
    }

    SoundButtonVariant.FLOATING_SPEAKER -> {
      FloatingSpeakerSoundButton(
        modifier = modifier
          .scale(scaleAnim.value)
          .testTag("floating_speaker_${letter ?: word ?: "button"}"),
        size = size,
        accentColor = accentColor,
        isPlaying = isPlayingAnimation,
        showRipples = showRipples,
        onClick = playAction
      )
    }

    SoundButtonVariant.HERO_CARD -> {
      HeroCardSoundButton(
        modifier = modifier
          .scale(scaleAnim.value)
          .testTag("hero_sound_card_${word ?: letter ?: "hero"}"),
        title = word ?: letter ?: "Sound Card",
        phonetic = phoneticSound,
        emoji = emoji,
        description = subtext ?: soundPrompt,
        accentColor = accentColor,
        isPlaying = isPlayingAnimation,
        onClick = playAction
      )
    }
  }
}

/**
 * Specialized Letter Sounds button displaying uppercase, lowercase, phonetic notation & emoji.
 */
@Composable
fun LetterSoundButton(
  letter: String,
  phoneticSound: String,
  audioController: AudioController,
  modifier: Modifier = Modifier,
  @RawRes soundResId: Int? = null,
  emoji: String? = null,
  exampleWord: String? = null,
  accentColor: Color = Color(0xFF00897B),
  size: SoundButtonSize = SoundButtonSize.LARGE,
  onClick: (() -> Unit)? = null
) {
  val cleanUpper = letter.trim().uppercase()
  val cleanLower = letter.trim().lowercase()

  SoundButton(
    audioController = audioController,
    modifier = modifier,
    soundResId = soundResId,
    letter = cleanUpper,
    phoneticSound = phoneticSound,
    word = exampleWord,
    emoji = emoji,
    label = "$cleanUpper$cleanLower",
    subtext = phoneticSound,
    variant = SoundButtonVariant.LETTER_TILE,
    size = size,
    accentColor = accentColor,
    onClick = onClick
  )
}

/**
 * Specialized CVC Words button with segmenting colors for Consonants & Vowels.
 */
@Composable
fun CvcSoundButton(
  word: String,
  audioController: AudioController,
  modifier: Modifier = Modifier,
  @RawRes soundResId: Int? = null,
  letters: List<String> = emptyList(),
  sounds: List<String> = emptyList(),
  emoji: String? = null,
  accentColor: Color = Color(0xFF00ACC1),
  onClick: (() -> Unit)? = null
) {
  val cleanWord = word.trim().uppercase()
  val displayLetters = if (letters.isNotEmpty()) letters else cleanWord.map { it.toString() }

  val coroutineScope = rememberCoroutineScope()
  var isPlaying by remember { mutableStateOf(false) }
  val scaleAnim = remember { Animatable(1f) }

  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color.White,
    shadowElevation = if (isPlaying) 8.dp else 4.dp,
    border = androidx.compose.foundation.BorderStroke(
      2.dp,
      if (isPlaying) accentColor else accentColor.copy(alpha = 0.3f)
    ),
    modifier = modifier
      .scale(scaleAnim.value)
      .clickable {
        coroutineScope.launch {
          isPlaying = true
          scaleAnim.animateTo(1.04f, animationSpec = tween(100))
          scaleAnim.animateTo(1.0f, animationSpec = spring(dampingRatio = 0.5f))
          delay(800)
          isPlaying = false
        }
        if (soundResId != null && soundResId != 0) {
          audioController.playSoundResource(soundResId, cleanWord)
        } else {
          audioController.playCvcSound(
            word = cleanWord,
            letters = displayLetters,
            sounds = sounds,
            slowBlend = true
          )
        }
        onClick?.invoke()
      }
      .testTag("cvc_sound_button_$cleanWord")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE0F7FA),
              Color.White
            )
          )
        )
        .padding(14.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (emoji != null) {
        Text(text = emoji, fontSize = 34.sp)
        Spacer(modifier = Modifier.height(6.dp))
      }

      // Letter Blocks (Consonant - Vowel - Consonant with distinct colors)
      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        displayLetters.forEachIndexed { index, char ->
          val isVowel = char.uppercase() in listOf("A", "E", "I", "O", "U")
          val blockBg = if (isVowel) Color(0xFFFFEBEE) else Color(0xFFE3F2FD)
          val blockBorder = if (isVowel) Color(0xFFEF5350) else Color(0xFF42A5F5)
          val textColor = if (isVowel) Color(0xFFC62828) else Color(0xFF1565C0)

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = blockBg,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, blockBorder),
            modifier = Modifier.size(44.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = char.uppercase(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = textColor
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Hear Sound Play pill
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = accentColor,
        modifier = Modifier.padding(top = 2.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = "Hear $cleanWord",
            tint = Color.White,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "BLEND SOUND 🔊",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
          )
        }
      }
    }
  }
}

// -----------------------------------------
// Sub-variants of SoundButton
// -----------------------------------------

@Composable
private fun StandardSoundButton(
  label: String,
  subtext: String?,
  emoji: String?,
  size: SoundButtonSize,
  accentColor: Color,
  backgroundColor: Color?,
  enabled: Boolean,
  isPlaying: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val containerBg = backgroundColor ?: if (isPlaying) accentColor.copy(alpha = 0.9f) else accentColor
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = if (isPlaying) 1.05f else 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseScale"
  )

  Surface(
    shape = RoundedCornerShape(size.cornerRadius),
    color = containerBg,
    shadowElevation = if (isPlaying) 8.dp else 3.dp,
    modifier = modifier
      .scale(pulseScale)
      .height(size.height)
      .clickable(enabled = enabled, onClick = onClick)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = size.paddingHorizontal),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      if (emoji != null) {
        Text(text = emoji, fontSize = (size.fontSize.value + 4).sp)
        Spacer(modifier = Modifier.width(8.dp))
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
        contentDescription = "Sound",
        tint = Color.White,
        modifier = Modifier.size(size.iconSize)
      )

      Spacer(modifier = Modifier.width(8.dp))

      Column(horizontalAlignment = Alignment.Start) {
        Text(
          text = label,
          fontSize = size.fontSize,
          fontWeight = FontWeight.ExtraBold,
          color = Color.White
        )
        if (subtext != null) {
          Text(
            text = subtext,
            fontSize = (size.fontSize.value - 4).coerceAtLeast(10f).sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.85f)
          )
        }
      }
    }
  }
}

@Composable
private fun LetterTileSoundButton(
  letter: String,
  phoneticSound: String,
  emoji: String?,
  size: SoundButtonSize,
  accentColor: Color,
  isPlaying: Boolean,
  showRipples: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val boxSize = when (size) {
    SoundButtonSize.COMPACT -> 64.dp
    SoundButtonSize.SMALL -> 80.dp
    SoundButtonSize.MEDIUM -> 96.dp
    SoundButtonSize.LARGE -> 116.dp
    SoundButtonSize.HERO -> 140.dp
  }

  Box(
    modifier = modifier.size(boxSize),
    contentAlignment = Alignment.Center
  ) {
    if (isPlaying && showRipples) {
      // Animated audio pulse ripples
      val infinite = rememberInfiniteTransition(label = "audio_ripple")
      val rippleAlpha by infinite.animateFloat(
        initialValue = 0.7f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
          animation = tween(700, easing = LinearEasing),
          repeatMode = RepeatMode.Restart
        ),
        label = "rippleAlpha"
      )
      val rippleRadius by infinite.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
          animation = tween(700, easing = FastOutSlowInEasing),
          repeatMode = RepeatMode.Restart
        ),
        label = "rippleRadius"
      )

      Canvas(modifier = Modifier.size(boxSize * 1.3f)) {
        drawCircle(
          color = accentColor.copy(alpha = rippleAlpha),
          radius = (this.size.width / 2f) * rippleRadius,
          style = Stroke(width = 4.dp.toPx())
        )
      }
    }

    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      shadowElevation = if (isPlaying) 10.dp else 4.dp,
      border = androidx.compose.foundation.BorderStroke(
        2.dp,
        if (isPlaying) accentColor else Color(0xFFCFD8DC)
      ),
      modifier = Modifier
        .size(boxSize)
        .clickable(onClick = onClick)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(
                accentColor.copy(alpha = 0.12f),
                Color.White
              )
            )
          )
          .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        if (emoji != null) {
          Text(text = emoji, fontSize = (boxSize.value * 0.28f).sp)
        }

        Text(
          text = "${letter.uppercase()}${letter.lowercase()}",
          fontSize = (boxSize.value * 0.32f).sp,
          fontWeight = FontWeight.Black,
          color = accentColor
        )

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = accentColor.copy(alpha = 0.15f)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = null,
              tint = accentColor,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = phoneticSound,
              fontSize = (boxSize.value * 0.14f).sp,
              fontWeight = FontWeight.Bold,
              color = accentColor
            )
          }
        }
      }
    }
  }
}

@Composable
private fun CvcBlockSoundButton(
  word: String,
  phoneticSound: String?,
  emoji: String?,
  accentColor: Color,
  isPlaying: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(20.dp),
    color = Color.White,
    shadowElevation = if (isPlaying) 6.dp else 2.dp,
    border = androidx.compose.foundation.BorderStroke(1.5.dp, accentColor.copy(alpha = 0.4f)),
    modifier = modifier
      .clickable(onClick = onClick)
  ) {
    Row(
      modifier = Modifier
        .background(
          Brush.horizontalGradient(
            colors = listOf(
              Color(0xFFE0F7FA),
              Color.White,
              Color(0xFFE0F7FA)
            )
          )
        )
        .padding(horizontal = 14.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      if (emoji != null) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
      }

      Text(
        text = word.uppercase(),
        fontSize = 18.sp,
        fontWeight = FontWeight.Black,
        color = Color(0xFF006064)
      )

      if (phoneticSound != null) {
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = phoneticSound,
          fontSize = 13.sp,
          fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
          color = Color(0xFF00838F)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Surface(
        shape = CircleShape,
        color = accentColor
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.VolumeUp,
          contentDescription = "Sound",
          tint = Color.White,
          modifier = Modifier
            .padding(6.dp)
            .size(16.dp)
        )
      }
    }
  }
}

@Composable
private fun PhonemeChipSoundButton(
  text: String,
  size: SoundButtonSize,
  accentColor: Color,
  isPlaying: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = if (isPlaying) accentColor else accentColor.copy(alpha = 0.15f),
    border = androidx.compose.foundation.BorderStroke(1.5.dp, accentColor),
    modifier = modifier.clickable(onClick = onClick)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
        contentDescription = null,
        tint = if (isPlaying) Color.White else accentColor,
        modifier = Modifier.size(14.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.ExtraBold,
        color = if (isPlaying) Color.White else accentColor
      )
    }
  }
}

@Composable
private fun FloatingSpeakerSoundButton(
  size: SoundButtonSize,
  accentColor: Color,
  isPlaying: Boolean,
  showRipples: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val diameter = size.height

  Box(
    modifier = modifier.size(diameter),
    contentAlignment = Alignment.Center
  ) {
    if (isPlaying && showRipples) {
      val infinite = rememberInfiniteTransition(label = "speaker_ripple")
      val rippleAlpha by infinite.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
          animation = tween(600, easing = LinearEasing),
          repeatMode = RepeatMode.Restart
        ),
        label = "spkAlpha"
      )

      Canvas(modifier = Modifier.size(diameter * 1.4f)) {
        drawCircle(
          color = accentColor.copy(alpha = rippleAlpha),
          radius = this.size.width / 2f,
          style = Stroke(width = 3.dp.toPx())
        )
      }
    }

    Surface(
      shape = CircleShape,
      color = accentColor,
      shadowElevation = if (isPlaying) 8.dp else 4.dp,
      modifier = Modifier
        .size(diameter)
        .clickable(onClick = onClick)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.VolumeUp,
          contentDescription = "Play Sound",
          tint = Color.White,
          modifier = Modifier.size(size.iconSize)
        )
      }
    }
  }
}

@Composable
private fun HeroCardSoundButton(
  title: String,
  phonetic: String?,
  emoji: String?,
  description: String?,
  accentColor: Color,
  isPlaying: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color.White,
    shadowElevation = if (isPlaying) 10.dp else 4.dp,
    border = androidx.compose.foundation.BorderStroke(
      2.dp,
      if (isPlaying) accentColor else accentColor.copy(alpha = 0.3f)
    ),
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.horizontalGradient(
            colors = listOf(
              accentColor.copy(alpha = 0.15f),
              Color.White
            )
          )
        )
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        if (emoji != null) {
          Box(
            modifier = Modifier
              .size(56.dp)
              .clip(CircleShape)
              .background(accentColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Text(text = emoji, fontSize = 32.sp)
          }
          Spacer(modifier = Modifier.width(14.dp))
        }

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = title,
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFF263238)
            )
            if (phonetic != null) {
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = phonetic,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentColor
              )
            }
          }

          if (description != null) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = description,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              color = Color(0xFF546E7A)
            )
          }
        }
      }

      Surface(
        shape = CircleShape,
        color = accentColor,
        shadowElevation = 4.dp
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.VolumeUp,
          contentDescription = "Play Sound",
          tint = Color.White,
          modifier = Modifier
            .padding(12.dp)
            .size(24.dp)
        )
      }
    }
  }
}
