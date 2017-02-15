package com.github.grubber.xtorrent.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.grubber.xtorrent.R
import com.github.grubber.xtorrent.base.XFragment

/**
 * Created by grubber on 16/11/29.
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