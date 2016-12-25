package com.github.xtorrent.xtorrent.movie

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2016/12/25.
 */
@Module
class MoviePresenterModule(val view: MovieContract.View) {
    @Provides
    fun provideMovieContractView(): MovieContract.View {
        return view
    }
}