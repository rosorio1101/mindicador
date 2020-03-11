package com.rosorio.mindicador.di

import com.rosorio.mindicador.App
import com.rosorio.mindicador.di.modules.AppModule
import com.rosorio.mindicador.di.modules.LoginModule
import com.rosorio.mindicador.di.modules.NetworkModule
import com.rosorio.mindicador.di.modules.PreferencesModule
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
        LoginModule::class
    ]
)
interface AppComponent: AndroidInjector<App> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>
}