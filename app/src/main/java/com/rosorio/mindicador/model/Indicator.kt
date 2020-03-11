package com.rosorio.mindicador.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@Parcelize
data class Indicator(
    @SerializedName("codigo")
    val code: String?,
    @SerializedName("nombre")
    val name: String?,
    @SerializedName("unidad_medida")
    val unitMeasurement: String?,
    @SerializedName("fecha")
    val date: Date?,
    @SerializedName("valor")
    val value: Double?
): Parcelable