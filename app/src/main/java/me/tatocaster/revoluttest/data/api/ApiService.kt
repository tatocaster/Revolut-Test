package me.tatocaster.revoluttest.data.api

import io.reactivex.Flowable
import me.tatocaster.revoluttest.data.api.response.GetRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/latest")
    fun getRates(@Query("base") currency: String): Flowable<GetRatesResponse>
}