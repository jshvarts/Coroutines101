package com.example.coroutines.di

import com.example.coroutines.threading.DefaultDispatcherProvider
import com.example.coroutines.threading.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ThreadingModule {
    @Binds
    abstract fun bindDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}