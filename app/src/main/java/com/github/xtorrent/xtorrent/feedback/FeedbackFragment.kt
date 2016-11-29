package com.github.xtorrent.xtorrent.feedback

import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.XFragment

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class FeedbackFragment : XFragment() {
    companion object {
        fun newInstance(): FeedbackFragment {
            return FeedbackFragment()
        }
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_feedback)
    }
}