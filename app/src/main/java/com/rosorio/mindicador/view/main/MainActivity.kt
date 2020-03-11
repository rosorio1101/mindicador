package com.rosorio.mindicador.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rosorio.mindicador.R
import com.rosorio.mindicador.api.MindicadorApi
import com.rosorio.mindicador.api.MindicadorResponse
import com.rosorio.mindicador.model.formatValue
import com.rosorio.mindicador.view.commons.ScreenState
import com.rosorio.mindicador.view.commons.viewmodels.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.loadingView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Parcelize
    data class IntentData(
        val username: String?
    ): Parcelable

    companion object {
        const val INTENT_DATA = "INTENT_DATA"

        @JvmStatic
        fun buildIntent(context: Context, data: IntentData): Intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val bundle = Bundle().apply {
                putParcelable(INTENT_DATA, data)
            }
            putExtras(bundle)
        }
    }

    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>

    private lateinit var viewModel: MainViewModel

    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        ContextCompat.getDrawable(this, R.drawable.view_holder_item_divider)?.let {
            val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                setDrawable(it)
            }
            recyclerView.addItemDecoration(divider)
        }

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.state.observe(::getLifecycle,::updateUI)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
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
            is MainState.OnData -> adapter.data = render.list.map {
                MainAdapter.Item(it?.nombre, it?.formatValue(), false)
            }

            is MainState.onError -> showErrorMessage(render.errorMessage)
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
}
