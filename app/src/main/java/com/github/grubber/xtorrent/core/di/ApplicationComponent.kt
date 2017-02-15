package com.github.grubber.xtorrent.core.di

import com.github.grubber.xtorrent.XApplication
import com.github.grubber.xtorrent.core.di.scope.ApplicationScope
import com.github.grubber.xtorrent.home.source.HomeResourcesRepositoryComponent
import com.github.grubber.xtorrent.home.source.HomeResourcesRepositoryModule
import com.github.grubber.xtorrent.movie.source.MovieRepositoryComponent
import com.github.grubber.xtorrent.search.source.SearchResourcesRepositoryComponent
import com.github.grubber.xtorrent.search.source.SearchResourcesRepositoryModule
import com.github.grubber.xtorrent.trend.source.TrendResourcesRepositoryComponent
import dagger.Component

/**
 * Created by grubber on 16/11/29.
 */
@ApplicationScope
@Component(modules = arrayOf(AndroidModule::class, DataModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun plus(searchResourcesRepositoryModule: SearchResourcesRepositoryModule): SearchResourcesRepositoryComponent
    fun plus(homeResourcesRepositoryModule: HomeResourcesRepositoryModule): HomeResourcesRepositoryComponent
    fun plusMovieRepositoryComponent(): MovieRepositoryComponent
    fun plusTrendResourcesRepositoryComponent(): TrendResourcesRepositoryComponent

    fun inject(xApplication: XApplication)
}