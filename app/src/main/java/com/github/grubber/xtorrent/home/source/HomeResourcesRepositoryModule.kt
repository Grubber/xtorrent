package com.github.grubber.xtorrent.home.source

import com.github.grubber.xtorrent.db.DatabaseManager
import com.github.grubber.xtorrent.home.source.local.HomeResourcesLocalDataSource
import com.github.grubber.xtorrent.home.source.remote.HomeResourcesRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/11/29.
 */
@Module
class HomeResourcesRepositoryModule {
    @HomeResourcesScope
    @Provides
    @LocalHomeResources
    fun provideHomeResourcesLocalDataSource(databaseManager: DatabaseManager): HomeResourcesDataSource {
        return HomeResourcesLocalDataSource(databaseManager)
    }

    @HomeResourcesScope
    @Provides
    @RemoteHomeResources
    fun provideHomeResourcesRemoteDataSource(): HomeResourcesDataSource {
        return HomeResourcesRemoteDataSource()
    }
}