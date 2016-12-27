package com.github.xtorrent.xtorrent.trend.source

import com.github.xtorrent.xtorrent.trend.model.TrendResource
import rx.Observable

/**
 * Created by zhihao.zeng on 16/12/27.
 */
interface TrendResourcesDataSource {
    fun getResources(type: String): Observable<List<TrendResource>>
}