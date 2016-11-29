package com.github.xtorrent.xtorrent.home.source

import com.github.xtorrent.xtorrent.db.DatabaseManager
import com.github.xtorrent.xtorrent.home.source.local.HomeResourcesLocalDataSource
import com.github.xtorrent.xtorrent.home.source.remote.HomeResourcesRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by zhihao.zeng on 16/11/29.
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