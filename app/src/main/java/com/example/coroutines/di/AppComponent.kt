package com.example.coroutines.di

import android.app.Application
import com.example.coroutines.views.ImagesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance application: Application): AppComponent
  }

  fun inject(fragment: ImagesFragment)
}