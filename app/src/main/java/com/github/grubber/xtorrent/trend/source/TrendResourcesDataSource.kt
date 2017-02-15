package com.github.grubber.xtorrent.trend.source

import com.github.grubber.xtorrent.trend.model.TrendResource
import rx.Observable

/**
 * Created by grubber on 16/12/27.
 */
interface TrendResourcesDataSource {
    fun getResources(type: String): Observable<List<TrendResource>>
}