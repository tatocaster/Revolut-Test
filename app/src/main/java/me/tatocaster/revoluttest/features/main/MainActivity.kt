package me.tatocaster.revoluttest.features.main

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.tatocaster.revoluttest.App
import me.tatocaster.revoluttest.AppComponent
import me.tatocaster.revoluttest.R
import me.tatocaster.revoluttest.entity.Rate
import me.tatocaster.revoluttest.utils.ItemSeparatorDecoration
import me.tatocaster.revoluttest.utils.showErrorAlert
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View {
    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    private lateinit var scopeGraph: MainComponent

    private lateinit var adapter: RatesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupScopeGraph(App.getAppContext(this).appComponent)
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        adapter = RatesListAdapter { currencyName ->
            mainPresenter.currencySelected(currencyName)
        }
        ratesList.adapter = adapter
        ratesList.layoutManager = LinearLayoutManager(this)
        ratesList.setHasFixedSize(true)
        val separator = ItemSeparatorDecoration(this, ContextCompat.getColor(this, R.color.item_separator), 1f)
        ratesList.addItemDecoration(separator)
        ratesList.setEmptyView(emptyView)
    }

    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun dataLoaded(rates: ArrayList<Rate>) {
        adapter.updateData(rates)
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
