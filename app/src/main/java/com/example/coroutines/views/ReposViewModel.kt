package com.example.coroutines.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.domain.GithubRepo
import com.example.coroutines.repository.GithubApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReposViewModel @Inject constructor(private val githubApi: GithubApi) : ViewModel() {

  private val _repos = MutableLiveData<List<GithubRepo>>()

  val repos: LiveData<List<GithubRepo>>
    get() = _repos

  fun lookupRepos() {
    // Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared.
    viewModelScope.launch {
      _repos.value = githubApi.getRepos("jshvarts")
    }
  }
}
