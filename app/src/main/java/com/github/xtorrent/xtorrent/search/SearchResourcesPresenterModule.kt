package com.github.xtorrent.xtorrent.search

import dagger.Module
import dagger.Provides

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@Module
class SearchResourcesPresenterModule(val view: SearchResourcesContract.View) {
    @Provides
    fun provideSearchResourcesContractView(): SearchResourcesContract.View {
        return view
    }
}