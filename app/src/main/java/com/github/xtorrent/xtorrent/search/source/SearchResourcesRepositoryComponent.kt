package com.github.xtorrent.xtorrent.search.source

import com.github.xtorrent.xtorrent.search.SearchResourcesPresenterComponent
import com.github.xtorrent.xtorrent.search.SearchResourcesPresenterModule
import com.github.xtorrent.xtorrent.search.detail.SearchResourceDetailPresenterComponent
import com.github.xtorrent.xtorrent.search.detail.SearchResourceDetailPresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 16/11/29.
 */
@SearchResourcesScope
@Subcomponent(modules = arrayOf(SearchResourcesRepositoryModule::class))
interface SearchResourcesRepositoryComponent {
    fun plus(searchResourcesPresenterModule: SearchResourcesPresenterModule): SearchResourcesPresenterComponent
    fun plus(searchResourceDetailPresenterModule: SearchResourceDetailPresenterModule): SearchResourceDetailPresenterComponent
}