package me.tatocaster.revoluttest.features.main

import me.tatocaster.revoluttest.entity.Rate

class MainContract {
    interface View {
        fun showError(message: String)

        fun dataLoaded(base: String, rates: ArrayList<Rate>)
    }

    interface Presenter {
        fun attach()

        fun detach()
    }
}