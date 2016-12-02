package com.github.xtorrent.xtorrent.search

import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepository
import com.github.xtorrent.xtorrent.utils.applySchedulers
import com.github.xtorrent.xtorrent.utils.bind
import com.github.xtorrent.xtorrent.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class SearchResourcesPresenter @Inject constructor(private val repository: SearchResourcesRepository,
                                                   private val view: SearchResourcesContract.View) : SearchResourcesContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    private var _keyword by Delegates.notNull<String>()

    override fun setKeyword(keyword: String) {
        _keyword = keyword
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getSearchResources(_keyword)
                .applySchedulers()
                .bind {
                    next {
                        if (it == null || it.isEmpty()) {
                            view.setEmptyView()
                        } else {
                            view.setContentView(it)
                        }
                    }

                    error {
                        Timber.d("### error is ${it?.message}")
                        view.setErrorView()
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}