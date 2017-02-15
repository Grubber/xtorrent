package com.github.grubber.xtorrent.movie.detail

import com.github.grubber.xtorrent.base.BasePresenter
import com.github.grubber.xtorrent.base.BaseView
import com.github.grubber.xtorrent.movie.model.Movie

/**
 * Created by grubber on 16/12/26.
 */
interface MovieDetailContract {
    interface View : BaseView<Presenter> {
        fun setErrorView()
        fun setContentView(movie: Movie)
    }

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}