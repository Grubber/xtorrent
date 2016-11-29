package com.github.xtorrent.xtorrent.home.source

import com.github.xtorrent.xtorrent.home.HomeResourcesPresenterComponent
import com.github.xtorrent.xtorrent.home.HomeResourcesPresenterModule
import dagger.Subcomponent

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@HomeResourcesScope
@Subcomponent(modules = arrayOf(HomeResourcesRepositoryModule::class))
interface HomeResourcesRepositoryComponent {
    fun plus(newlyPresenterModule: HomeResourcesPresenterModule): HomeResourcesPresenterComponent
}