package com.github.xtorrent.xtorrent.movie

import com.github.xtorrent.xtorrent.base.BasePresenter
import com.github.xtorrent.xtorrent.base.BaseView
import com.github.xtorrent.xtorrent.movie.model.Movie
import com.squareup.picasso.Picasso

/**
 * Created by grubber on 2016/12/25.
 */
interface MovieContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(list: List<Movie>? = null, loadedError: Boolean = false)
        fun setPicasso(picasso: Picasso)
    }

    interface Presenter : BasePresenter {
        fun setPageNumber(pageNumber: Int)
    }
}