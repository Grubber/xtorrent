package com.github.grubber.xtorrent.search.detail

import com.github.grubber.xtorrent.base.BasePresenter
import com.github.grubber.xtorrent.base.BaseView
import com.github.grubber.xtorrent.search.model.Resource
import com.github.grubber.xtorrent.search.model.ResourceItem

/**
 * Created by grubber on 16/11/29.
 */
interface SearchResourceDetailContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(data: Pair<Resource, List<ResourceItem>>)
    }

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
        fun downloadTorrent(magnet: String, name: String)
    }
}