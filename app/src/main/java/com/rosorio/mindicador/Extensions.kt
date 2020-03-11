package com.rosorio.mindicador

import com.rosorio.mindicador.model.Indicator
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Indicator.formatValue() : String = when(unitMeasurement) {
    "Porcentaje" -> DecimalFormat("##.##%").format(value?.div(100))
    "Dólar" -> NumberFormat.getCurrencyInstance(Locale.US).format(value)
    else -> NumberFormat.getCurrencyInstance(Locale.US).apply {
        currency = Currency.getInstance("CLP")
    }.format(value?.toInt())
}

private val dateFormat by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'")
}

fun Date.toRawString(): String = dateFormat.format(this)