package com.rosorio.mindicador.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rosorio.mindicador.R
import com.rosorio.mindicador.formatValue
import com.rosorio.mindicador.model.Indicator
import com.rosorio.mindicador.toRawString
import com.rosorio.mindicador.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_DATA = "INTENT_DATA"
        @JvmStatic
        fun buildIntent(context: Context, data: Indicator?): Intent = Intent(context, DetailActivity::class.java).apply {
            val bundle = Bundle().apply {
                if (data != null) putParcelable(INTENT_DATA, data)
            }
            putExtras(bundle)
        }
    }

    var indicator: Indicator? = null
        private set(value) {
            value?.also {
                tvCode.text = it.code?.toUpperCase() ?: ""
                tvName.text = it.name ?: ""
                tvDate.text = it.date?.toRawString() ?: ""
                tvValue.text = it.formatValue()
                title = it.name ?: ""
            }
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        indicator = savedInstanceState?.getParcelable(INTENT_DATA) ?:
                intent?.getParcelableExtra(INTENT_DATA)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(INTENT_DATA, indicator)
        super.onSaveInstanceState(outState)
    }
}
