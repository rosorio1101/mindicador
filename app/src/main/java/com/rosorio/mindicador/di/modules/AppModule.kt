package com.rosorio.mindicador.di.modules

import android.content.Context
import com.rosorio.mindicador.App
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @Binds
    fun bindContext(app: App): Context
}