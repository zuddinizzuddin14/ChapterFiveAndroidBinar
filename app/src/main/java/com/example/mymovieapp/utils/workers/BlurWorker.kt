package com.example.mymovieapp.utils.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mymovieapp.R
import com.example.mymovieapp.presentation.ui.profileuser.editprofile.EditProfileActivity
import com.example.mymovieapp.utils.ImageUtil
import com.example.mymovieapp.utils.workers.WorkerKeys.IMAGE_INPUT
import com.example.mymovieapp.utils.workers.WorkerKeys.IMAGE_OUTPUT

class BlurWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        showNotification()
        return try {
            val image = inputData.getString(IMAGE_INPUT).toString()

            val blurImage = applyBlur(applicationContext, ImageUtil().stringToBitMap(image))

            val builder = Data.Builder()
            builder.putString(IMAGE_OUTPUT, ImageUtil().bitmapToString(blurImage))

            val outputData = builder.build()

            Result.success(outputData)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }

    private fun applyBlur(context: Context, bitmap: Bitmap): Bitmap {
        val copyBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val outputBitmap = Bitmap.createBitmap(copyBitmap)

        val renderScript = RenderScript.create(context)
        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        val allocationIn = Allocation.createFromBitmap(renderScript, bitmap)
        val allocationOut = Allocation.createFromBitmap(renderScript, outputBitmap)

        scriptIntrinsicBlur.setRadius(10F)
        scriptIntrinsicBlur.setInput(allocationIn)
        scriptIntrinsicBlur.forEach(allocationOut)

        allocationOut.copyTo(outputBitmap)

        return outputBitmap
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
            .setContentText("Blurring Image")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, notification.build())
        }

    }

}