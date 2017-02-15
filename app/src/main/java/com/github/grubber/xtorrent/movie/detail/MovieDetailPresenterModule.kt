package com.github.grubber.xtorrent.movie.detail

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 16/12/26.
 */
@Module
class MovieDetailPresenterModule(val view: MovieDetailContract.View) {
    @Provides
    fun provideMovieDetailContractView(): MovieDetailContract.View {
        return view
    }
}