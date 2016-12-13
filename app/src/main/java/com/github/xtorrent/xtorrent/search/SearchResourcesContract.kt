package com.github.xtorrent.xtorrent.search

import com.github.xtorrent.xtorrent.base.BasePresenter
import com.github.xtorrent.xtorrent.base.BaseView
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem

/**
 * Created by zhihao.zeng on 16/11/29.
 */
interface SearchResourcesContract {
    interface View : BaseView<Presenter> {
        fun setLoadingView()
        fun setErrorView()
        fun setEmptyView()
        fun setContentView(list: List<Pair<Resource, List<ResourceItem>>>)
    }

    interface Presenter : BasePresenter {
        fun setKeyword(keyword: String)
        fun setPageNumber(pageNumber: Int)
    }
}