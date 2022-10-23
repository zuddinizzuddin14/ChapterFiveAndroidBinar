package com.example.mymovieapp.utils.workers

import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mymovieapp.R
import com.example.mymovieapp.presentation.ui.profileuser.editprofile.EditProfileActivity
import com.example.mymovieapp.utils.ImageUtil
import com.example.mymovieapp.utils.workers.WorkerKeys.IMAGE_OUTPUT
import java.text.SimpleDateFormat
import java.util.*

class SaveWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )

    override fun doWork(): Result {
        showNotification()

        val resolver = applicationContext.contentResolver
        return try {
            val image = inputData.getString(IMAGE_OUTPUT).toString()
            val bitmap = ImageUtil().stringToBitMap(image)
            val imageUrl = MediaStore.Images.Media.insertImage(
                resolver, bitmap, title, dateFormatter.format(Date()))
            if (!imageUrl.isNullOrEmpty()) {
                val output = workDataOf(IMAGE_OUTPUT to imageUrl)
                Result.success(output)
            } else {
                Log.e(TAG, "Writing to MediaStore Failed")
                Result.failure()
            }
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }

    private fun showNotification() {

        val intent = Intent(applicationContext, EditProfileActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0, intent, 0
        )

        val notification = NotificationCompat.Builder(
            applicationContext
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("WorkRequest Starting")
            .setContentText("Saving Image")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, notification.build())
        }

    }
}