package com.github.grubber.xtorrent.utils

/**
 * Created by grubber on 16/11/29.
 */
fun <T> checkNotNull(t: T?): T {
    return t ?: throw NullPointerException()
}

fun <T> checkNotNull(t: T?, value: Any): T {
    return t ?: throw NullPointerException(value.toString())
}