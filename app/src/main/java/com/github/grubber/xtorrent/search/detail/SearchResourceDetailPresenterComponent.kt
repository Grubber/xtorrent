package com.github.grubber.xtorrent.search.detail

import com.github.grubber.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by grubber on 16/11/29.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(SearchResourceDetailPresenterModule::class))
interface SearchResourceDetailPresenterComponent {
    fun inject(searchResourceDetailActivity: SearchResourceDetailActivity)
}