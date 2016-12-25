package com.github.xtorrent.xtorrent.search

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.base.PagingRecyclerViewAdapter
import com.github.xtorrent.xtorrent.search.detail.SearchResourceDetailActivity
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem
import com.github.xtorrent.xtorrent.search.view.ResourceItemView

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class SearchResourcesFragment : ContentFragment(), SearchResourcesContract.View {
    companion object {
        private val EXTRA_KEYWORD = "keyword"

        fun newInstance(keyword: String): SearchResourcesFragment {
            val fragment = SearchResourcesFragment()
            val args = Bundle()
            args.putString(EXTRA_KEYWORD, keyword)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_search_resources, container, false)
    }

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)

    private lateinit var _presenter: SearchResourcesContract.Presenter

    private val _keyword by lazy {
        arguments.getString(EXTRA_KEYWORD)
    }

    private val _adapter by lazy {
        SearchResourceItemAdapter(context, _presenter)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)
        _recyclerView.adapter = _adapter

        _presenter.setKeyword(_keyword)
        _presenter.subscribe()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setContentView(list: List<Pair<Resource, List<ResourceItem>>>?, loadedError: Boolean) {
        val loadingState = if (list == null && loadedError) {
            PagingRecyclerViewAdapter.STATE_LOADING_FAILED
        } else {
            PagingRecyclerViewAdapter.STATE_LOADING_SUCCEED
        }
        _adapter.addItems(list, loadingState)
        displayContentView()
    }

    override fun setPresenter(presenter: SearchResourcesContract.Presenter) {
        _presenter = presenter
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    class SearchResourceItemAdapter(private val context: Context,
                                    private val presenter: SearchResourcesContract.Presenter) : PagingRecyclerViewAdapter<Pair<Resource, List<ResourceItem>>>() {
        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return SearchResourceItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_resources, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as SearchResourceItemViewHolder
            val item = getItem(position)

            val title = item.first.title()
                    .replace("<b>", "<b><font color=\"#EA4335\">")
                    .replace("</b>", "</font></b>")
            holder.titleView.text = Html.fromHtml(title)

            holder.itemContainer.removeAllViews()
            item.second.forEach {
                val itemView = ResourceItemView(context)
                itemView.setText(it.title())
                holder.itemContainer.addView(itemView)
            }

            with(item.first) {
                holder.typeView.text = type()
                holder.filesView.text = files()
                holder.sizeView.text = size()
                holder.downloadsView.text = downloads()
                holder.updatedView.text = updated()
                holder.createdView.text = created()
            }

            holder.itemView.setOnClickListener {
                SearchResourceDetailActivity.start(context, item.first.title().replace("<b>", "").replace("</b>", ""), item.first.url())
            }
        }

        override fun getLoadCount(): Int {
            return 15
        }

        override fun onLoadMore(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

        override fun onRetry(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }
    }

    class SearchResourceItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleView by bindView<TextView>(R.id.titleView)
        val itemContainer by bindView<LinearLayout>(R.id.itemContainer)
        val typeView by bindView<TextView>(R.id.typeView)
        val filesView by bindView<TextView>(R.id.filesView)
        val sizeView by bindView<TextView>(R.id.sizeView)
        val downloadsView by bindView<TextView>(R.id.downloadsView)
        val updatedView by bindView<TextView>(R.id.updatedView)
        val createdView by bindView<TextView>(R.id.createdView)
    }

    override fun getTitle(): String? {
        return null
    }
}