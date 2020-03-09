package com.rosorio.mindicador

import com.rosorio.mindicador.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<App>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)
    }
}