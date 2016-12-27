package com.github.xtorrent.xtorrent.trend.model

/**
 * Created by zhihao.zeng on 16/12/27.
 */
data class TrendResource(var title: String,
                         var url: String,
                         var files: Int,
                         var downloads: Int,
                         var created: String)