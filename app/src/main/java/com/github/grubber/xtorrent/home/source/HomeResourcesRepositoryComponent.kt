package com.github.grubber.xtorrent.home.source

import com.github.grubber.xtorrent.home.HomeResourcesPresenterComponent
import com.github.grubber.xtorrent.home.HomeResourcesPresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 16/11/29.
 */
@HomeResourcesScope
@Subcomponent(modules = arrayOf(HomeResourcesRepositoryModule::class))
interface HomeResourcesRepositoryComponent {
    fun plus(newlyPresenterModule: HomeResourcesPresenterModule): HomeResourcesPresenterComponent
}