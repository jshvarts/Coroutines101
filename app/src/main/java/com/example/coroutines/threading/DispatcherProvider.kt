package com.example.coroutines.threading

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

// alternatively, use https://github.com/RBusarow/Dispatch

interface DispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class DefaultDispatcherProvider @Inject constructor() : DispatcherProvider