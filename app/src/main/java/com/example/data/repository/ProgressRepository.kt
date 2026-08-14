package com.example.data.repository

import com.example.data.db.ProgressDao
import com.example.data.models.BadgeEntity
import com.example.data.models.CategoryStatEntity
import com.example.data.models.LearnedWordEntity
import com.example.data.models.UserProgressEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ProgressRepository(private val dao: ProgressDao) {

  val userProgress: Flow<UserProgressEntity> = dao.getUserProgress().map { progress ->
    progress ?: UserProgressEntity()
  }

  val categoryStats: Flow<List<CategoryStatEntity>> = dao.getAllCategoryStats()
  val unlockedBadges: Flow<List<BadgeEntity>> = dao.getUnlockedBadges()
  val learnedWords: Flow<List<LearnedWordEntity>> = dao.getLearnedWords()

  suspend fun addStars(amount: Int, categoryId: String) {
    val current = dao.getCategoryStat(categoryId) ?: CategoryStatEntity(categoryId = categoryId)
    val updatedStat = current.copy(
      starsEarned = current.starsEarned + amount,
      completedCount = current.completedCount + 1,
      percentComplete = ((current.completedCount + 1) * 10).coerceAtMost(100)
    )
    dao.saveCategoryStat(updatedStat)

    // Update global user progress
    val currentProgress = dao.getUserProgress().firstOrNull() ?: UserProgressEntity()
    val todayEpochDay = System.currentTimeMillis() / (1000 * 60 * 60 * 24)

    val updatedStreak = when {
      currentProgress.lastPracticeEpochDay == 0L -> {
        if (currentProgress.learningStreak > 1) currentProgress.learningStreak else 1
      }
      currentProgress.lastPracticeEpochDay == todayEpochDay -> currentProgress.learningStreak
      currentProgress.lastPracticeEpochDay == todayEpochDay - 1 -> currentProgress.learningStreak + 1
      else -> 1
    }

    val updatedProgress = currentProgress.copy(
      totalStars = currentProgress.totalStars + amount,
      activitiesCompleted = currentProgress.activitiesCompleted + 1,
      learningStreak = updatedStreak,
      lastPracticeEpochDay = todayEpochDay
    )
    dao.saveUserProgress(updatedProgress)

    // Check for auto badge unlocks (Stars & Streaks)
    if (updatedProgress.totalStars >= 10) {
      dao.unlockBadge(BadgeEntity("star_10", "Star Starter", "Earned 10 stars!", "⭐"))
    }
    if (updatedProgress.totalStars >= 50) {
      dao.unlockBadge(BadgeEntity("star_50", "Star Collector", "Earned 50 stars!", "🌟"))
    }
    if (updatedProgress.totalStars >= 100) {
      dao.unlockBadge(BadgeEntity("star_100", "English Champ", "Earned 100 stars!", "🏆"))
    }
    if (updatedStreak >= 3) {
      dao.unlockBadge(BadgeEntity("streak_3", "3-Day Flame", "Practiced 3 days in a row!", "🔥"))
    }
    if (updatedStreak >= 7) {
      dao.unlockBadge(BadgeEntity("streak_7", "7-Day Super Streak", "Practiced 7 days in a row!", "⚡"))
    }
    if (updatedStreak >= 14) {
      dao.unlockBadge(BadgeEntity("streak_14", "Streak Master", "Practiced 14 consecutive days!", "👑"))
    }

    // Category Milestone Badges
    val categoryBadges = mapOf(
      "animals" to BadgeEntity("badge_animals", "Animal Safari", "Completed animal learning milestones!", "🦁"),
      "colors" to BadgeEntity("badge_colors", "Rainbow Master", "Completed color learning milestones!", "🎨"),
      "shapes" to BadgeEntity("badge_shapes", "Shape Wizard", "Completed shape learning milestones!", "🔺"),
      "alphabets" to BadgeEntity("badge_alphabets", "ABC Legend", "Completed alphabet learning milestones!", "🔤"),
      "numbers" to BadgeEntity("badge_numbers", "Math Marvel", "Completed number learning milestones!", "🔢"),
      "phonics" to BadgeEntity("badge_phonics", "Phonics Pioneer", "Completed phonics learning milestones!", "🔠"),
      "fruits_veg" to BadgeEntity("badge_fruits", "Healthy Eater", "Completed fruit & veggie milestones!", "🍎"),
      "fruits" to BadgeEntity("badge_fruits", "Healthy Eater", "Completed fruit & veggie milestones!", "🍎"),
      "body_parts" to BadgeEntity("badge_body", "Body Explorer", "Completed body parts milestones!", "👁️"),
      "sentences" to BadgeEntity("badge_sentences", "Storyteller", "Completed sentence learning milestones!", "🗣️"),
      "spelling" to BadgeEntity("badge_spelling", "Spelling Bee", "Completed spelling milestones!", "✏️"),
      "music" to BadgeEntity("badge_music", "Maestro Bobo", "Played music instruments!", "🎵"),
      "puzzles" to BadgeEntity("badge_puzzles", "Puzzle Genius", "Solved shape & picture puzzles!", "🧩")
    )

    categoryBadges[categoryId.lowercase()]?.let { badge ->
      dao.unlockBadge(badge)
    }
  }

  suspend fun recordDailyPractice() {
    val currentProgress = dao.getUserProgress().firstOrNull() ?: UserProgressEntity()
    val todayEpochDay = System.currentTimeMillis() / (1000 * 60 * 60 * 24)

    val updatedStreak = when {
      currentProgress.lastPracticeEpochDay == 0L -> {
        if (currentProgress.learningStreak > 1) currentProgress.learningStreak else 1
      }
      currentProgress.lastPracticeEpochDay == todayEpochDay -> currentProgress.learningStreak
      currentProgress.lastPracticeEpochDay == todayEpochDay - 1 -> currentProgress.learningStreak + 1
      else -> 1
    }

    val updatedProgress = currentProgress.copy(
      learningStreak = updatedStreak,
      lastPracticeEpochDay = todayEpochDay
    )
    dao.saveUserProgress(updatedProgress)

    if (updatedStreak >= 3) {
      dao.unlockBadge(BadgeEntity("streak_3", "3-Day Flame", "Practiced 3 days in a row!", "🔥"))
    }
    if (updatedStreak >= 7) {
      dao.unlockBadge(BadgeEntity("streak_7", "7-Day Super Streak", "Practiced 7 days in a row!", "⚡"))
    }
    if (updatedStreak >= 14) {
      dao.unlockBadge(BadgeEntity("streak_14", "Streak Master", "Practiced 14 consecutive days!", "👑"))
    }
  }

  suspend fun addLearnedWord(word: String, categoryId: String) {
    dao.addLearnedWord(LearnedWordEntity(word, categoryId))
  }

  suspend fun updateSettings(
    soundEnabled: Boolean? = null,
    voiceEnabled: Boolean? = null,
    animationsEnabled: Boolean? = null,
    nightMode: Boolean? = null,
    firstLaunchCompleted: Boolean? = null,
    difficultyLevel: String? = null,
    dailyReminderEnabled: Boolean? = null,
    reminderTimeHour: Int? = null,
    reminderTimeMinute: Int? = null
  ) {
    val current = dao.getUserProgress().firstOrNull() ?: UserProgressEntity()
    val updated = current.copy(
      soundEnabled = soundEnabled ?: current.soundEnabled,
      voiceEnabled = voiceEnabled ?: current.voiceEnabled,
      animationsEnabled = animationsEnabled ?: current.animationsEnabled,
      nightMode = nightMode ?: current.nightMode,
      firstLaunchCompleted = firstLaunchCompleted ?: current.firstLaunchCompleted,
      difficultyLevel = difficultyLevel ?: current.difficultyLevel,
      dailyReminderEnabled = dailyReminderEnabled ?: current.dailyReminderEnabled,
      reminderTimeHour = reminderTimeHour ?: current.reminderTimeHour,
      reminderTimeMinute = reminderTimeMinute ?: current.reminderTimeMinute
    )
    dao.saveUserProgress(updated)
  }

  suspend fun resetAllProgress() {
    dao.clearUserProgress()
    dao.clearCategoryStats()
    dao.clearBadges()
    dao.clearLearnedWords()
    dao.saveUserProgress(UserProgressEntity())
  }
}

