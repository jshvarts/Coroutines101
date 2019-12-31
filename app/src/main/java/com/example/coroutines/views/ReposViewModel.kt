package com.example.coroutines.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutines.domain.GithubRepo
import com.example.coroutines.repository.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ReposViewModel @Inject constructor(private val githubApi: GithubApi) : ViewModel(),
  CoroutineScope {

  private val job = Job()

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  private val _repos = MutableLiveData<List<GithubRepo>>()

  val repos: LiveData<List<GithubRepo>>
    get() = _repos

  fun lookupRepos() {
    launch {
      _repos.value = githubApi.getRepos("jshvarts")
    }
  }

  override fun onCleared() {
    job.cancel()
    super.onCleared()
  }
}
