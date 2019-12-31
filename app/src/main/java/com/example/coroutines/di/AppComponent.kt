package com.example.coroutines.di

import android.app.Application
import com.example.coroutines.views.ReposFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance application: Application): AppComponent
  }

  fun inject(fragment: ReposFragment)
}