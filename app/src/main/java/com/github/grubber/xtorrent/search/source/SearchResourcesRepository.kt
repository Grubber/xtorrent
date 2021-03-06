package com.github.grubber.xtorrent.search.source

import com.github.grubber.xtorrent.search.model.Resource
import com.github.grubber.xtorrent.search.model.ResourceItem
import rx.Observable
import javax.inject.Inject

/**
 * Created by grubber on 16/11/29.
 */
@SearchResourcesScope
class SearchResourcesRepository @Inject constructor(private @LocalSearchResources val localDataSource: SearchResourcesDataSource,
                                                    private @RemoteSearchResources val remoteDataSource: SearchResourcesDataSource) : SearchResourcesDataSource {
    override fun getSearchResources(keyword: String, pageNumber: Int): Observable<List<Pair<Resource, List<ResourceItem>>>> {
        return remoteDataSource.getSearchResources(keyword, pageNumber)
    }

    override fun getSearchResource(url: String): Observable<Pair<Resource, List<ResourceItem>>> {
        val localOb = localDataSource.getSearchResource(url) // TODO
        val remoteOb = remoteDataSource.getSearchResource(url)
                .doOnNext {
                    //                    localDataSource.saveSearchResource(it.first)
                }
        return Observable.concat(localOb, remoteOb)
                .filter { it != null }
                .first()
    }

    override fun getResourceTorrentUrl(magnet: String): Observable<String> {
        return remoteDataSource.getResourceTorrentUrl(magnet)
    }

    override fun saveSearchResource(resource: Resource) {
        // TODO
    }


    override fun updateSearchResource(resource: Resource) {
        // TODO
    }
}