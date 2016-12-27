package com.github.xtorrent.xtorrent.trend

import com.github.xtorrent.xtorrent.base.BasePresenter
import com.github.xtorrent.xtorrent.base.BaseView
import com.github.xtorrent.xtorrent.trend.model.TrendResource

/**
 * Created by zhihao.zeng on 16/12/27.
 */
interface TrendResourcesContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(resources: List<TrendResource>)
    }

    interface Presenter : BasePresenter {
        fun setType(type: Int)
    }
}