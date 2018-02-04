package me.tatocaster.revoluttest.features.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.tatocaster.revoluttest.App
import me.tatocaster.revoluttest.AppComponent
import me.tatocaster.revoluttest.R
import me.tatocaster.revoluttest.utils.showErrorAlert
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {
    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    private lateinit var scopeGraph: MainComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupScopeGraph(App.getAppContext(this).appComponent)

    }

    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun dataLoaded(base: String, rates: Map<String, Double>) {

    }

    override fun onResume() {
        super.onResume()
        mainPresenter.attach()
    }

    override fun onPause() {
        super.onPause()
        mainPresenter.detach()
    }

    private fun setupScopeGraph(appComponent: AppComponent) {
        scopeGraph = DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
        scopeGraph.inject(this)
    }

    companion object {
        val TAG = "MainActivity"
    }
}
