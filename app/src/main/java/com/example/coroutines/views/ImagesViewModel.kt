package com.example.coroutines.views

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.repository.ImageRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagesViewModel @Inject constructor(private val imageRepository: ImageRepository) :
  ViewModel() {

  private val _images = MutableLiveData<Pair<Bitmap, Bitmap>>()

  val images: LiveData<Pair<Bitmap, Bitmap>>
    get() = _images

  fun lookupImages() {
    viewModelScope.launch {
      val image1 = async {
        imageRepository.downloadImage("photo-1577711456630-1daf4a628e46?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjEwODczNX0")
      }

      val image2 = async {
        imageRepository.downloadImage("photo-1562886877-0be0db6aba84?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjEwODczNX0")
      }

      _images.value = Pair(image1.await(), image2.await())
    }
  }
}
