package com.github.grubber.xtorrent.home

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/11/29.
 */
@Module
class HomeResourcesPresenterModule(val view: HomeResourcesContract.View) {
    @Provides
    fun provideHomeResourcesContractView(): HomeResourcesContract.View {
        return view
    }
}