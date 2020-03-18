package com.example.coroutines.repository

import com.example.coroutines.domain.UserDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
  private val apiService: ApiService
) {

  @ExperimentalCoroutinesApi
  fun userDetails(login: String): Flow<Result<UserDetails>> {
    return flow {
      val userDetails = apiService.userDetails(login)
      emit(Result.success(userDetails))
    }
  }
}