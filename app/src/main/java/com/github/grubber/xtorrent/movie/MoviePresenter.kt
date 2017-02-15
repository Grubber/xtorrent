package com.github.grubber.xtorrent.movie

import com.github.grubber.xtorrent.movie.model.Movie
import com.github.grubber.xtorrent.movie.source.MovieRepository
import com.github.grubber.xtorrent.utils.applySchedulers
import com.github.grubber.xtorrent.utils.bind
import com.github.grubber.xtorrent.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2016/12/25.
 */
class MoviePresenter @Inject constructor(private val repository: MovieRepository,
                                         private val view: MovieContract.View) : MovieContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }
    private var _pageNumber = 1
    private val _data by lazy {
        arrayListOf<Movie>()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    override fun setPageNumber(pageNumber: Int) {
        _pageNumber = pageNumber
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getMovies(_pageNumber)
                .applySchedulers()
                .bind {
                    next {
                        if (it != null) {
                            _data.addAll(it)
                            view.setContentView(it)
                        }
                    }

                    error {
                        if (_data.isEmpty()) {
                            view.setErrorView()
                        } else {
                            view.setContentView(null, true)
                        }
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}