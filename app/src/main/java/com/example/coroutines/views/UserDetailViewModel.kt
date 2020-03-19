package com.example.coroutines.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.domain.UserDetails
import com.example.coroutines.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()

    val userDetails: LiveData<UserDetails>
        get() = _userDetails


    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    @ExperimentalCoroutinesApi
    fun lookupUser(login: String) {

        viewModelScope.launch {
            val flow = userRepository.userDetails(login)
            flow.collect { result: Result<UserDetails> ->
                when {
                    result.isSuccess -> _userDetails.value = result.getOrNull()
                    result.isFailure -> _isError.value = true
                }
            }
        }
    }
}
