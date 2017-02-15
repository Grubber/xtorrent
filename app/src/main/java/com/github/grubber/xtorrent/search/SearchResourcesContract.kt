package com.github.grubber.xtorrent.search

import com.github.grubber.xtorrent.base.BasePresenter
import com.github.grubber.xtorrent.base.BaseView
import com.github.grubber.xtorrent.search.model.Resource
import com.github.grubber.xtorrent.search.model.ResourceItem

/**
 * Created by grubber on 16/11/29.
 */
interface SearchResourcesContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(list: List<Pair<Resource, List<ResourceItem>>>? = null, loadedError: Boolean = false)
    }

    interface Presenter : BasePresenter {
        fun setKeyword(keyword: String)
        fun setPageNumber(pageNumber: Int)
    }
}