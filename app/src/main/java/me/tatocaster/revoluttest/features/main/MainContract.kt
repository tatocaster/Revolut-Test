package me.tatocaster.revoluttest.features.main

class MainContract {
    interface View {
        fun showError(message: String)

        fun dataLoaded(base: String, rates: Map<String, Double>)
    }

    interface Presenter {
        fun attach()

        fun detach()
    }
}