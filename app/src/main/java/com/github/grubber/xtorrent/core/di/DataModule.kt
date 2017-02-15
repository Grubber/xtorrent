package com.github.grubber.xtorrent.core.di

import android.content.Context
import android.content.SharedPreferences
import com.github.grubber.xtorrent.core.di.qualifier.ForApplication
import com.github.grubber.xtorrent.core.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/11/29.
 */
@Module
class DataModule {
    @Provides
    @ApplicationScope
    fun provideSharedPreferences(@ForApplication context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}