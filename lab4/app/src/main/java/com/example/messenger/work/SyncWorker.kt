package com.example.messenger.work

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messenger.MyApplication
import com.example.messenger.R
import com.example.messenger.data.local.AppDatabase
import com.example.messenger.data.network.RetrofitClient
import com.example.messenger.data.repository.MessageRepository

class SyncWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return try {
            val repository = MessageRepository(
                apiService = RetrofitClient.apiService,
                dao = AppDatabase.getInstance(applicationContext).messageDao(),
                context = applicationContext
            )
            repository.getMessages()
            showSyncNotification()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showSyncNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(applicationContext, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_feed)
            .setContentTitle(applicationContext.getString(R.string.notification_sync_title))
            .setContentText(applicationContext.getString(R.string.notification_sync_text))
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1001
    }
}
