package com.github.xtorrent.xtorrent.trend

import com.github.xtorrent.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by zhihao.zeng on 16/12/27.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(TrendResourcesPresenterModule::class))
interface TrendResourcesPresenterComponent {
    fun inject(trendFragment: TrendFragment)
}