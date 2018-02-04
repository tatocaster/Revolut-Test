package me.tatocaster.revoluttest.features.main

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.tatocaster.revoluttest.data.api.ApiService
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