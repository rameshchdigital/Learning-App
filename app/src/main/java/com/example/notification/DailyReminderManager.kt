package com.example.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import java.util.Calendar

object DailyReminderManager {

  const val CHANNEL_ID = "daily_vocab_reminder_channel"
  const val NOTIFICATION_ID = 8881
  const val REQUEST_CODE = 9001

  fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "Daily Vocabulary Reminders"
      val descriptionText = "Nudges children to practice vocabulary daily at set times"
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
        enableVibration(true)
      }
      val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }

  fun scheduleDailyReminder(context: Context, hour: Int = 17, minute: Int = 0) {
    createNotificationChannel(context)

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, DailyReminderReceiver::class.java).apply {
      action = "com.example.ACTION_DAILY_REMINDER"
    }
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      REQUEST_CODE,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
      timeInMillis = System.currentTimeMillis()
      set(Calendar.HOUR_OF_DAY, hour)
      set(Calendar.MINUTE, minute)
      set(Calendar.SECOND, 0)
      if (before(Calendar.getInstance())) {
        add(Calendar.DAY_OF_YEAR, 1)
      }
    }

    try {
      alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
      )
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun cancelDailyReminder(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, DailyReminderReceiver::class.java).apply {
      action = "com.example.ACTION_DAILY_REMINDER"
    }
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      REQUEST_CODE,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
  }

  fun showReminderNotification(context: Context) {
    createNotificationChannel(context)

    val contentIntent = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val pendingIntent = PendingIntent.getActivity(
      context,
      0,
      contentIntent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle("🌟 Daily Vocabulary Time with Bobo!")
      .setContentText("Time to practice new words and earn stars! Keep your learning streak alive! 🔥")
      .setStyle(
        NotificationCompat.BigTextStyle().bigText(
          "Bobo is waiting for you! 🦁 Learn new vocabulary cards today, earn stars ⭐, and unlock awesome badges!"
        )
      )
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .build()

    val notificationManager =
      context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(NOTIFICATION_ID, notification)
  }
}
