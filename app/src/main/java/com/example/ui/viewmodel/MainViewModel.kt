package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.data.db.AppDatabase
import com.example.data.models.CategoryStatEntity
import com.example.data.models.DailyQuizUiState
import com.example.data.models.DifficultyLevel
import com.example.data.models.GameCategory
import com.example.data.models.GameOption
import com.example.data.models.GameQuestion
import com.example.data.models.UserProgressEntity
import com.example.data.repository.ProgressRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Screen {
  SPLASH,
  WELCOME,
  HOME,
  LEARN_CATEGORIES,
  PLAY_GAMES,
  PROGRESS_REWARDS,
  BADGE_COLLECTION,
  DAILY_QUIZ,
  ALPHABET_LESSON,
  PHONICS_LESSON,
  NUMBERS_LESSON,
  COLORS_LESSON,
  SHAPES_LESSON,
  ANIMALS_EXPLORER,
  FRUITS_VEG_LESSON,
  BODY_PARTS_LESSON,
  CATEGORY_LESSON,
  SPEAKING_PRACTICE,
  SENTENCE_LEARNING,
  SPELLING_LESSON,
  VOCABULARY_CARDS,
  GAME,
  SOUND_SORTING,
  LISTENING_CHALLENGE,
  MUSIC_PLAYGROUND,
  PUZZLE_PLAYGROUND,
  PARENT_DASHBOARD,
  SETTINGS
}

data class GameUiState(
  val currentCategory: GameCategory = GameCategory.ANIMALS,
  val currentDifficulty: DifficultyLevel = DifficultyLevel.MEDIUM,
  val questions: List<GameQuestion> = emptyList(),
  val currentQuestionIndex: Int = 0,
  val selectedOptionId: String? = null,
  val isAnswerCorrect: Boolean? = null,
  val isSessionCompleted: Boolean = false,
  val starsEarnedInSession: Int = 0,
  val isCelebrationVisible: Boolean = false,
  val celebrationMessage: String = "Great Job!"
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

  private val db = Room.databaseBuilder(
    application,
    AppDatabase::class.java,
    "baby_world_db"
  ).fallbackToDestructiveMigration().build()

  private val repository = ProgressRepository(db.progressDao())
  val audioController = AudioController(application)

  val userProgress: StateFlow<UserProgressEntity> = repository.userProgress
    .let { flow ->
      val state = MutableStateFlow(UserProgressEntity())
      viewModelScope.launch {
        flow.collect { progress ->
          state.value = progress
          audioController.soundEnabled = progress.soundEnabled
          audioController.voiceEnabled = progress.voiceEnabled
        }
      }
      state.asStateFlow()
    }

  val categoryStats: StateFlow<List<CategoryStatEntity>> = repository.categoryStats
    .let { flow ->
      val state = MutableStateFlow<List<CategoryStatEntity>>(emptyList())
      viewModelScope.launch {
        flow.collect { state.value = it }
      }
      state.asStateFlow()
    }

  val badges: StateFlow<List<com.example.data.models.BadgeEntity>> = repository.unlockedBadges
    .let { flow ->
      val state = MutableStateFlow<List<com.example.data.models.BadgeEntity>>(emptyList())
      viewModelScope.launch {
        flow.collect { state.value = it }
      }
      state.asStateFlow()
    }

  val learnedWords: StateFlow<List<com.example.data.models.LearnedWordEntity>> = repository.learnedWords
    .let { flow ->
      val state = MutableStateFlow<List<com.example.data.models.LearnedWordEntity>>(emptyList())
      viewModelScope.launch {
        flow.collect { state.value = it }
      }
      state.asStateFlow()
    }

  private val _currentScreen = MutableStateFlow(Screen.SPLASH)
  val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

  private val _gameUiState = MutableStateFlow(GameUiState())
  val gameUiState: StateFlow<GameUiState> = _gameUiState.asStateFlow()

  private val _dailyQuizUiState = MutableStateFlow(DailyQuizUiState())
  val dailyQuizUiState: StateFlow<DailyQuizUiState> = _dailyQuizUiState.asStateFlow()

  private val celebrationPhrases = listOf(
    "Great Job!", "Amazing!", "Yay!", "Wonderful!",
    "You Did It!", "Super!", "Fantastic!", "Awesome!"
  )

  init {
    viewModelScope.launch {
      delay(300)
      val progress = userProgress.value
      if (progress.firstLaunchCompleted) {
        _currentScreen.value = Screen.HOME
      } else {
        _currentScreen.value = Screen.WELCOME
      }
    }
  }

  fun navigateTo(screen: Screen) {
    _currentScreen.value = screen
    audioController.playNavigationSound()
    when (screen) {
      Screen.HOME -> audioController.speak("Welcome to Little English Buddy!")
      Screen.LEARN_CATEGORIES -> audioController.speak("Explore and learn English!")
      Screen.PLAY_GAMES -> audioController.speak("Let's play learning games!")
      Screen.PROGRESS_REWARDS -> audioController.speak("Look at your stars and progress!")
      Screen.BADGE_COLLECTION -> audioController.speak("Badge Collection! See all your earned milestone badges!")
      Screen.SPEAKING_PRACTICE -> audioController.speak("Let's speak English together!")
      Screen.VOCABULARY_CARDS -> audioController.speak("Let's explore cheerful word cards!")
      else -> {}
    }
  }

  fun startCategoryGame(category: GameCategory, overrideDifficulty: DifficultyLevel? = null) {
    audioController.playClickSound()
    val difficulty = overrideDifficulty ?: DifficultyLevel.fromId(userProgress.value.difficultyLevel)
    val questions = GameContentRepository.getQuestionsForCategory(category, difficulty)
    _gameUiState.value = GameUiState(
      currentCategory = category,
      currentDifficulty = difficulty,
      questions = questions,
      currentQuestionIndex = 0
    )

    when (category) {
      GameCategory.ALPHABETS -> navigateTo(Screen.ALPHABET_LESSON)
      GameCategory.PHONICS -> navigateTo(Screen.PHONICS_LESSON)
      GameCategory.NUMBERS -> navigateTo(Screen.NUMBERS_LESSON)
      GameCategory.COLORS -> navigateTo(Screen.COLORS_LESSON)
      GameCategory.SHAPES -> navigateTo(Screen.SHAPES_LESSON)
      GameCategory.ANIMALS -> navigateTo(Screen.ANIMALS_EXPLORER)
      GameCategory.BODY_PARTS -> navigateTo(Screen.BODY_PARTS_LESSON)
      GameCategory.SENTENCES -> navigateTo(Screen.SENTENCE_LEARNING)
      GameCategory.SPELLING -> navigateTo(Screen.SPELLING_LESSON)
      GameCategory.MUSIC -> navigateTo(Screen.MUSIC_PLAYGROUND)
      GameCategory.PUZZLES -> navigateTo(Screen.PUZZLE_PLAYGROUND)
      else -> navigateTo(Screen.CATEGORY_LESSON)
    }
  }

  fun updateDifficultyLevel(difficulty: DifficultyLevel) {
    audioController.playClickSound()
    viewModelScope.launch {
      repository.updateSettings(difficultyLevel = difficulty.id)
      val currentCategory = _gameUiState.value.currentCategory
      val refreshedQuestions = GameContentRepository.getQuestionsForCategory(currentCategory, difficulty)
      _gameUiState.value = _gameUiState.value.copy(
        currentDifficulty = difficulty,
        questions = refreshedQuestions,
        currentQuestionIndex = 0,
        selectedOptionId = null,
        isAnswerCorrect = null
      )
      if (_currentScreen.value == Screen.GAME) {
        speakCurrentQuestion()
      }
    }
  }

  fun awardStarsDirectly(amount: Int, categoryId: String = "animals") {
    audioController.playRewardSound()
    viewModelScope.launch {
      repository.addStars(amount, categoryId)
    }
  }

  fun recordDailyPractice() {
    audioController.playStreakSound()
    viewModelScope.launch {
      repository.recordDailyPractice()
    }
  }

  fun speakCurrentQuestion() {
    val state = _gameUiState.value
    if (state.questions.isNotEmpty() && state.currentQuestionIndex < state.questions.size) {
      val question = state.questions[state.currentQuestionIndex]
      audioController.speak(question.voicePrompt)
    }
  }

  fun speakOption(option: GameOption) {
    audioController.speak(option.label)
  }

  fun onOptionSelected(option: GameOption) {
    val state = _gameUiState.value
    if (state.isAnswerCorrect == true) return

    val currentQuestion = state.questions[state.currentQuestionIndex]
    val isCorrect = option.id == currentQuestion.correctAnswerId

    if (isCorrect) {
      if (state.currentCategory == GameCategory.ANIMALS) {
        audioController.playAnimalSound(option.id)
      } else {
        audioController.playMatchSound()
      }
      val randomPhrase = celebrationPhrases.random()
      audioController.speak("$randomPhrase ${option.label}!")

      _gameUiState.value = state.copy(
        selectedOptionId = option.id,
        isAnswerCorrect = true,
        isCelebrationVisible = true,
        celebrationMessage = randomPhrase,
        starsEarnedInSession = state.starsEarnedInSession + 1
      )

      viewModelScope.launch {
        repository.addStars(1, state.currentCategory.id)
        delay(1600)
        _gameUiState.value = _gameUiState.value.copy(isCelebrationVisible = false)
        nextQuestion()
      }
    } else {
      audioController.playTryAgainSound()
      audioController.speak("Try again!")

      _gameUiState.value = state.copy(
        selectedOptionId = option.id,
        isAnswerCorrect = false
      )

      viewModelScope.launch {
        delay(1000)
        _gameUiState.value = _gameUiState.value.copy(
          selectedOptionId = null,
          isAnswerCorrect = null
        )
      }
    }
  }

  private fun nextQuestion() {
    val state = _gameUiState.value
    val nextIdx = state.currentQuestionIndex + 1
    if (nextIdx < state.questions.size) {
      _gameUiState.value = state.copy(
        currentQuestionIndex = nextIdx,
        selectedOptionId = null,
        isAnswerCorrect = null
      )
      speakCurrentQuestion()
    } else {
      _gameUiState.value = state.copy(
        isSessionCompleted = true
      )
      audioController.playRewardSound()
      audioController.speak("Amazing playing! You earned stars!")
    }
  }

  fun restartCategoryGame() {
    startCategoryGame(_gameUiState.value.currentCategory)
  }

  fun completeFirstLaunch() {
    viewModelScope.launch {
      repository.updateSettings(firstLaunchCompleted = true)
      navigateTo(Screen.HOME)
    }
  }

  fun toggleSound(enabled: Boolean) {
    viewModelScope.launch {
      repository.updateSettings(soundEnabled = enabled)
    }
  }

  fun toggleVoice(enabled: Boolean) {
    viewModelScope.launch {
      repository.updateSettings(voiceEnabled = enabled)
    }
  }

  fun toggleAnimations(enabled: Boolean) {
    viewModelScope.launch {
      repository.updateSettings(animationsEnabled = enabled)
    }
  }

  fun toggleNightMode(enabled: Boolean) {
    viewModelScope.launch {
      repository.updateSettings(nightMode = enabled)
    }
  }

  fun toggleDailyReminder(enabled: Boolean, hour: Int = 17, minute: Int = 0, context: android.content.Context? = null) {
    viewModelScope.launch {
      repository.updateSettings(
        dailyReminderEnabled = enabled,
        reminderTimeHour = hour,
        reminderTimeMinute = minute
      )
      context?.let { ctx ->
        if (enabled) {
          com.example.notification.DailyReminderManager.scheduleDailyReminder(ctx, hour, minute)
        } else {
          com.example.notification.DailyReminderManager.cancelDailyReminder(ctx)
        }
      }
    }
  }

  fun triggerTestNotification(context: android.content.Context) {
    com.example.notification.DailyReminderManager.showReminderNotification(context)
  }

  fun startDailyQuiz() {
    val stats = categoryStats.value
    val practicedIds = stats.filter { it.completedCount > 0 || it.starsEarned > 0 }.map { it.categoryId }
    val difficulty = DifficultyLevel.fromId(userProgress.value.difficultyLevel)
    val questions = GameContentRepository.getDailyQuizQuestions(practicedIds, difficulty)

    val categoryTitleMap = mapOf(
      "animals" to "Animals",
      "colors" to "Colors",
      "shapes" to "Shapes",
      "alphabets" to "Alphabets",
      "numbers" to "Numbers",
      "phonics" to "Phonics",
      "fruits_veg" to "Fruits & Veggies",
      "body_parts" to "Body Parts",
      "spelling" to "Spelling",
      "sentences" to "Sentences",
      "music" to "Music",
      "puzzles" to "Puzzles"
    )
    val categoryNames = practicedIds.mapNotNull { categoryTitleMap[it.lowercase()] }.distinct().take(3)
    val summaryText = if (categoryNames.isNotEmpty()) categoryNames.joinToString(", ") else "Animals, Colors & Shapes"

    _dailyQuizUiState.value = DailyQuizUiState(
      questions = questions,
      currentIndex = 0,
      selectedOptionId = null,
      isAnswered = false,
      isCorrect = false,
      correctCount = 0,
      scoreStars = 0,
      bonusStars = 0,
      isQuizCompleted = false,
      practicedCategoriesText = summaryText
    )

    audioController.playClickSound()
    audioController.speak("Welcome to your Daily Quiz! 5 fun questions from your practiced categories!")
  }

  fun selectDailyQuizOption(option: GameOption) {
    val state = _dailyQuizUiState.value
    if (state.isAnswered || state.currentIndex >= state.questions.size) return

    val currentQ = state.questions[state.currentIndex]
    val isCorrect = option.id == currentQ.correctAnswerId

    val newCorrectCount = if (isCorrect) state.correctCount + 1 else state.correctCount
    val newScoreStars = if (isCorrect) state.scoreStars + 2 else state.scoreStars

    _dailyQuizUiState.value = state.copy(
      selectedOptionId = option.id,
      isAnswered = true,
      isCorrect = isCorrect,
      correctCount = newCorrectCount,
      scoreStars = newScoreStars
    )

    if (isCorrect) {
      audioController.playMatchSound()
      val phrases = listOf("Awesome!", "Great Job!", "Super Star!", "Spot on!", "Wonderful!")
      audioController.speak("${phrases.random()} ${option.label} is correct!")
    } else {
      audioController.playClickSound()
      val correctOpt = currentQ.options.firstOrNull { it.id == currentQ.correctAnswerId }
      audioController.speak("Good try! The correct answer was ${correctOpt?.label ?: ""}.")
    }
  }

  fun nextDailyQuizQuestion() {
    val state = _dailyQuizUiState.value
    audioController.playClickSound()

    if (state.currentIndex + 1 < state.questions.size) {
      val nextIdx = state.currentIndex + 1
      _dailyQuizUiState.value = state.copy(
        currentIndex = nextIdx,
        selectedOptionId = null,
        isAnswered = false,
        isCorrect = false
      )
      val nextQ = state.questions[nextIdx]
      audioController.speak(nextQ.voicePrompt)
    } else {
      val bonus = when {
        state.correctCount == 5 -> 5
        state.correctCount >= 4 -> 3
        state.correctCount >= 3 -> 2
        else -> 1
      }
      val totalEarned = state.scoreStars + bonus
      viewModelScope.launch {
        repository.addStars(totalEarned, "daily_quiz")
        repository.recordDailyPractice()
      }

      _dailyQuizUiState.value = state.copy(
        bonusStars = bonus,
        isQuizCompleted = true
      )

      audioController.playRewardSound()
      val msg = if (state.correctCount == 5) {
        "PERFECT 100 SCORE! You earned $totalEarned stars with bonus points!"
      } else {
        "Super Daily Quiz finished! You got ${state.correctCount} out of 5 correct and earned $totalEarned stars!"
      }
      audioController.speak(msg)
    }
  }

  fun resetAllProgress() {
    viewModelScope.launch {
      repository.resetAllProgress()
    }
  }

  override fun onCleared() {
    super.onCleared()
    audioController.release()
  }
}
