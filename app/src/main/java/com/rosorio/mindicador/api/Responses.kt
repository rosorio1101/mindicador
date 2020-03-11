package com.rosorio.mindicador.api

import com.rosorio.mindicador.model.Indicator
import java.util.Date


data class MindicadorResponse(
    val version: String? = null,
    val autor: String? = null,
    val fecha: Date? = null,
    val uf: Indicator? = null,
    val ivp: Indicator? = null,
    val dolar: Indicator? = null,
    val dolarIntercambio: Indicator? = null,
    val euro: Indicator? = null,
    val ipc: Indicator? = null,
    val utm: Indicator? = null,
    val imacec: Indicator? = null,
    val tpm: Indicator? = null,
    val libraCobre: Indicator? = null,
    val tasaDesempleo: Indicator? = null,
    val bitcoin: Indicator? = null
)