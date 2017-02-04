package com.github.xtorrent.xtorrent.search.source

import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem
import rx.Observable

/**
 * Created by grubber on 16/11/29.
 */
interface SearchResourcesDataSource {
    fun getSearchResources(keyword: String, pageNumber: Int): Observable<List<Pair<Resource, List<ResourceItem>>>>
    fun getSearchResource(url: String): Observable<Pair<Resource, List<ResourceItem>>>

    fun saveSearchResource(resource: Resource)
    fun updateSearchResource(resource: Resource)

    fun getResourceTorrentUrl(magnet: String): Observable<String>
}