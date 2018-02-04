package me.tatocaster.revoluttest

import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        initComponents()
    }

    private fun initComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }

    companion object {
        @JvmStatic
        fun getAppContext(context: Context): App = context.applicationContext as App
    }
}