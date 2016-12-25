package com.github.xtorrent.xtorrent.movie

import com.github.xtorrent.xtorrent.core.di.scope.FragmentScope
import com.github.xtorrent.xtorrent.main.MainFragment
import dagger.Subcomponent

/**
 * Created by grubber on 2016/12/25.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(MoviePresenterModule::class))
interface MoviePresenterComponent {
    fun inject(mainFragment: MainFragment)
}