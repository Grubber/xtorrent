package com.github.grubber.xtorrent.base

/**
 * Created by grubber on 16/11/29.
 */
interface BaseView<in T> {
    fun setPresenter(presenter: T)
}