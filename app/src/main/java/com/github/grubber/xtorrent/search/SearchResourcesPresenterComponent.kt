package com.github.grubber.xtorrent.search

import com.github.grubber.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by grubber on 16/11/29.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(SearchResourcesPresenterModule::class))
interface SearchResourcesPresenterComponent {
    fun inject(searchResourcesActivity: SearchResourcesActivity)
}