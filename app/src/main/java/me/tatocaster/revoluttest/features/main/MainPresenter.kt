package me.tatocaster.revoluttest.features.main

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.tatocaster.revoluttest.data.api.ApiService
import me.tatocaster.revoluttest.entity.Rate
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainPresenter @Inject constructor(private var view: MainContract.View,
                                        private var apiService: ApiService) : MainContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()


    override fun attach() {
        disposables.add(
                Flowable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                        .flatMap {
                            println("$it")
                            apiService.getRates("EUR")
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val ratesList = arrayListOf<Rate>()
                            it.rates.forEach({
                                ratesList.add(Rate(it.key, it.value))
                            })
                            view.dataLoaded(it.base, ratesList)
                        }, {
                            view.showError(it.message!!)
                            Timber.e(it)
                        })
        )
    }

    override fun detach() {
        disposables.clear()
    }
}