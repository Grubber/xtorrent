package com.github.xtorrent.xtorrent.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.trello.rxlifecycle.components.support.RxFragment
import rx.Observable

/**
 * Created by zhihao.zeng on 16/11/29.
 */
abstract class XFragment : RxFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getTitle()?.let {
            (activity as XActivity).setTitle(it)
        }
    }

    protected val firebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    fun getToolbar(): Toolbar {
        return (activity as XActivity).toolbar
    }

    private fun <T> _bindToLifecycle(observable: Observable<T>): Observable<T> {
        return observable.compose(this.bindToLifecycle<T>())
    }

    protected fun <T> bindSubscribe(observable: Observable<T>,
                                    onNext: (T) -> Unit) {
        _bindToLifecycle(observable).subscribe(onNext)
    }

    abstract fun getTitle(): String?

    fun showToast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    fun showToast(resId: Int) = Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
}