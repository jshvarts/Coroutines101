package com.example.coroutines.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.URL
import javax.inject.Inject

private const val BASE_IMAGE_URL = "https://images.unsplash.com/"

class ImageRepository @Inject constructor() {
  /**
   * @param imagePath excluding base url
   */
  suspend fun downloadImage(imagePath: String): Bitmap {
    val url = URL("$BASE_IMAGE_URL$imagePath")
    Timber.d("Downloading image $url")

    return withContext(Dispatchers.IO) {
      BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }
  }
}