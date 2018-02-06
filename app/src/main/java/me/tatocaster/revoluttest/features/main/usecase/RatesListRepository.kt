package me.tatocaster.revoluttest.features.main.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.tatocaster.revoluttest.data.api.ApiService
import me.tatocaster.revoluttest.entity.Rate
import javax.inject.Inject


interface RatesListRepository {
    fun getFromService(rate: Rate): Single<ArrayList<Rate>>
}

class RatesListRepositoryImpl @Inject constructor(private val apiService: ApiService) : RatesListRepository {
    override fun getFromService(rate: Rate): Single<ArrayList<Rate>> =
            apiService.getRates(rate.name)
                    .flatMap {
                        val ratesList = arrayListOf<Rate>()
                        it.rates.forEach({
                            ratesList.add(Rate(it.key, it.value * rate.rate))
                        })

                        ratesList.add(0, rate) // add as a base, this will be the indicator of the first item
                        Single.just(ratesList)
                    }
                    .subscribeOn(Schedulers.io())
}