package com.github.grubber.xtorrent.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.grubber.xtorrent.R
import com.github.grubber.xtorrent.base.XActivity

/**
 * Created by grubber on 16/11/29.
 */
class SettingsActivity : XActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, SettingsFragment.newInstance())
                .commit()
    }
}