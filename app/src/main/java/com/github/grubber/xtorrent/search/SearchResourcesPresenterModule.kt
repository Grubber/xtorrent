package com.github.grubber.xtorrent.search

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/11/29.
 */
@Module
class SearchResourcesPresenterModule(val view: SearchResourcesContract.View) {
    @Provides
    fun provideSearchResourcesContractView(): SearchResourcesContract.View {
        return view
    }
}