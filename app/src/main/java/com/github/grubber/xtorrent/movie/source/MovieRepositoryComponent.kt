package com.github.grubber.xtorrent.movie.source

import com.github.grubber.xtorrent.movie.MoviePresenterComponent
import com.github.grubber.xtorrent.movie.MoviePresenterModule
import com.github.grubber.xtorrent.movie.detail.MovieDetailPresenterComponent
import com.github.grubber.xtorrent.movie.detail.MovieDetailPresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 2016/12/25.
 */
@MovieScope
@Subcomponent
interface MovieRepositoryComponent {
    fun plus(moviePresenterModule: MoviePresenterModule): MoviePresenterComponent
    fun plus(movieDetailPresenterModule: MovieDetailPresenterModule): MovieDetailPresenterComponent
}