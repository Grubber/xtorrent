package com.github.xtorrent.xtorrent.search.source

import com.github.xtorrent.xtorrent.search.SearchResourcesPresenterComponent
import com.github.xtorrent.xtorrent.search.SearchResourcesPresenterModule
import dagger.Subcomponent

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@SearchResourcesScope
@Subcomponent(modules = arrayOf(SearchResourcesRepositoryModule::class))
interface SearchResourcesRepositoryComponent {
    fun plus(searchResourcesPresenterModule: SearchResourcesPresenterModule): SearchResourcesPresenterComponent
}