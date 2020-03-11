package com.rosorio.mindicador.view.main

import com.rosorio.mindicador.model.Indicator

sealed class MainState {
    class onError(val errorMessage: String): MainState()
    class OnData(val list: List<Indicator?>): MainState()
}