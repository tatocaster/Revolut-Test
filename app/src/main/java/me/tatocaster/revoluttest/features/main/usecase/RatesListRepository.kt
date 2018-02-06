package me.tatocaster.revoluttest.features.main.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.tatocaster.revoluttest.data.api.ApiService
import me.tatocaster.revoluttest.entity.Rate
import javax.inject.Inject


interface RatesListRepository {
    fun getFromService(currencyName: String): Single<ArrayList<Rate>>
}

class RatesListRepositoryImpl @Inject constructor(private val apiService: ApiService) : RatesListRepository {
    override fun getFromService(currencyName: String): Single<ArrayList<Rate>> =
            apiService.getRates(currencyName)
                    .flatMap {
                        val ratesList = arrayListOf<Rate>()
                        it.rates.forEach({
                            ratesList.add(Rate(it.key, it.value))
                        })
                        ratesList.add(0, Rate(currencyName, 1.00)) // add as a base
                        Single.just(ratesList)
                    }
                    .subscribeOn(Schedulers.io())
}