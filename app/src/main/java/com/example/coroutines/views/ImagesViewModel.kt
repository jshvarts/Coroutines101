package com.example.coroutines.views

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutines.repository.ImageRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ImagesViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private val _images = MutableLiveData<Pair<Bitmap, Bitmap>>()

    val images: LiveData<Pair<Bitmap, Bitmap>>
        get() = _images

    fun lookupImages() {
        val img1Observable = Observable.fromCallable {
            imageRepository.downloadImage("photo-1577711456630-1daf4a628e46?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjEwODczNX0")
        }.subscribeOn(Schedulers.io())

        val img2Observable = Observable.fromCallable {
            imageRepository.downloadImage("photo-1562886877-0be0db6aba84?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjEwODczNX0")
        }.subscribeOn(Schedulers.io())

        Observable.zip(img1Observable, img2Observable,
            BiFunction<Bitmap, Bitmap, Pair<Bitmap, Bitmap>> { bitmap1, bitmap2 ->
                Pair(
                    bitmap1,
                    bitmap2
                )
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _images.value = it
            }, { throwable ->
                Timber.e(throwable, "Error occurred downloading images.")
                _isError.value = true
            }
            ).also {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
