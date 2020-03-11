package com.rosorio.mindicador.di.modules

import android.content.SharedPreferences
import com.rosorio.mindicador.datasources.LoginDataSource
import com.rosorio.mindicador.datasources.PreferencesLoginDataSources
import dagger.Module
import dagger.Provides

@Module
abstract class DataSourceModule {
    companion object {
        @JvmStatic
        @Provides
        fun provideLoginDataSource(preferences: SharedPreferences): LoginDataSource =
            PreferencesLoginDataSources(preferences)
    }
}