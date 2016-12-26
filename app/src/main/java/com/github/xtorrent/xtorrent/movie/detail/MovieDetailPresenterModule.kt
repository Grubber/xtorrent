package com.github.xtorrent.xtorrent.movie.detail

import dagger.Module
import dagger.Provides

/**
 * Created by zhihao.zeng on 16/12/26.
 */
@Module
class MovieDetailPresenterModule(val view: MovieDetailContract.View) {
    @Provides
    fun provideMovieDetailContractView(): MovieDetailContract.View {
        return view
    }
}