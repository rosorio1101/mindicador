package com.rosorio.mindicador.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rosorio.mindicador.R
import com.rosorio.mindicador.formatValue
import com.rosorio.mindicador.model.Indicator
import com.rosorio.mindicador.view.commons.ScreenState
import com.rosorio.mindicador.view.commons.viewmodels.ViewModelFactory
import com.rosorio.mindicador.view.detail.DetailActivity
import com.rosorio.mindicador.view.login.LoginActivity
import dagger.android.AndroidInjection
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_login.loadingView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    @Parcelize
    data class IntentData(
        val username: String?
    ): Parcelable

    companion object {
        const val INTENT_DATA = "INTENT_DATA"

        @JvmStatic
        fun buildIntent(context: Context, data: IntentData): Intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val bundle = Bundle().apply {
                putParcelable(INTENT_DATA, data)
            }
            putExtras(bundle)
        }
    }

    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>

    private lateinit var viewModel: MainViewModel

    private val onItemSelectedListener: OnItemSelectedListener = { item ->
        val indicator = indicators.firstOrNull { it?.name == item?.name }
        startActivity(DetailActivity.buildIntent(this, indicator))
    }

    private val adapter by lazy {
        MainAdapter().apply {
            listener = onItemSelectedListener
        }
    }

    private val indicators = mutableListOf<Indicator?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        svSearchCode.setOnQueryTextListener(this)
        configureRecyclerView()

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.state.observe(::getLifecycle,::updateUI)
        intent?.extras?.getParcelable<IntentData>(INTENT_DATA)?.also { (username) ->
            title = "Hola $username"
        }
    }

    override fun onStart() {
        super.onStart()
        if(indicators.isNullOrEmpty())
            viewModel.loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.logout -> {
                viewModel.signOut()
            }
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        filterIndicators(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filterIndicators(newText)
        return true
    }

    private fun configureRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        ContextCompat.getDrawable(this, R.drawable.view_holder_item_divider)?.also {
            val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                setDrawable(it)
            }
            recyclerView.addItemDecoration(divider)
        }
    }

    private fun updateUI(screenState: ScreenState<MainState>) {
        when(screenState) {
            ScreenState.Loading -> showLoading()
            is ScreenState.Render -> {
                hideLoading()
                updateRenderState(screenState.renderState)
            }
        }
    }

    private fun updateRenderState(render: MainState) {
        when(render) {
            is MainState.OnData -> {
                indicators.clear()
                indicators.addAll(render.list)
                populateAdapter(indicators)
            }
            is MainState.onError -> showErrorMessage(render.errorMessage)
            is MainState.SignOut -> goToLogin()
        }
    }

    private fun filterIndicators(code: String? ){
        val filteredIndicator = indicators.filter { it?.code?.toLowerCase()?.contains(code?.toLowerCase() ?: "") ?: false }
        populateAdapter(filteredIndicator)
    }

    private fun populateAdapter(indicators: List<Indicator?>) {
        adapter.data = indicators.map {
            MainAdapter.Item(it?.name, it?.formatValue(), false)
        }
    }

    private fun showLoading(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        loadingView.showLoading()
    }

    private fun hideLoading() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        loadingView.hideLoading()
    }

    private fun showErrorMessage(errorMessage: String){
        Snackbar.make(container, errorMessage, Snackbar.LENGTH_SHORT).show()
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
