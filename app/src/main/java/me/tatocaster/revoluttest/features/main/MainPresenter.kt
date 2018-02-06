package me.tatocaster.revoluttest.features.main

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import me.tatocaster.revoluttest.features.main.usecase.RatesListRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainPresenter @Inject constructor(private var view: MainContract.View,
                                        private val repository: RatesListRepository) : MainContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val currenciesUpdateQueue = BehaviorSubject.create<String>()
    private var defaultCurrency = "EUR"

    init {
        currenciesUpdateQueue
                .flatMapSingle {
                    repository.getFromService(it)
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

    override fun detach() {
        disposables.clear()
    }
}