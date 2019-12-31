package com.example.coroutines.repository

import com.example.coroutines.domain.GithubRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
  @GET("users/{username}/repos") fun getRepos(
    @Path("username") username: String
  ): Single<List<GithubRepo>>

  @GET("repos/{username}/{repoName}")
  fun getRepo(
    @Path("username") username: String,
    @Path("repoName") repoName: String
  ): Single<GithubRepo>
}