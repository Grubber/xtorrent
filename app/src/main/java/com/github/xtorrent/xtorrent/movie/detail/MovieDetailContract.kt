package com.github.xtorrent.xtorrent.movie.detail

import com.github.xtorrent.xtorrent.base.BasePresenter
import com.github.xtorrent.xtorrent.base.BaseView
import com.github.xtorrent.xtorrent.movie.model.Movie
import com.squareup.picasso.Picasso

/**
 * Created by zhihao.zeng on 16/12/26.
 */
interface MovieDetailContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(movie: Movie)
        fun setPicasso(picasso: Picasso)
    }

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}