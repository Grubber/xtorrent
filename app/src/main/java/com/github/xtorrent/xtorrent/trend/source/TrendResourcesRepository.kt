package com.github.xtorrent.xtorrent.trend.source

import com.github.xtorrent.xtorrent.trend.model.TrendResource
import rx.Observable
import javax.inject.Inject

/**
 * Created by zhihao.zeng on 16/12/27.
 */
@TrendResourcesScope
class TrendResourcesRepository @Inject constructor() : TrendResourcesDataSource {
    override fun getResources(type: Int): Observable<List<TrendResource>> {
        return Observable.just(null)
    }
}