package com.github.grubber.xtorrent.trend

import com.github.grubber.xtorrent.trend.source.TrendResourcesRepository
import com.github.grubber.xtorrent.utils.applySchedulers
import com.github.grubber.xtorrent.utils.bind
import com.github.grubber.xtorrent.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 16/12/27.
 */
class TrendResourcesPresenter @Inject constructor(private val repository: TrendResourcesRepository,
                                                  private val view: TrendResourcesContract.View) : TrendResourcesContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    private lateinit var _type: String

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    override fun setType(type: String) {
        _type = type
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getResources(_type)
                .applySchedulers()
                .bind {
                    next {
                        if (it == null) {
                            view.setContentView(arrayListOf())
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