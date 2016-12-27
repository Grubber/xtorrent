package com.github.xtorrent.xtorrent.trend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.trend.model.TrendResource

/**
 * Created by zhihao.zeng on 16/12/27.
 */
class TrendResourcesFragment : ContentFragment(), TrendResourcesContract.View {
    companion object {
        const val TYPE_MONTH = 1
        const val TYPE_WEEK = 2

        private const val EXTRA_TYPE = "type"

        fun newInstance(type: Int): TrendResourcesFragment {
            val fragment = TrendResourcesFragment()
            val args = Bundle()
            args.putInt(EXTRA_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_trend_resources, container, false)
    }

    private val _type by lazy {
        arguments.getInt(EXTRA_TYPE)
    }

    private lateinit var _presenter: TrendResourcesContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setType(_type)
        _presenter.subscribe()
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: TrendResourcesContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(resources: List<TrendResource>) {
        // TODO

        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }
}