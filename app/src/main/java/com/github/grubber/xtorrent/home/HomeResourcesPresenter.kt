package com.github.grubber.xtorrent.home

import com.github.grubber.xtorrent.home.model.HomeResource
import com.github.grubber.xtorrent.home.source.HomeResourcesRepository
import com.github.grubber.xtorrent.utils.applySchedulers
import com.github.grubber.xtorrent.utils.bind
import com.github.grubber.xtorrent.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by grubber on 16/11/29.
 */
class HomeResourcesPresenter @Inject constructor(private val repository: HomeResourcesRepository,
                                                 private val view: HomeResourcesContract.View) : HomeResourcesContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private var _type by Delegates.notNull<HomeResource.Type>()

    override fun setType(type: HomeResource.Type) {
        _type = type
    }

    override fun subscribe() {
        _binder.clear()
        _binder += repository.getHomeResources(_type)
                .applySchedulers()
                .bind {
                    next {
                        if (it == null || it.isEmpty()) {
                            view.setErrorView()
                        } else {
                            view.setContentView(it)
                        }
                        view.stopRefreshing()
                    }

                    error {
                        Timber.d("### error is $it")
                        view.setErrorView()
                        view.stopRefreshing()
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}