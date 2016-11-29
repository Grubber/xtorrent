package com.github.xtorrent.xtorrent.trend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.XFragment

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class TrendFragment : XFragment() {
    companion object {
        fun newInstance(): TrendFragment {
            return TrendFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_trend, container, false)
    }

    override fun getTitle(): String? {
        return null
    }
}