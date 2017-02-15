package com.github.grubber.xtorrent

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.github.grubber.xtorrent.core.di.*
import com.github.grubber.xtorrent.db.DatabaseManager
import com.github.grubber.xtorrent.home.source.HomeResourcesRepositoryComponent
import com.github.grubber.xtorrent.home.source.HomeResourcesRepositoryModule
import com.github.grubber.xtorrent.movie.source.MovieRepositoryComponent
import com.github.grubber.xtorrent.search.source.SearchResourcesRepositoryComponent
import com.github.grubber.xtorrent.search.source.SearchResourcesRepositoryModule
import com.github.grubber.xtorrent.trend.source.TrendResourcesRepositoryComponent
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.Picasso
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by grubber on 16/11/29.
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
                .networkModule(NetworkModule())
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