package com.github.xtorrent.xtorrent

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.github.xtorrent.xtorrent.core.di.AndroidModule
import com.github.xtorrent.xtorrent.core.di.ApplicationComponent
import com.github.xtorrent.xtorrent.core.di.DaggerApplicationComponent
import com.github.xtorrent.xtorrent.core.di.DataModule
import com.github.xtorrent.xtorrent.db.DatabaseManager
import com.github.xtorrent.xtorrent.home.source.HomeResourcesRepositoryComponent
import com.github.xtorrent.xtorrent.home.source.HomeResourcesRepositoryModule
import com.github.xtorrent.xtorrent.movie.source.MovieRepositoryComponent
import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepositoryComponent
import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepositoryModule
import com.github.xtorrent.xtorrent.trend.source.TrendResourcesRepositoryComponent
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.Picasso
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class XApplication : MultiDexApplication() {
    companion object {
        fun from(context: Context): XApplication {
            return context.applicationContext as XApplication
        }
    }

    var applicationComponent by Delegates.notNull<ApplicationComponent>()
    var homeResourcesRepositoryComponent by Delegates.notNull<HomeResourcesRepositoryComponent>()
    var searchResourcesRepositoryComponent by Delegates.notNull<SearchResourcesRepositoryComponent>()
    var movieRepositoryComponent by Delegates.notNull<MovieRepositoryComponent>()
    var trendResourcesRepositoryComponent by Delegates.notNull<TrendResourcesRepositoryComponent>()

    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var picasso: Picasso

    override fun onCreate() {
        super.onCreate()

        _setupAnalytics()
        _setupObjectGraph()

        databaseManager.open()
    }

    private fun _setupObjectGraph() {
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .dataModule(DataModule())
                .build()
        homeResourcesRepositoryComponent = applicationComponent.plus(HomeResourcesRepositoryModule())
        searchResourcesRepositoryComponent = applicationComponent.plus(SearchResourcesRepositoryModule())
        movieRepositoryComponent = applicationComponent.plusMovieRepositoryComponent()
        trendResourcesRepositoryComponent = applicationComponent.plusTrendResourcesRepositoryComponent()

        applicationComponent.inject(this)
    }

    private fun _setupAnalytics() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}