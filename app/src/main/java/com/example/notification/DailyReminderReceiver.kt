package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyReminderReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
      // Reschedule alarm on device reboot
      DailyReminderManager.scheduleDailyReminder(context, 17, 0)
    } else {
      // Trigger daily reminder push notification
      DailyReminderManager.showReminderNotification(context)
    }
  }
}
