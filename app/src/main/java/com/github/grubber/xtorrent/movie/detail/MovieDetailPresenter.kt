package com.github.grubber.xtorrent.movie.detail

import com.github.grubber.xtorrent.movie.source.MovieRepository
import com.github.grubber.xtorrent.utils.applySchedulers
import com.github.grubber.xtorrent.utils.bind
import com.github.grubber.xtorrent.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 16/12/26.
 */
class MovieDetailPresenter @Inject constructor(private val repository: MovieRepository,
                                               private val view: MovieDetailContract.View) : MovieDetailContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private lateinit var _url: String

    override fun setUrl(url: String) {
        _url = url
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getMovie(_url)
                .applySchedulers()
                .bind {
                    next {
                        if (it == null) {
                            view.setErrorView()
                        } else {
                            view.setContentView(it)
                        }
                    }

                    error {
                        view.setErrorView()
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}