package com.rosorio.mindicador.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rosorio.mindicador.model.Indicator
import com.rosorio.mindicador.view.commons.ScreenState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.*
import java.util.*

class MainViewModelTest {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mainInteractor: MainInteractor

    @InjectMocks
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should load data successfully`() {
        val mockedObserver = createStateObserver()
        val mockIndicators = listOf(Indicator(
            "a",
            "a",
            "Pesos",
            Date(),
            1.0
        ))
        doAnswer {
            (it.arguments[0] as MainInteractor.OnRetrieveDataListener).onSuccess(mockIndicators)
            null
        }.`when`(mainInteractor).retrieveIndicators(any())
        mainViewModel.state.observeForever(mockedObserver)
        mainViewModel.loadData()

        verify(mockedObserver).onChanged(ArgumentMatchers.argThat {
            it is ScreenState.Loading
        })

        verify(mockedObserver).onChanged(argThat {
            it is ScreenState.Render<MainState> && it.renderState is MainState.OnData
        })
    }

    @Test
    fun `should show error`() {
        val mockedObserver = createStateObserver()
        doAnswer {
            (it.arguments[0] as MainInteractor.OnRetrieveDataListener).onError("custom error message")
            null
        }.`when`(mainInteractor).retrieveIndicators(any())
        mainViewModel.state.observeForever(mockedObserver)
        mainViewModel.loadData()

        verify(mockedObserver).onChanged(ArgumentMatchers.argThat {
            it is ScreenState.Loading
        })

        verify(mockedObserver).onChanged(argThat {
            it is ScreenState.Render<MainState> && it.renderState is MainState.onError
        })
    }


    private fun createStateObserver(): Observer<ScreenState<MainState>> = Mockito.spy(Observer { })
}