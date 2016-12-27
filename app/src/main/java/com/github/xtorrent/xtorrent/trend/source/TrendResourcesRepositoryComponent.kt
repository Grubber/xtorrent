package com.github.xtorrent.xtorrent.trend.source

import com.github.xtorrent.xtorrent.trend.TrendResourcesPresenterComponent
import com.github.xtorrent.xtorrent.trend.TrendResourcesPresenterModule
import dagger.Subcomponent

/**
 * Created by zhihao.zeng on 16/12/27.
 */
@TrendResourcesScope
@Subcomponent()
interface TrendResourcesRepositoryComponent {
    fun plus(trendResourcesPresenterModule: TrendResourcesPresenterModule): TrendResourcesPresenterComponent
}