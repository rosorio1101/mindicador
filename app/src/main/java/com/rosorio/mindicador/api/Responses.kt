package com.rosorio.mindicador.api

import java.util.Date

data class Indicator(
    val codigo: String?,
    val nombre: String?,
    val unidadMedida: String?,
    val fecha: Date?,
    val valor: Double?
)

data class MindicadorResponse(
    val version: String?,
    val autor: String?,
    val fecha: Date?,
    val uf: Indicator?,
    val ivp: Indicator?,
    val dolar: Indicator?,
    val dolarIntercambio: Indicator?,
    val euro: Indicator?,
    val ipc: Indicator?,
    val utm: Indicator?,
    val imacec: Indicator?,
    val tpm: Indicator?,
    val libraCobre: Indicator?,
    val tasaDesempleo: Indicator?,
    val bitcoin: Indicator?
)