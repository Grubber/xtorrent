package com.github.grubber.xtorrent.main

import android.os.Bundle
import com.github.grubber.xtorrent.R
import com.github.grubber.xtorrent.base.XActivity

/**
 * Created by grubber on 16/11/29.
 */
class MainActivity : XActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideToolbar()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, MainFragment.newInstance())
                .commit()
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.content) as MainFragment).onBackPressed()
    }

    fun exit() {
        super.onBackPressed()
    }
}