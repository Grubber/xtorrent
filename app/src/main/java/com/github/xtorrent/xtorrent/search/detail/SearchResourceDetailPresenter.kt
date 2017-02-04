package com.github.xtorrent.xtorrent.search.detail

import com.github.xtorrent.xtorrent.search.source.SearchResourcesRepository
import com.github.xtorrent.xtorrent.utils.FileUtils
import com.github.xtorrent.xtorrent.utils.applySchedulers
import com.github.xtorrent.xtorrent.utils.bind
import com.github.xtorrent.xtorrent.utils.plusAssign
import okhttp3.OkHttpClient
import okhttp3.Request
import rx.Observable
import rx.lang.kotlin.observable
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

/**
 * @author Grubber
 */
class SearchResourceDetailPresenter @Inject constructor(private val repository: SearchResourcesRepository,
                                                        private val view: SearchResourceDetailContract.View,
                                                        private val okHttpClient: OkHttpClient,
                                                        private val fileUtils: FileUtils) : SearchResourceDetailContract.Presenter {
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

    override fun downloadTorrent(magnet: String, name: String) {
        _binder.clear()

        _binder += repository.getResourceTorrentUrl(magnet)
                .flatMap {
                    if (it != null) {
                        _downloadBt(it, name, magnet)
                    } else {
                        Observable.just(null)
                    }
                }
                .applySchedulers()
                .bind {
                    next {
                        it?.let {
                            Timber.d("### torrent download path is $it")
                        }
                    }

                    error {

                    }
                }
    }

    private fun _downloadBt(url: String, name: String, magnet: String): Observable<String> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val request = Request.Builder()
                            .url(url)
                            .build()
                    val response = okHttpClient.newCall(request)
                            .execute()
                    if (response.isSuccessful) {
                        val bytes = response.body().bytes()
                        val file = File(fileUtils.getCacheDirectory(), "$name-$magnet.torrent")
                        val fos = FileOutputStream(file)
                        fos.use {
                            val bos = BufferedOutputStream(it)
                            bos.use {
                                bos.write(bytes)
                                bos.flush()
                            }
                        }
                        it.onNext(file.absolutePath)
                        it.onCompleted()
                    } else {
                        it.onError(IOException())
                    }
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}