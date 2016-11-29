package com.github.xtorrent.xtorrent.home

import com.github.xtorrent.xtorrent.base.BasePresenter
import com.github.xtorrent.xtorrent.base.BaseView
import com.github.xtorrent.xtorrent.home.model.HomeResource

/**
 * Created by zhihao.zeng on 16/11/29.
 */
interface HomeResourcesContract {
    interface View : BaseView<Presenter> {
        fun setLoadingView()
        fun setErrorView()
        fun setEmptyView()
        fun setContentView(resources: List<HomeResource>)
        fun stopRefreshing()
    }

    interface Presenter : BasePresenter {
        fun setType(type: HomeResource.Type)
    }
}