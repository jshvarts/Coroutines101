package com.example.coroutines.repository

import com.example.coroutines.domain.UserDetails
import com.example.coroutines.threading.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val apiService: ApiService,
    private val dispatchers: DispatcherProvider
) {

    @ExperimentalCoroutinesApi
    fun userDetails(login: String): Flow<Result<UserDetails>> {
        return flow {
            val userDetails = apiService.userDetails(login)
            emit(Result.success(userDetails))
        }.flowOn(dispatchers.io())
    }
}