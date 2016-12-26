package com.github.xtorrent.xtorrent.movie.detail

import com.github.xtorrent.xtorrent.core.di.scope.FragmentScope
import dagger.Subcomponent

/**
 * Created by zhihao.zeng on 16/12/26.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(MovieDetailPresenterModule::class))
interface MovieDetailPresenterComponent {
    fun inject(movieDetailActivity: MovieDetailActivity)
}