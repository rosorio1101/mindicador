package com.rosorio.mindicador.view.commons

sealed class ScreenState<out T> {
    object Loading: ScreenState<Nothing>()
    class Render<T>(val renderState: T): ScreenState<T>()
}