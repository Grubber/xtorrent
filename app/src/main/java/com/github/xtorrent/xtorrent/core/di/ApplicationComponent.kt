package com.github.xtorrent.xtorrent.core.di

import com.github.xtorrent.xtorrent.XApplication
import com.github.xtorrent.xtorrent.core.di.scope.ApplicationScope
import com.github.xtorrent.xtorrent.home.source.HomeResourcesRepositoryComponent
import com.github.xtorrent.xtorrent.home.source.HomeResourcesRepositoryModule
import com.github.xtorrent.xtorrent.movie.source.MovieRepositoryComponent
import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepositoryComponent
import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepositoryModule
import dagger.Component

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@ApplicationScope
@Component(modules = arrayOf(AndroidModule::class, DataModule::class))
interface ApplicationComponent {
    fun plus(searchResourcesRepositoryModule: SearchResourcesRepositoryModule): SearchResourcesRepositoryComponent
    fun plus(homeResourcesRepositoryModule: HomeResourcesRepositoryModule): HomeResourcesRepositoryComponent
    fun plus(): MovieRepositoryComponent

    fun inject(xApplication: XApplication)
}