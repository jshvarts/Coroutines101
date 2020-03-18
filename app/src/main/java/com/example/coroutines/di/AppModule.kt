package com.example.coroutines.di

import com.example.coroutines.repository.ApiService
import com.example.coroutines.repository.UserRepository
import com.example.coroutines.threading.DefaultDispatcherProvider
import com.example.coroutines.threading.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        schedulers: DispatcherProvider
    ) = UserRepository(apiService, schedulers)

    @Provides
    fun provideSchedulers(): DispatcherProvider = DefaultDispatcherProvider()
}