package com.github.xtorrent.xtorrent.search.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem

/**
 * @author Grubber
 */
class SearchResourceDetailFragment : ContentFragment(), SearchResourceDetailContract.View {
    companion object {
        private val _EXTRA_TITLE = "title"
        private val _EXTRA_URL = "url"

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
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setContentView(data: Pair<Resource, List<ResourceItem>>) {
        // TODO
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