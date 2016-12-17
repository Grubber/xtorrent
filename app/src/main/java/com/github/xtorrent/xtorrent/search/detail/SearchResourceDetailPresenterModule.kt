package com.github.xtorrent.xtorrent.search.detail

import dagger.Module
import dagger.Provides

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@Module
class SearchResourceDetailPresenterModule(val view: SearchResourceDetailContract.View) {
    @Provides
    fun provideSearchResourceDetailContractView(): SearchResourceDetailContract.View {
        return view
    }
}