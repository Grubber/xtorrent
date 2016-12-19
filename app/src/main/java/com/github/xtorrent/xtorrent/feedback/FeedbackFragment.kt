package com.github.xtorrent.xtorrent.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_feedback)
    }
}