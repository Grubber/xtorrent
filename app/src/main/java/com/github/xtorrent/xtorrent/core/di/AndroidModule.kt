package com.github.xtorrent.xtorrent.core.di

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import com.github.xtorrent.xtorrent.core.di.qualifier.ClientVersionCode
import com.github.xtorrent.xtorrent.core.di.qualifier.ClientVersionName
import com.github.xtorrent.xtorrent.core.di.qualifier.ForApplication
import com.github.xtorrent.xtorrent.core.di.scope.ApplicationScope
import com.github.xtorrent.xtorrent.utils.FileUtils
import com.github.xtorrent.xtorrent.utils.checkNotNull
import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/11/29.
 */
@Module
class AndroidModule(context: Context) {
    private val _context: Context = checkNotNull(context, "Application context can't be null.")

    @Provides
    @ApplicationScope
    @ForApplication
    fun provideApplicationContext(): Context {
        return _context
    }

    @ApplicationScope
    @Provides
    @ClientVersionCode
    fun provideVersionCode(@ForApplication context: Context): Int {
        return _getVersionCode(context)
    }

    @ApplicationScope
    @Provides
    @ClientVersionName
    fun provideVersionName(@ForApplication context: Context): String {
        return _getVersionName(context)
    }

    @ApplicationScope
    @Provides
    fun provideAssetManager(@ForApplication context: Context): AssetManager {
        return context.assets
    }

    @ApplicationScope
    @Provides
    fun provideFileUtils(@ForApplication context: Context): FileUtils {
        return FileUtils(context)
    }

    private fun _getVersionCode(context: Context): Int {
        val pi = _getPackageInfo(context)
        if (pi != null) {
            return pi.versionCode
        }
        return 0
    }

    private fun _getVersionName(context: Context): String {
        val pi = _getPackageInfo(context)
        if (pi != null) {
            return pi.versionName
        } else {
            return ""
        }
    }

    private fun _getPackageInfo(context: Context): PackageInfo? {
        val pm = context.packageManager
        var pi: PackageInfo? = null
        try {
            pi = pm.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return pi
    }
}