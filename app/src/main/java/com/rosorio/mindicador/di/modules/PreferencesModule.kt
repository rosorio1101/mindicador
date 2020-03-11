package com.rosorio.mindicador.di.modules

import android.content.Context
import com.rosorio.mindicador.BuildConfig
import com.rosorio.mindicador.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context) = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE)
}