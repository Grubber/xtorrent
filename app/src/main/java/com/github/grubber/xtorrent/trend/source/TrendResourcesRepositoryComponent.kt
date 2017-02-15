package com.github.grubber.xtorrent.trend.source

import com.github.grubber.xtorrent.trend.TrendResourcesPresenterComponent
import com.github.grubber.xtorrent.trend.TrendResourcesPresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 16/12/27.
 */
@TrendResourcesScope
@Subcomponent()
interface TrendResourcesRepositoryComponent {
    fun plus(trendResourcesPresenterModule: TrendResourcesPresenterModule): TrendResourcesPresenterComponent
}