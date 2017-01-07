package com.github.xtorrent.xtorrent.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.XFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

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

    private val _adView by bindView<AdView>(R.id.adView)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        _adView.loadAd(adRequest)
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_feedback)
    }
}