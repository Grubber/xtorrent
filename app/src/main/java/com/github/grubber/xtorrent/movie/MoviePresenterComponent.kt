package com.github.grubber.xtorrent.movie

import com.github.grubber.xtorrent.core.di.scope.FragmentScope
import com.github.grubber.xtorrent.main.MainFragment
import dagger.Subcomponent

/**
 * Created by grubber on 2016/12/25.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(MoviePresenterModule::class))
interface MoviePresenterComponent {
    fun inject(mainFragment: MainFragment)
}