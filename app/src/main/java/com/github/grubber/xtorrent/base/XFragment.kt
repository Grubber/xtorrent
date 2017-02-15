package com.github.grubber.xtorrent.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.github.grubber.xtorrent.XApplication
import com.trello.rxlifecycle.components.support.RxFragment
import rx.Observable

/**
 * Created by grubber on 16/11/29.
 */
abstract class XFragment : RxFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getTitle()?.let {
            (activity as XActivity).setTitle(it)
        }
    }

    protected fun picasso() = XApplication.from(context).picasso

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