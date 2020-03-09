package com.rosorio.mindicador.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.rosorio.mindicador.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MindicadorApi {

    @GET("api")
    fun getIndicators(): Call<MindicadorResponse>


    companion object {
        fun build(): MindicadorApi {
            return Retrofit.Builder()
            .baseUrl(BuildConfig.API_HOST)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .build().create(MindicadorApi::class.java)
        }
    }
}