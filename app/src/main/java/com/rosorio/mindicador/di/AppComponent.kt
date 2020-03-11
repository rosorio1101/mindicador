package com.rosorio.mindicador.di

import com.rosorio.mindicador.App
import com.rosorio.mindicador.di.modules.*
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        PreferencesModule::class,
        NetworkModule::class,
        LoginModule::class,
        MainModule::class
    ]
)
interface AppComponent: AndroidInjector<App> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>
}