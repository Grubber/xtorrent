package com.github.grubber.xtorrent.trend

import com.github.grubber.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by grubber on 16/12/27.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(TrendResourcesPresenterModule::class))
interface TrendResourcesPresenterComponent {
    fun inject(trendFragment: TrendFragment)
}