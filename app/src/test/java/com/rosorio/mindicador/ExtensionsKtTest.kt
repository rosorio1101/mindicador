package com.rosorio.mindicador

import com.rosorio.mindicador.model.Indicator
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ExtensionsKtTest {
    @Test
    fun `formatValue should format Dolar`() {
        val indicator = Indicator(
            "",
            "",
            "Dólar",
            Date(),
            10.0
        )
        val formatted = indicator.formatValue()
        assertEquals("$10.00",formatted)
    }

    @Test
    fun `formatValue should format Porcentaje`() {
        val indicator = Indicator(
            "",
            "",
            "Porcentaje",
            Date(),
            0.7
        )
        val formatted = indicator.formatValue()
        assertEquals("0,7%",formatted)
    }

    @Test
    fun `formatValue should format Peso`() {
        val indicator = Indicator(
            "",
            "",
            "Peso",
            Date(),
            1.0
        )
        val formatted = indicator.formatValue()
        assertEquals("CLP1.00",formatted)
    }
}