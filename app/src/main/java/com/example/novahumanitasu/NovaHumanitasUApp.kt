package com.example.novahumanitasu


import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.novahumanitasu.workers.ReminderWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.util.Log

@HiltAndroidApp
class NovaHumanitasUApp : Application() {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)

        Log.d("NovaHumanitasUApp", "Application onCreate - Scheduling Worker")
        setupPeriodicReminders()
    }

    private fun setupPeriodicReminders() {
        val reminderWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "ReminderWorkerUniqueName",
            ExistingPeriodicWorkPolicy.KEEP,
            reminderWorkRequest
        )
        Log.d("NovaHumanitasUApp", "ReminderWorker programado con política KEEP.")
    }
}