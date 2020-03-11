package com.rosorio.mindicador.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@Parcelize
data class Indicator(
    val codigo: String?,
    val nombre: String?,
    val unidadMedida: String?,
    val fecha: Date?,
    val valor: Double?
): Parcelable


fun Indicator.formatValue() : String = when(unidadMedida) {
    "Porcentaje" -> DecimalFormat("##.##%").format(valor?.div(100))
    "Dólar" -> NumberFormat.getCurrencyInstance(Locale.US).format(valor)
    else -> NumberFormat.getCurrencyInstance(Locale.US).apply {
        currency = Currency.getInstance("CLP")
    }.format(valor)
}