package com.example.coroutines.repository

import com.example.coroutines.domain.UserDetails
import com.example.coroutines.threading.CoroutineTestRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class UserRepositoryTest {
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
}