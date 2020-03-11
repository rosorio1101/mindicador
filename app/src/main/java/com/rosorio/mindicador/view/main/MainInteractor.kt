package com.rosorio.mindicador.view.main

import com.rosorio.mindicador.api.MindicadorApi
import com.rosorio.mindicador.api.MindicadorResponse
import com.rosorio.mindicador.model.Indicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainInteractor(private val api: MindicadorApi) {
    interface OnRetrieveDataListener {
        fun onSuccess(indicators: List<Indicator?>)
        fun onError(errorMessage: String)
    }

    fun retrieveIndicators(listener: OnRetrieveDataListener?) {
        api.getIndicators().enqueue(object: Callback<MindicadorResponse> {
            override fun onFailure(call: Call<MindicadorResponse>, t: Throwable) {
                listener?.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<MindicadorResponse>, response: Response<MindicadorResponse>) {
                if(response.isSuccessful) {
                    response.body()?.let { body ->
                        listener?.onSuccess(listOf(
                            body.uf,
                            body.bitcoin,
                            body.dolar,
                            body.dolarIntercambio,
                            body.euro,
                            body.imacec,
                            body.ipc,
                            body.utm,
                            body.ivp,
                            body.libraCobre,
                            body.tasaDesempleo,
                            body.tpm
                        ))
                    }
                } else {
                    listener?.onError(response.errorBody().toString())
                }
            }
        })
    }
}