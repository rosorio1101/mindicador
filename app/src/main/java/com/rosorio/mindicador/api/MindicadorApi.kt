package com.rosorio.mindicador.api

import retrofit2.Call
import retrofit2.http.GET

interface MindicadorApi {

    @GET("api")
    fun getIndicators(): Call<MindicadorResponse>

}