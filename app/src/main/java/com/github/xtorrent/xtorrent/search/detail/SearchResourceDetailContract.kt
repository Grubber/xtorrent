package com.github.xtorrent.xtorrent.search.detail

import com.github.xtorrent.xtorrent.base.BasePresenter
import com.github.xtorrent.xtorrent.base.BaseView
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem

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