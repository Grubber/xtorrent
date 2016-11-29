package com.github.xtorrent.xtorrent.home

import dagger.Module
import dagger.Provides

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@Module
class HomeResourcesPresenterModule(val view: HomeResourcesContract.View) {
    @Provides
    fun provideHomeResourcesContractView(): HomeResourcesContract.View {
        return view
    }
}