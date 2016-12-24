package com.github.xtorrent.xtorrent.utils

import org.jsoup.Connection
import org.jsoup.Jsoup
import java.util.*

/**
 * Created by grubber on 2016/12/24.
 */
const val LANG_ZH = "zh"
const val LANG_EN = "en"

fun newJsoupConnection(url: String): Connection {
    val locale = if (LANG_ZH == Locale.getDefault().language) LANG_ZH else LANG_EN
    return Jsoup.connect(url).cookie("locale", locale)
}