package com.github.xtorrent.xtorrent.search.detail

import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepository
import com.github.xtorrent.xtorrent.utils.applySchedulers
import com.github.xtorrent.xtorrent.utils.bind
import com.github.xtorrent.xtorrent.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Grubber
 */
class SearchResourceDetailPresenter @Inject constructor(private val repository: SearchResourcesRepository,
                                                        private val view: SearchResourceDetailContract.View) : SearchResourceDetailContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    override fun setUrl(url: String) {
        _url = url
    }

    private lateinit var _url: String

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getSearchResource(_url)
                .applySchedulers()
                .bind {
                    next {
                        it?.let {
                            view.setContentView(it)
                        }
                    }

                    error {
                        view.setErrorView()
                    }
                }
    }

    override fun downloadTorrent(magnet: String) {
        _binder.clear()

        _binder += repository.getResourceTorrentUrl(magnet)
                .applySchedulers()
                .bind {
                    next {
                        it?.let {
                            Timber.d("### torrent url is $it")
                        }
                    }

                    error {

                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}