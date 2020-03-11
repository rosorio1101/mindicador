package com.rosorio.mindicador

import com.rosorio.mindicador.di.DaggerAppComponent
import com.rosorio.mindicador.security.SecurityManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        SecurityManager.init(BuildConfig.SECRET_KEY, BuildConfig.SECRET_IV)
    }
}