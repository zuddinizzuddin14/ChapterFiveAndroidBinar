package com.example.mymovieapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ImageUtil {

    fun applyBlur(context: Context, bitmap: Bitmap): Bitmap {
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

    private fun saveToInternalStorage(context: Context, bitmapImage: Bitmap): String {
        val directory = context.getDir("imageDir", Context.MODE_PRIVATE)
        val fos = FileOutputStream(File(directory, "profile.jpg"))
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()
        return directory.absolutePath
    }
    fun storageToBitmap(path: String): Bitmap {
        val file = File(path, "profile.jpg")
        return BitmapFactory.decodeStream(FileInputStream(file))
    }

    fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    fun stringToBitMap(string: String): Bitmap {
        val imageBytes = Base64.decode(string, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

}