package com.github.grubber.xtorrent.utils

import org.jsoup.Connection
import org.jsoup.Jsoup

/**
 * Created by grubber on 2016/12/24.
 */
const val LANG_ZH = "zh"
const val LANG_ZH_CN = "zh-cn"
const val LANG_ZH_TW = "zh-tw"
const val LANG_EN = "en"

fun newJsoupConnection(url: String): Connection {
//    val locale = when (Locale.getDefault().language) {
//        LANG_ZH -> LANG_ZH_CN
//        else -> LANG_EN
//    }
//    return Jsoup.connect(url).cookie("locale", locale)
    return Jsoup.connect(url)
}