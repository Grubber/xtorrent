package com.github.xtorrent.xtorrent.home.source

import com.github.xtorrent.xtorrent.home.model.HomeResource
import com.github.xtorrent.xtorrent.home.source.local.HomeResourcesLocalDataSource
import com.github.xtorrent.xtorrent.utils.bind
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.observable
import javax.inject.Inject

/**
 * Created by grubber on 16/11/29.
 */
@HomeResourcesScope
class HomeResourcesRepository @Inject constructor(private @LocalHomeResources val localDataSource: HomeResourcesDataSource,
                                                  private @RemoteHomeResources val remoteDataSource: HomeResourcesDataSource) : HomeResourcesDataSource {
    override fun getHomeResources(type: HomeResource.Type): Observable<List<HomeResource>> {
        return observable { subscriber ->
            localDataSource as HomeResourcesLocalDataSource
            val resources = arrayListOf<HomeResource>()
            val localSubscription = localDataSource.getHomeResources(type)
                    .subscribe {
                        if (it.isNotEmpty()) {
                            subscriber.onNext(it)
                            resources += it
                        }
                    }
            val remoteSubscription = remoteDataSource.getHomeResources(type)
                    .bind {
                        next {
                            localDataSource.deleteAllHomeResources(type)
                            if (it != null) {
                                localDataSource.saveHomeResources(it)
                            }
                            subscriber.onNext(it)
                            subscriber.onCompleted()
                        }

                        error {
                            if (resources.isEmpty()) {
                                subscriber.onError(it)
                            }
                        }
                    }
            subscriber.add(object : Subscription {
                override fun isUnsubscribed(): Boolean {
                    return false
                }

                override fun unsubscribe() {
                    localSubscription.unsubscribe()
                    remoteSubscription.unsubscribe()
                }
            })
        }
    }

    override fun getHomeResource(url: String): Observable<HomeResource> {
        val localOb = localDataSource.getHomeResource(url)
        val remoteOb = remoteDataSource.getHomeResource(url)
                .doOnNext {
                    localDataSource.saveHomeResource(it)
                }
        return Observable.merge(localOb, remoteOb)
    }

    override fun saveHomeResource(resource: HomeResource) {
        // Ignored.
    }
}