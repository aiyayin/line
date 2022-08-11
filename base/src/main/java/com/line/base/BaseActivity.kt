package com.line.base

import android.R
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by ying.fu.
 * Date: 2019/7/26.
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> finish()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("baseActivity> ", "onBackClick")
    }
}