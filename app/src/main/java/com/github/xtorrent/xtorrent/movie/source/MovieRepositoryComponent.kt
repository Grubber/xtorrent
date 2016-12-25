package com.github.xtorrent.xtorrent.movie.source

import com.github.xtorrent.xtorrent.movie.MoviePresenterComponent
import com.github.xtorrent.xtorrent.movie.MoviePresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 2016/12/25.
 */
@MovieScope
@Subcomponent
interface MovieRepositoryComponent {
    fun plus(moviePresenterModule: MoviePresenterModule): MoviePresenterComponent
}