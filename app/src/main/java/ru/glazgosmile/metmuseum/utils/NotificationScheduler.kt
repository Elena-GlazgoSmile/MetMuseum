package ru.glazgosmile.metmuseum.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import ru.glazgosmile.metmuseum.reminder.ReminderReceiver
import java.util.Calendar

object NotificationScheduler {

    @SuppressLint("ScheduleExactAlarm")
    fun schedule(context: Context, timeString: String, studentName: String) {
        Log.d("NotificationScheduler", "Вызван schedule с timeString: $timeString")
        if (timeString.isBlank()) return

        try {
            val parts = timeString.split(":")
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            Log.d("NotificationScheduler", "Время будильника: ${calendar.time}")

            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("STUDENT_NAME", studentName)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Log.d("NotificationScheduler", "canScheduleExactAlarms: ${alarmManager.canScheduleExactAlarms()}")
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                    )
                } else {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                    )
                }
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                )
            }
            Log.d("NotificationScheduler", "Будильник успешно установлен!")
            Toast.makeText(context, "Уведомление установлено на $timeString", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("NotificationScheduler", "Ошибка при установке будильника", e)
            Toast.makeText(context, "Ошибка планирования: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}