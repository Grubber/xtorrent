package com.github.grubber.xtorrent.search.detail

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/11/29.
 */
@Module
class SearchResourceDetailPresenterModule(val view: SearchResourceDetailContract.View) {
    @Provides
    fun provideSearchResourceDetailContractView(): SearchResourceDetailContract.View {
        return view
    }
}