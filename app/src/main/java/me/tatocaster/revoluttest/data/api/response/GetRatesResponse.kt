package me.tatocaster.revoluttest.data.api.response

import java.util.*

/**
 * Created by tatocaster on 04.02.18.
 */
data class GetRatesResponse(val base: String,
                            val date: Date,
                            val rates: Map<String, Double>)