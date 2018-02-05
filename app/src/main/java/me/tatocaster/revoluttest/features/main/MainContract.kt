package me.tatocaster.revoluttest.features.main

import me.tatocaster.revoluttest.entity.Rate

class MainContract {
    interface View {
        fun showError(message: String)

        fun dataLoaded(rates: ArrayList<Rate>)
    }

    interface Presenter {
        fun attach()

        fun currencySelected(currencyName: String)

        fun detach()
    }
}