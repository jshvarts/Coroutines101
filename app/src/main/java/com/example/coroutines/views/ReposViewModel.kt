package com.example.coroutines.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutines.domain.GithubRepo
import com.example.coroutines.repository.GithubApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ReposViewModel @Inject constructor(private val githubApi: GithubApi) : ViewModel() {
  private val compositedDisposable = CompositeDisposable()

  private val _repos = MutableLiveData<List<GithubRepo>>()

  val repos: LiveData<List<GithubRepo>>
    get() = _repos

  fun lookupRepos() {
    val disposable = githubApi.getRepos("jshvarts")
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        {
          Timber.d("repos: $it")
          _repos.value = it
        }, {
          Timber.e(it)
        }
      )

    compositedDisposable.add(disposable)
  }

  override fun onCleared() {
    super.onCleared()
    compositedDisposable.clear()
  }
}
