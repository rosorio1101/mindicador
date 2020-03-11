package com.rosorio.mindicador.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosorio.mindicador.model.Indicator
import com.rosorio.mindicador.view.commons.ScreenState

class MainViewModel(private val interactor: MainInteractor): ViewModel(), MainInteractor.OnRetrieveDataListener {
    private val _state = MutableLiveData<ScreenState<MainState>>()

    val state: LiveData<ScreenState<MainState>>
        get() = _state

    fun loadData() {
        _state.value = ScreenState.Loading
        interactor.retrieveIndicators(this)
    }

    override fun onSuccess(indicators: List<Indicator?>) {
        _state.value = ScreenState.Render(MainState.OnData(indicators))
    }

    override fun onError(errorMessage: String) {
        _state.value = ScreenState.Render(MainState.onError(errorMessage))
    }

    fun signOut() {
        interactor.logout()
        _state.value = ScreenState.Render(MainState.SignOut)
    }
}
