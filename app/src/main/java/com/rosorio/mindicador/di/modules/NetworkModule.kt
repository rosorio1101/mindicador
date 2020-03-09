package com.rosorio.mindicador.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rosorio.mindicador.BuildConfig
import com.rosorio.mindicador.api.MindicadorApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()


    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_HOST)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    fun provideMindicadorApi(retrofit: Retrofit): MindicadorApi = retrofit.create(MindicadorApi::class.java)
}