package com.example.coroutines.repository

import com.example.coroutines.domain.GithubRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

  // Internally, Retrofit provides main-safety by doing suspending function work with
  // withContext(Dispatchers.IO)

  @GET("users/{username}/repos")
  suspend fun getRepos(
    @Path("username") username: String
  ): List<GithubRepo>

  @GET("repos/{username}/{repoName}")
  fun getRepo(
    @Path("username") username: String,
    @Path("repoName") repoName: String
  ): GithubRepo
}