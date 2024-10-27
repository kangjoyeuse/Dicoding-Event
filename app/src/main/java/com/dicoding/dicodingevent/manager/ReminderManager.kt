package com.dicoding.dicodingevent.manager

import android.content.Context
import androidx.work.*
import com.dicoding.dicodingevent.worker.DailyReminderWorker
import java.util.concurrent.TimeUnit

class ReminderManager(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleReminder(isEnabled: Boolean) {
        if (isEnabled) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(
                1, TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                dailyWorkRequest
            )
        } else {
            workManager.cancelUniqueWork(WORK_NAME)
        }
    }

    companion object {
        private const val WORK_NAME = "daily_reminder_work"
    }
}