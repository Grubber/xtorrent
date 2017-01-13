package com.github.xtorrent.xtorrent.search.detail

import android.content.*
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem
import com.github.xtorrent.xtorrent.search.view.ResourceItemView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.jakewharton.rxbinding.view.clicks
import kotlin.properties.Delegates

/**
 * @author Grubber
 */
class SearchResourceDetailFragment : ContentFragment(), SearchResourceDetailContract.View {
    companion object {
        private const val _EXTRA_TITLE = "title"
        private const val _EXTRA_URL = "url"

        fun newInstance(title: String, url: String): SearchResourceDetailFragment {
            val fragment = SearchResourceDetailFragment()
            val args = Bundle()
            args.putString(_EXTRA_TITLE, title)
            args.putString(_EXTRA_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var _presenter: SearchResourceDetailContract.Presenter

    private val _title by lazy {
        arguments.getString(_EXTRA_TITLE)
    }
    private val _url by lazy {
        arguments.getString(_EXTRA_URL)
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_search_resource_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setUrl(_url)
        _presenter.subscribe()

        _magnetView.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        _magnetView.paint.isAntiAlias = true

        bindSubscribe(_copyButton.clicks()) {
            _clipboardManager.primaryClip = ClipData.newPlainText("", _resource.magnet())
            showToast(R.string.toast_copied)
            val params = Bundle()
            params.putString("action_type", "copy")
            firebaseAnalytics.logEvent("event_resource_click", params)
        }
        bindSubscribe(_downloadButton.clicks()) {
            _linkToDownload()
            val params = Bundle()
            params.putString("action_type", "download")
            firebaseAnalytics.logEvent("event_resource_click", params)
        }
        bindSubscribe(_magnetView.clicks()) {
            _linkToDownload()
            val params = Bundle()
            params.putString("action_type", "magnet")
            firebaseAnalytics.logEvent("event_resource_click", params)
        }

        val adRequest = AdRequest.Builder().build()
        _adView.loadAd(adRequest)
    }

    private val _adView by bindView<AdView>(R.id.adView)

    private fun _linkToDownload() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(_resource.magnet())
            startActivity(intent)

            val params = Bundle()
            params.putString("app_type", "maybe115Netdisk")
            firebaseAnalytics.logEvent("event_resource_wakeup", params)
        } catch (e: ActivityNotFoundException) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                val packageName = "com.baidu.netdisk"
                val className = "$packageName.ui.MainActivity"
                intent.component = ComponentName(packageName, className)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                showToast("由于百度网盘下载页面不开放原因，请自己手动到下载页面下载资源")

                val params = Bundle()
                params.putString("app_type", "baiduNetdisk")
                firebaseAnalytics.logEvent("event_resource_wakeup", params)
            } catch (e: ActivityNotFoundException) {
                showToast(R.string.toast_no_apps_found)
            }
        }
    }

    private val _clipboardManager by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    private var _resource by Delegates.notNull<Resource>()

    override fun setErrorView() {
        displayErrorView()
    }

    private val _titleView by bindView<TextView>(R.id.titleView)
    private val _magnetView by bindView<TextView>(R.id.magnetView)
    private val _copyButton by bindView<Button>(R.id.copyButton)
    private val _downloadButton by bindView<Button>(R.id.downloadButton)
    private val _typeView by bindView<TextView>(R.id.typeView)
    private val _sizeView by bindView<TextView>(R.id.sizeView)
    private val _filesView by bindView<TextView>(R.id.filesView)
    private val _downloadsView by bindView<TextView>(R.id.downloadsView)
    private val _updatedView by bindView<TextView>(R.id.updatedView)
    private val _createdView by bindView<TextView>(R.id.createdView)
    private val _itemContainer by bindView<LinearLayout>(R.id.itemContainer)

    override fun setContentView(data: Pair<Resource, List<ResourceItem>>) {
        _resource = data.first
        with(_resource) {
            _titleView.text = title()
            _magnetView.text = magnet()
            _typeView.text = type()
            _sizeView.text = size()
            _filesView.text = files()
            _downloadsView.text = downloads()
            _updatedView.text = updated()
            _createdView.text = created()
        }

        _itemContainer.removeAllViews()
        data.second.forEach {
            val itemView = ResourceItemView(context)
            itemView.setText(it.title())
            _itemContainer.addView(itemView)
        }

        displayContentView()
    }

    override fun setPresenter(presenter: SearchResourceDetailContract.Presenter) {
        _presenter = presenter
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return _title
    }
}