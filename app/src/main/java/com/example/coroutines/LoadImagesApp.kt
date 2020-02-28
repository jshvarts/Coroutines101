package com.example.coroutines

import android.app.Application
import com.example.coroutines.di.AppComponent
import com.example.coroutines.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class LoadImagesApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appComponent = DaggerAppComponent.factory().create(this)

        RxJavaPlugins.setErrorHandler { t -> Timber.e(t) }
    }
}
