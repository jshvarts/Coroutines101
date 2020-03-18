package com.example.coroutines.repository

import com.example.coroutines.threading.DispatcherProvider
import com.example.coroutines.domain.UserDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val dispatcherProvider: DispatcherProvider
) {

    @ExperimentalCoroutinesApi
    fun userDetails(login: String): Flow<Result<UserDetails>> {
        return flow {
            val userDetails = apiService.userDetails(login)
            emit(Result.success(userDetails))
        }.flowOn(dispatcherProvider.io())
    }
}