package com.dicoding.dicodingevent.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dicoding.dicodingevent.data.Repository
import com.dicoding.dicodingevent.utils.NotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyReminderWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val nearestEvent = repository.getNearestActiveEvent()
            if (nearestEvent != null) {
                NotificationHelper.showNotification(
                    applicationContext,
                    "Upcoming Event",
                    "Datanglah jangan lupa! ${nearestEvent.name} segera dimulai pada ${nearestEvent.beginTime}"
                )
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}