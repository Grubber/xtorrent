package com.github.xtorrent.xtorrent.utils

import android.content.Context
import com.github.xtorrent.xtorrent.BuildConfig
import java.io.File

/**
 * Created by grubber on 2017/1/15.
 */
class FileUtils(val context: Context) {
    fun getCacheDirectory(): File {
        return if (BuildConfig.DEBUG) context.externalCacheDir else context.cacheDir
    }
}