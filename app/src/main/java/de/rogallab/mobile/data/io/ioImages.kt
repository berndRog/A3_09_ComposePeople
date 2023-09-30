package de.rogallab.mobile.data.io

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toFile
import java.io.File
import java.util.*

fun readImageFromInternalStorage(uri: Uri): Bitmap? {
   uri.toFile().apply {
      return BitmapFactory.decodeFile(this.absolutePath)
   }
}

fun writeImageToInternalStorage(context: Context, bitmap: Bitmap): String? {
// .../app_images/...
// val files:  File = context.filesDir
   val images: File = context.getDir("images", Context.MODE_PRIVATE)
   val fileName: String = "${UUID.randomUUID().toString()}.jpg"

   var uriPath: String? = null
   File(images, fileName).apply {
      this.outputStream().use { out ->
         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
         out.flush()
      }
      Uri.fromFile(this)?.let {
         uriPath = it.path
      }
   }
   return uriPath
}

fun deleteFileOnInternalStorage(context: Context, fileName:String) {
   context.deleteFile(fileName)
}