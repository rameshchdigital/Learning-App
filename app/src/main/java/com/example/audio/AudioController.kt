package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.speech.tts.TextToSpeech
import java.util.Locale
import kotlin.math.sin

class AudioController(private val context: Context) : TextToSpeech.OnInitListener {

  var soundEnabled: Boolean = true
  var voiceEnabled: Boolean = true

  private var tts: TextToSpeech? = TextToSpeech(context, this)
  private var isTtsReady: Boolean = false
  private var pendingSpeakText: String? = null

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = tts?.setLanguage(Locale.US)
      if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
        tts?.setPitch(1.15f) // Friendly child-like tone
        tts?.setSpeechRate(0.88f) // Clear & easy for kids to follow
        isTtsReady = true
        pendingSpeakText?.let {
          speak(it)
          pendingSpeakText = null
        }
      }
    }
  }

  fun speak(text: String) {
    if (!voiceEnabled) return
    if (!isTtsReady) {
      pendingSpeakText = text
      return
    }
    tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UtteranceId_${System.currentTimeMillis()}")
  }

  fun playSoundResource(soundResId: Int, fallbackText: String? = null) {
    if (!soundEnabled && !voiceEnabled) return
    try {
      if (soundResId != 0) {
        val mediaPlayer = android.media.MediaPlayer.create(context, soundResId)
        if (mediaPlayer != null) {
          mediaPlayer.setOnCompletionListener { mp ->
            mp.release()
          }
          mediaPlayer.start()
          return
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    if (!fallbackText.isNullOrEmpty()) {
      speak(fallbackText)
    }
  }

  fun playSoundResourceOrSpeak(soundResId: Int?, textToSpeak: String) {
    if (soundResId != null && soundResId != 0) {
      playSoundResource(soundResId, fallbackText = textToSpeak)
    } else {
      speak(textToSpeak)
    }
  }

  fun playLetterSound(letter: String, phoneme: String? = null, exampleWord: String? = null) {
    if (!voiceEnabled) return
    playTapSound()
    val cleanLetter = letter.trim().uppercase(Locale.ROOT)
    val phonemeText = phoneme?.trim()?.replace("/", "") ?: getPhoneticSoundForLetter(cleanLetter)
    val text = if (!exampleWord.isNullOrEmpty()) {
      "Letter $cleanLetter. Sound: $phonemeText. $exampleWord!"
    } else {
      "$cleanLetter. $phonemeText!"
    }
    speak(text)
  }

  fun playCvcSound(
    word: String,
    letters: List<String> = emptyList(),
    sounds: List<String> = emptyList(),
    slowBlend: Boolean = false
  ) {
    if (!voiceEnabled) return
    playTapSound()
    val cleanWord = word.trim().uppercase(Locale.ROOT)
    if (slowBlend && letters.isNotEmpty()) {
      val letterSequence = letters.joinToString(separator = " ... ")
      speak("$letterSequence ... makes $cleanWord!")
    } else if (sounds.isNotEmpty()) {
      val soundSeq = sounds.map { it.replace("/", "") }.joinToString(separator = " ... ")
      speak("$soundSeq ... $cleanWord!")
    } else {
      speak("$cleanWord!")
    }
  }

  private fun getPhoneticSoundForLetter(letter: String): String {
    return when (letter.uppercase(Locale.ROOT)) {
      "A" -> "ah"
      "B" -> "buh"
      "C" -> "kuh"
      "D" -> "duh"
      "E" -> "eh"
      "F" -> "fff"
      "G" -> "guh"
      "H" -> "huh"
      "I" -> "ih"
      "J" -> "juh"
      "K" -> "kuh"
      "L" -> "lll"
      "M" -> "mmm"
      "N" -> "nnn"
      "O" -> "aw"
      "P" -> "puh"
      "Q" -> "kwah"
      "R" -> "rrr"
      "S" -> "sss"
      "T" -> "tuh"
      "U" -> "uh"
      "V" -> "vvv"
      "W" -> "wuh"
      "X" -> "ks"
      "Y" -> "yuh"
      "Z" -> "zzz"
      else -> letter
    }
  }

  fun speakCard(word: String, sentence: String? = null, animalId: String? = null) {
    if (!voiceEnabled) return
    val speakText = if (!sentence.isNull_or_empty_or_blank(sentence)) "$word! $sentence" else word
    speak(speakText)
    if (animalId != null) {
      playAnimalSound(animalId)
    }
  }

  private fun String?.isNull_or_empty_or_blank(str: String?): Boolean {
    return str == null || str.trim().isEmpty()
  }

  fun playNote(frequencyHz: Double, durationMs: Int = 300) {
    playTone(frequencyHz, frequencyHz, durationMs)
  }

  fun playTone(
    startFreq: Double,
    endFreq: Double = startFreq,
    durationMs: Int = 300,
    waveType: String = "sine"
  ) {
    if (!soundEnabled) return
    val sampleRate = 44100
    val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
    if (numSamples <= 0) return
    val generatedSnd = ByteArray(2 * numSamples)

    val fadeSamples = (sampleRate * 0.01).toInt().coerceAtMost(numSamples / 4)

    var phase = 0.0
    var idx = 0

    for (i in 0 until numSamples) {
      val progress = i.toDouble() / numSamples
      val freq = startFreq + (endFreq - startFreq) * progress
      val phaseIncrement = 2.0 * Math.PI * freq / sampleRate
      phase += phaseIncrement

      var sampleVal = when (waveType) {
        "square" -> if (sin(phase) >= 0) 0.5 else -0.5
        "triangle" -> (2.0 / Math.PI) * Math.asin(sin(phase))
        else -> sin(phase)
      }

      val envelope = when {
        i < fadeSamples -> i.toDouble() / fadeSamples
        i > numSamples - fadeSamples -> (numSamples - i).toDouble() / fadeSamples
        else -> 1.0
      }

      sampleVal *= envelope
      val valShort = (sampleVal * 32767).toInt().coerceIn(-32768, 32767).toShort()
      generatedSnd[idx++] = (valShort.toInt() and 0x00ff).toByte()
      generatedSnd[idx++] = ((valShort.toInt() and 0xff00) ushr 8).toByte()
    }

    try {
      val audioTrack = AudioTrack.Builder()
        .setAudioAttributes(
          AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        )
        .setAudioFormat(
          AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        )
        .setBufferSizeInBytes(generatedSnd.size)
        .setTransferMode(AudioTrack.MODE_STATIC)
        .build()

      audioTrack.write(generatedSnd, 0, generatedSnd.size)
      audioTrack.play()
      Thread.sleep(durationMs.toLong() + 15)
      audioTrack.stop()
      audioTrack.release()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun playClickSound() {
    if (!soundEnabled) return
    Thread {
      playTone(550.0, 850.0, 40, "sine")
    }.start()
  }

  fun playTapSound() {
    playClickSound()
  }

  fun playNavigationSound() {
    if (!soundEnabled) return
    Thread {
      playTone(440.0, 660.0, 60, "sine")
    }.start()
  }

  fun playCardFlipSound() {
    if (!soundEnabled) return
    Thread {
      playTone(320.0, 520.0, 45, "triangle")
    }.start()
  }

  fun playMatchSound() {
    if (!soundEnabled) return
    Thread {
      playTone(523.25, 523.25, 90) // C5
      try { Thread.sleep(95) } catch (_: Exception) {}
      playTone(659.25, 659.25, 110) // E5
      try { Thread.sleep(115) } catch (_: Exception) {}
      playTone(783.99, 783.99, 180) // G5
    }.start()
  }

  fun playRewardSound() {
    if (!soundEnabled) return
    Thread {
      playTone(523.25, 523.25, 80)  // C5
      try { Thread.sleep(85) } catch (_: Exception) {}
      playTone(659.25, 659.25, 80)  // E5
      try { Thread.sleep(85) } catch (_: Exception) {}
      playTone(783.99, 783.99, 90)  // G5
      try { Thread.sleep(95) } catch (_: Exception) {}
      playTone(1046.50, 1046.50, 200) // C6
    }.start()
  }

  fun playBadgeUnlockSound() {
    if (!soundEnabled) return
    Thread {
      playTone(392.00, 392.00, 90)  // G4
      try { Thread.sleep(95) } catch (_: Exception) {}
      playTone(523.25, 523.25, 90)  // C5
      try { Thread.sleep(95) } catch (_: Exception) {}
      playTone(659.25, 659.25, 90)  // E5
      try { Thread.sleep(95) } catch (_: Exception) {}
      playTone(783.99, 783.99, 110) // G5
      try { Thread.sleep(115) } catch (_: Exception) {}
      playTone(1046.50, 1046.50, 260) // C6
    }.start()
  }

  fun playBubblePopSound() {
    if (!soundEnabled) return
    Thread {
      playTone(300.0, 1100.0, 70, "sine")
    }.start()
  }

  fun playStreakSound() {
    if (!soundEnabled) return
    Thread {
      playTone(523.25, 659.25, 100, "sine")
      try { Thread.sleep(105) } catch (_: Exception) {}
      playTone(659.25, 880.00, 150, "sine")
    }.start()
  }

  fun playCorrectSound() = playSuccessSound()

  fun playSuccessSound() {
    if (!soundEnabled) return
    Thread {
      playTone(523.25, 523.25, 120) // C5
      try { Thread.sleep(130) } catch (_: Exception) {}
      playTone(659.25, 659.25, 150) // E5
      try { Thread.sleep(160) } catch (_: Exception) {}
      playTone(783.99, 783.99, 250) // G5
    }.start()
  }

  fun playTryAgainSound() {
    if (!soundEnabled) return
    Thread {
      playTone(220.0, 220.0, 150) // A3
      try { Thread.sleep(160) } catch (_: Exception) {}
      playTone(196.0, 196.0, 200) // G3
    }.start()
  }

  fun playDrumSound() {
    Thread { playTone(120.0, 80.0, 120, "triangle") }.start()
  }

  fun playBellSound() {
    Thread { playTone(880.0, 880.0, 250) }.start()
  }

  fun playTrumpetSound() {
    Thread { playTone(440.0, 550.0, 220, "square") }.start()
  }

  fun playGuitarSound() {
    Thread { playTone(330.0, 330.0, 220, "triangle") }.start()
  }

  fun playAnimalSound(animalId: String) {
    if (!soundEnabled) return
    Thread {
      when (animalId.lowercase(Locale.ROOT)) {
        "dog" -> {
          playTone(380.0, 260.0, 90, "triangle")
          try { Thread.sleep(80) } catch (_: Exception) {}
          playTone(450.0, 300.0, 120, "triangle")
        }
        "cat" -> {
          playTone(550.0, 780.0, 180, "sine")
          playTone(780.0, 500.0, 170, "sine")
        }
        "cow" -> {
          playTone(140.0, 125.0, 500, "triangle")
        }
        "lion" -> {
          playTone(110.0, 85.0, 280, "triangle")
          try { Thread.sleep(40) } catch (_: Exception) {}
          playTone(95.0, 75.0, 320, "triangle")
        }
        "duck" -> {
          playTone(320.0, 260.0, 110, "square")
          try { Thread.sleep(70) } catch (_: Exception) {}
          playTone(300.0, 240.0, 130, "square")
        }
        "pig" -> {
          playTone(250.0, 190.0, 90, "triangle")
          try { Thread.sleep(60) } catch (_: Exception) {}
          playTone(230.0, 180.0, 110, "triangle")
        }
        "frog" -> {
          playTone(170.0, 230.0, 80, "square")
          try { Thread.sleep(50) } catch (_: Exception) {}
          playTone(210.0, 160.0, 100, "square")
        }
        "elephant" -> {
          playTone(480.0, 720.0, 350, "sine")
        }
        "sheep" -> {
          playTone(280.0, 240.0, 230, "triangle")
          try { Thread.sleep(80) } catch (_: Exception) {}
          playTone(260.0, 220.0, 200, "triangle")
        }
        "bird" -> {
          playTone(1100.0, 1450.0, 70, "sine")
          try { Thread.sleep(40) } catch (_: Exception) {}
          playTone(1300.0, 1600.0, 80, "sine")
          try { Thread.sleep(40) } catch (_: Exception) {}
          playTone(1500.0, 1750.0, 90, "sine")
        }
        else -> playBellSound()
      }
    }.start()
  }

  fun playDogSound() = playAnimalSound("dog")
  fun playCatSound() = playAnimalSound("cat")
  fun playCowSound() = playAnimalSound("cow")
  fun playLionSound() = playAnimalSound("lion")
  fun playDuckSound() = playAnimalSound("duck")
  fun playPigSound() = playAnimalSound("pig")
  fun playFrogSound() = playAnimalSound("frog")
  fun playElephantSound() = playAnimalSound("elephant")
  fun playSheepSound() = playAnimalSound("sheep")
  fun playBirdSound() = playAnimalSound("bird")

  fun release() {
    tts?.stop()
    tts?.shutdown()
  }
}
