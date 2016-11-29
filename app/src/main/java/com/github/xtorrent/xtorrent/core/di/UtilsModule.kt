package com.github.xtorrent.xtorrent.core.di

import android.content.Context
import com.github.xtorrent.xtorrent.core.di.qualifier.ForApplication
import com.github.xtorrent.xtorrent.core.di.scope.ApplicationScope
import com.github.xtorrent.xtorrent.utils.ToastHelper
import dagger.Module
import dagger.Provides

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@Module
class UtilsModule {
    @Provides
    @ApplicationScope
    fun provideToastHelper(@ForApplication context: Context) = ToastHelper(context)
}