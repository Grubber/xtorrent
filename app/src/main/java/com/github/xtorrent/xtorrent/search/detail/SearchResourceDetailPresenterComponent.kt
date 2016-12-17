package com.github.xtorrent.xtorrent.search.detail

import com.github.xtorrent.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(SearchResourceDetailPresenterModule::class))
interface SearchResourceDetailPresenterComponent {
    fun inject(searchResourceDetailActivity: SearchResourceDetailActivity)
}