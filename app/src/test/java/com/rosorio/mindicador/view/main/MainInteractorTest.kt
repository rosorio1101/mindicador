package com.rosorio.mindicador.view.main

import com.rosorio.mindicador.api.MindicadorApi
import com.rosorio.mindicador.api.MindicadorResponse
import com.rosorio.mindicador.model.Indicator
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainInteractorTest {
    @Mock
    lateinit var api: MindicadorApi
    @InjectMocks
    lateinit var interactor: MainInteractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should retrieve data`() {
        val mockCall: Call<MindicadorResponse> = mock(Call::class.java) as Call<MindicadorResponse>
        val mockMindicadorResponse = MindicadorResponse(
            version = "",
            autor = "",
            fecha = null,
            uf = Indicator(
                "a",
                "a",
                "Pesos",
                Date(),
                1.0
            )
        )
        val mockListener = mock(MainInteractor.OnRetrieveDataListener::class.java)

            `when`(api.getIndicators()).thenReturn(mockCall)

        doAnswer {
            (it.arguments[0] as Callback<MindicadorResponse>).onResponse(mockCall, Response.success(
                mockMindicadorResponse
            ))
            null
        }.`when`(mockCall).enqueue(any())

        interactor.retrieveIndicators(mockListener)

        verify(mockListener).onSuccess(anyList())
    }
}