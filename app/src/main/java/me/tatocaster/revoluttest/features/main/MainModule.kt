package me.tatocaster.revoluttest.features.main

import dagger.Module
import dagger.Provides

@Module
class MainModule(private val view: MainContract.View) {

    @Provides
    fun provideView(): MainContract.View = this.view

    @Provides
    fun providePresenter(presenter: MainPresenter): MainContract.Presenter = presenter
}