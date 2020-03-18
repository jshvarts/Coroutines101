package com.example.coroutines.repository

import com.example.coroutines.domain.UserDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

  @GET("users/{login}")
  suspend fun userDetails(@Path("login") login: String): UserDetails
}