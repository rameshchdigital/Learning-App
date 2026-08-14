package com.example.data.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.data.models.BadgeEntity
import com.example.data.models.CategoryStatEntity
import com.example.data.models.LearnedWordEntity
import com.example.data.models.UserProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
  @Query("SELECT * FROM user_progress WHERE id = 1")
  fun getUserProgress(): Flow<UserProgressEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveUserProgress(progress: UserProgressEntity)

  @Query("SELECT * FROM category_stats")
  fun getAllCategoryStats(): Flow<List<CategoryStatEntity>>

  @Query("SELECT * FROM category_stats WHERE categoryId = :catId")
  suspend fun getCategoryStat(catId: String): CategoryStatEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveCategoryStat(stat: CategoryStatEntity)

  @Query("SELECT * FROM unlocked_badges")
  fun getUnlockedBadges(): Flow<List<BadgeEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun unlockBadge(badge: BadgeEntity)

  @Query("SELECT * FROM learned_words")
  fun getLearnedWords(): Flow<List<LearnedWordEntity>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addLearnedWord(word: LearnedWordEntity)

  @Query("DELETE FROM user_progress")
  suspend fun clearUserProgress()

  @Query("DELETE FROM category_stats")
  suspend fun clearCategoryStats()

  @Query("DELETE FROM unlocked_badges")
  suspend fun clearBadges()

  @Query("DELETE FROM learned_words")
  suspend fun clearLearnedWords()
}

@Database(
  entities = [UserProgressEntity::class, CategoryStatEntity::class, BadgeEntity::class, LearnedWordEntity::class],
  version = 5,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun progressDao(): ProgressDao
}

