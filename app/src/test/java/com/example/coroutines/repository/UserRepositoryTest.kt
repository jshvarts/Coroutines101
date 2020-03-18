package com.example.coroutines.repository

import com.example.coroutines.domain.UserDetails
import com.example.coroutines.threading.TestDispatcherProvider
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class UserRepositoryTest {

    private val testDispatcherProvider = TestDispatcherProvider()

    @Test
    fun `should get user details on success`() = testDispatcherProvider.runBlockingTest {
        val userDetails = UserDetails(
            login = "someUsername",
            id = 1,
            avatarUrl = "someAvatarUrl"
        )
        val apiService = mock<ApiService> {
            onBlocking { userDetails("someUsername") } doReturn userDetails
        }

        val repository = UserRepository(apiService, testDispatcherProvider)

        val flow = repository.userDetails("someUsername")

        val result: Result<UserDetails> = flow.single()

        assertTrue("user details call is success", result.isSuccess)
        assertEquals(userDetails, result.getOrNull())
    }

    @Test
    fun `should get error for user details`() = testDispatcherProvider.runBlockingTest {
        val apiService = mock<ApiService> {
            onBlocking { userDetails("someUsername") } doAnswer {
                throw IOException()
            }
        }

        val repository = UserRepository(apiService, testDispatcherProvider)

        val flow = repository.userDetails("someUsername")

        val result: Result<UserDetails> = flow.single()

        assertTrue("user details call failed", result.isFailure)
    }

    @Test
    fun `should retry with error and retry failed`() = testDispatcherProvider.runBlockingTest {
        val apiService = mock<ApiService> {
            onBlocking { userDetails("someUsername") } doAnswer {
                throw IOException()
            }
        }

        val repository = UserRepository(apiService, testDispatcherProvider)

        val flow = repository.userDetails("someUsername")
        flow.collect { assertTrue("all retries failed with error", it.isFailure) }
    }

//    @Test
//    fun `should retry with error and retry succeeds`() = testDispatcher.runBlockingTest {
//        // runBlockingTest allows advancing time
//        var throwError = true
//
//        val userDetails = UserDetails(
//            login = "someUsername",
//            id = 1,
//            avatarUrl = "someAvatarUrl"
//        )
//
//        val apiService = mock<ApiService> {
//            onBlocking { userDetails("someUsername") } doAnswer {
//                if (throwError) throw IOException() else userDetails
//            }
//        }
//
//        val repository = UserRepository(apiService, testDispatcherProvider)
//
//        pauseDispatcher {
//            val flow = repository.userDetails("someUsername")
//            launch {
//                flow.collect { assertTrue("retry succeeds", it.isSuccess) }
//            }
//
//            advanceTimeBy(1000L)
//            throwError = false
//            advanceTimeBy(1000L)
//        }
//    }
}