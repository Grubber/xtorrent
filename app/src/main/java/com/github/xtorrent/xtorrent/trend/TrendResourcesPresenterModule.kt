package com.github.xtorrent.xtorrent.trend

import dagger.Module
import dagger.Provides

/**
 * Created by zhihao.zeng on 16/12/27.
 */
@Module
class TrendResourcesPresenterModule(val view: TrendResourcesContract.View) {
    @Provides
    fun provideTrendResourcesContractView(): TrendResourcesContract.View {
        return view
    }
}