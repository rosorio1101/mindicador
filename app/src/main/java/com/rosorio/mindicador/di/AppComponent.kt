package com.rosorio.mindicador.di

import com.rosorio.mindicador.App
import com.rosorio.mindicador.di.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class
    ]
)
interface AppComponent: AndroidInjector<App>