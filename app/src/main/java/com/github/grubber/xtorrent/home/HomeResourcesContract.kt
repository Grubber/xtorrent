package com.github.grubber.xtorrent.home

import com.github.grubber.xtorrent.base.BasePresenter
import com.github.grubber.xtorrent.base.BaseView
import com.github.grubber.xtorrent.home.model.HomeResource

/**
 * Created by grubber on 16/11/29.
 */
interface HomeResourcesContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(resources: List<HomeResource>)
        fun stopRefreshing()
    }

    interface Presenter : BasePresenter {
        fun setType(type: HomeResource.Type)
    }
}