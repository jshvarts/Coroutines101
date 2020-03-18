package com.example.coroutines.repository

import com.example.coroutines.domain.UserDetails
import com.example.coroutines.threading.CoroutineTestRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class UserRepositoryTest {
    @Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
    @get:Rule
    private val coroutinesTestRule = CoroutineTestRule()

    @Test
    fun `should get user details on success`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val userDetails = UserDetails(
            login = "someUsername",
            id = 1,
            avatarUrl = "someAvatarUrl"
        )
        val apiService = mock<ApiService> {
            onBlocking { userDetails("someUsername") } doReturn userDetails
        }

        val repository = UserRepository(apiService, coroutinesTestRule.testDispatcherProvider)

        val flow = repository.userDetails("someUsername")

        val result: Result<UserDetails> = flow.single()

        assertTrue("user details call is success", result.isSuccess)
        assertEquals(userDetails, result.getOrNull())
    }

    @Test
    fun `should get error for user details`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val apiService = mock<ApiService> {
            onBlocking { userDetails("someUsername") } doAnswer {
                throw IOException()
            }
        }

        val repository = UserRepository(apiService, coroutinesTestRule.testDispatcherProvider)

        val flow = repository.userDetails("someUsername")

        val result: Result<UserDetails> = flow.single()

        assertTrue("user details call failed", result.isFailure)
    }

    @Test
    fun `should retry with error and retry failed`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val apiService = mock<ApiService> {
                onBlocking { userDetails("someUsername") } doAnswer {
                    throw IOException()
                }
            }

            val repository = UserRepository(apiService, coroutinesTestRule.testDispatcherProvider)

            val flow = repository.userDetails("someUsername")
            flow.collect { assertTrue("all retries failed with error", it.isFailure) }
        }

    @Test
    fun `should retry with error and retry succeeds`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // runBlockingTest allows advancing time
            var throwError = true

            val userDetails = UserDetails(
                login = "someUsername",
                id = 1,
                avatarUrl = "someAvatarUrl"
            )

            val apiService = mock<ApiService> {
                onBlocking { userDetails("someUsername") } doAnswer {
                    if (throwError) throw IOException() else userDetails
                }
            }

            val repository = UserRepository(apiService, coroutinesTestRule.testDispatcherProvider)

            pauseDispatcher {
                val flow = repository.userDetails("someUsername")
                launch {
                    flow.collect { assertTrue("retry succeeds", it.isSuccess) }
                }

                advanceTimeBy(1000L)
                throwError = false
                //advanceTimeBy(1000L)
            }
        }
}