package com.github.xtorrent.xtorrent.home

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.base.PagingRecyclerViewAdapter
import com.github.xtorrent.xtorrent.home.model.HomeResource
import com.github.xtorrent.xtorrent.search.SearchResourcesActivity
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import java.util.regex.Pattern

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class HomeResourcesFragment : ContentFragment(), HomeResourcesContract.View {
    companion object {
        private const val EXTRA_TYPE = "type"

        fun newInstance(type: HomeResource.Type): HomeResourcesFragment {
            val fragment = HomeResourcesFragment()
            val args = Bundle()
            args.putString(EXTRA_TYPE, type.name)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home_resources, container, false)
    }

    private lateinit var _presenter: HomeResourcesContract.Presenter

    private val _type by lazy {
        HomeResource.Type.valueOf(arguments.getString(EXTRA_TYPE))
    }

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)
    private val _refreshView by bindView<SwipeRefreshLayout>(R.id.refreshView)

    private val _adapter by lazy {
        ResourceItemAdapter(_type, context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)
        _recyclerView.adapter = _adapter

        bindSubscribe(_refreshView.refreshes()) {
            _presenter.subscribe()
        }

        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun stopRefreshing() {
        if (_refreshView.isRefreshing) _refreshView.isRefreshing = false
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setContentView(resources: List<HomeResource>) {
        _adapter.addItems(resources, PagingRecyclerViewAdapter.STATE_LOADING_COMPLETED)
        displayContentView()
    }

    override fun setPresenter(presenter: HomeResourcesContract.Presenter) {
        _presenter = presenter
        _presenter.setType(_type)
    }

    class ResourceItemAdapter(val type: HomeResource.Type, val context: Context) : PagingRecyclerViewAdapter<HomeResource>() {
        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ResourceItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_resources, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as ResourceItemViewHolder
            val item = getItem(position)
            holder.titleView.text = item.title()
            holder.descriptionView.text = item.description()
            holder.descriptionView.visibility = if (type == HomeResource.Type.NEWLY) {
                View.GONE
            } else {
                View.VISIBLE
            }
            holder.itemView.setOnClickListener {
                val matcher = _pattern.matcher(item.url())
                if (matcher.find()) {
                    SearchResourcesActivity.start(context, matcher.group(1))
                }
            }
        }

        private val _pattern by lazy {
            Pattern.compile("/s/(.*)(___)")
        }

        override fun onLoadMore(pageNumber: Int) {
            // Ignored.
        }

        override fun getLoadCount(): Int {
            return 0
        }

        override fun onRetry(pageNumber: Int) {
            // Ignored.
        }
    }

    class ResourceItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleView by bindView<TextView>(R.id.titleView)
        val descriptionView by bindView<TextView>(R.id.descriptionView)
    }

    override fun getTitle(): String? {
        return null
    }
}