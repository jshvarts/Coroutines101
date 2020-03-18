package com.example.coroutines.threading

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest

class TestDispatcherProvider(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider {
    override fun default(): CoroutineDispatcher = dispatcher
    override fun io(): CoroutineDispatcher = dispatcher
    override fun main(): CoroutineDispatcher = dispatcher
    override fun unconfined(): CoroutineDispatcher = dispatcher

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        dispatcher.runBlockingTest(block)
}
