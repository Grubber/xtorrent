package com.github.xtorrent.xtorrent.search.source

import com.github.xtorrent.xtorrent.search.source.local.SearchResourcesLocalDataSource
import com.github.xtorrent.xtorrent.search.source.remote.SearchResourcesRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/11/29.
 */
@Module
class SearchResourcesRepositoryModule {
    @SearchResourcesScope
    @Provides
    @LocalSearchResources
    fun provideSearchResultLocalDataSource(): SearchResourcesDataSource {
        return SearchResourcesLocalDataSource()
    }

    @SearchResourcesScope
    @Provides
    @RemoteSearchResources
    fun provideSearchResultRemoteDataSource(): SearchResourcesDataSource {
        return SearchResourcesRemoteDataSource()
    }
}