package com.github.grubber.xtorrent.movie.detail

import com.github.grubber.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by grubber on 16/12/26.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(MovieDetailPresenterModule::class))
interface MovieDetailPresenterComponent {
    fun inject(movieDetailActivity: MovieDetailActivity)
}