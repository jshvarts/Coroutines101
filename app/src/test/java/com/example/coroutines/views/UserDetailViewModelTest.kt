package com.example.coroutines.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.coroutines.domain.UserDetails
import com.example.coroutines.repository.UserRepository
import com.example.coroutines.threading.CoroutineTestRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val TEST_LOGIN = "someUsername"

class UserDetailViewModelTest {
    @get:Rule
    val rule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock<UserRepository>()

    private val userDetailsObserver = mock<Observer<UserDetails>>()

    private val isErrorObserver = mock<Observer<Boolean>>()

    private lateinit var userDetailViewModel: UserDetailViewModel

    @Before
    fun setUp() {
        userDetailViewModel = UserDetailViewModel(repository).apply {
            userDetails.observeForever(userDetailsObserver)
            isError.observeForever(isErrorObserver)
        }
    }

    @Test
    fun `should emit user details`() = rule.dispatcher.runBlockingTest {
        val userDetails = UserDetails(
            login = TEST_LOGIN,
            id = 1,
            avatarUrl = "someAvatarUrl"
        )

        val result = Result.success(userDetails)
        val channel = Channel<Result<UserDetails>>()
        val flow = channel.consumeAsFlow()

        doReturn(flow)
            .whenever(repository)
            .userDetails(login = TEST_LOGIN)

        launch {
            channel.send(result)
        }

        userDetailViewModel.lookupUser(TEST_LOGIN)

        verify(userDetailsObserver).onChanged(userDetails)
    }

    @Test
    fun `should emit error on failure`() = rule.dispatcher.runBlockingTest {
        val isError = true

        val result = Result.failure<UserDetails>(Exception())
        val channel = Channel<Result<UserDetails>>()
        val flow = channel.consumeAsFlow()

        doReturn(flow)
            .whenever(repository)
            .userDetails(login = TEST_LOGIN)

        launch {
            channel.send(result)
        }

        userDetailViewModel.lookupUser(TEST_LOGIN)

        verify(isErrorObserver).onChanged(isError)
    }
}