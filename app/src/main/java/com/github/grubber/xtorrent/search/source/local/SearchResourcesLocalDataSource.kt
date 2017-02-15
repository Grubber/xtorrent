package com.github.grubber.xtorrent.search.source.local

import com.github.grubber.xtorrent.search.model.Resource
import com.github.grubber.xtorrent.search.model.ResourceItem
import com.github.grubber.xtorrent.search.source.SearchResourcesDataSource
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * Created by grubber on 16/11/29.
 */
class SearchResourcesLocalDataSource : SearchResourcesDataSource {
    override fun getSearchResources(keyword: String, pageNumber: Int): Observable<List<Pair<Resource, List<ResourceItem>>>> {
        // Ignored
        return emptyObservable()
    }

    override fun getSearchResource(url: String): Observable<Pair<Resource, List<ResourceItem>>> {
        // TODO
        return Observable.just(null)
    }

    override fun saveSearchResource(resource: Resource) {
        // TODO
    }

    override fun updateSearchResource(resource: Resource) {
        // TODO
    }

    override fun getResourceTorrentUrl(magnet: String): Observable<String> {
        // Ignored
        return emptyObservable()
    }
}