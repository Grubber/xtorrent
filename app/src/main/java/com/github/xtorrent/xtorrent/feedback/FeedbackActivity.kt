package com.github.xtorrent.xtorrent.feedback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.XActivity

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class FeedbackActivity : XActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, FeedbackFragment.newInstance())
                .commit()
    }
}