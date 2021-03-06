package com.github.grubber.xtorrent.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import butterknife.bindView
import com.github.grubber.xtorrent.R
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

/**
 * Created by grubber on 16/11/29.
 */
abstract class XActivity : RxAppCompatActivity() {
    val toolbar by bindView<Toolbar>(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_content)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }
}