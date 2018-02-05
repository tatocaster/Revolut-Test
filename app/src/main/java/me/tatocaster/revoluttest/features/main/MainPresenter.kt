package me.tatocaster.revoluttest.features.main

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import me.tatocaster.revoluttest.data.api.ApiService
import me.tatocaster.revoluttest.entity.Rate
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainPresenter @Inject constructor(private var view: MainContract.View,
                                        private var apiService: ApiService) : MainContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val currenciesUpdateQueue = BehaviorSubject.create<String>()
    private var defaultCurrency = "EUR"

    init {
        currenciesUpdateQueue
                .flatMapSingle {
                    getFromService(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.dataLoaded(it)
                }, {
                    view.showError(it.message!!)
                    Timber.e(it)
                })
    }


    override fun attach() {
        disposables.add(Flowable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribe { _ -> currenciesUpdateQueue.onNext(defaultCurrency) }
        )
    }

    override fun currencySelected(currencyName: String) {
        defaultCurrency = currencyName
        currenciesUpdateQueue.onNext(currencyName)
    }

    private fun getFromService(currencyName: String): Single<ArrayList<Rate>> {
        return apiService.getRates(currencyName)
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

    override fun detach() {
        disposables.clear()
    }
}