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
import com.github.xtorrent.xtorrent.base.HeaderRecyclerViewAdapter
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem
import com.github.xtorrent.xtorrent.search.view.ResourceInfoView
import com.github.xtorrent.xtorrent.search.view.ResourceItemView
import java.util.*

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
        SearchResourceItemAdapter(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)
        _recyclerView.adapter = _adapter

        _presenter.setKeyword(_keyword)
        _presenter.subscribe()
    }

    override fun setLoadingView() {
        displayLoadingView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setEmptyView() {
        displayEmptyView()
    }

    override fun setContentView(list: List<Pair<Resource, List<ResourceItem>>>) {
        _adapter.items = list as ArrayList<Pair<Resource, List<ResourceItem>>>
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

    class SearchResourceItemAdapter(private val context: Context) : HeaderRecyclerViewAdapter() {
        var items = arrayListOf<Pair<Resource, List<ResourceItem>>>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return SearchResourceItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_resources, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as SearchResourceItemViewHolder
            val item = items[position]

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

            holder.descriptionContainer.removeAllViews()
            val typeView = ResourceInfoView(context)
            typeView.setText(item.first.type())
            holder.descriptionContainer.addView(typeView)
            val filesView = ResourceInfoView(context)
            filesView.setText(item.first.files())
            holder.descriptionContainer.addView(filesView)
            val sizeView = ResourceInfoView(context)
            sizeView.setText(item.first.size())
            holder.descriptionContainer.addView(sizeView)
            val downloadsView = ResourceInfoView(context)
            downloadsView.setText(item.first.downloads())
            holder.descriptionContainer.addView(downloadsView)
            val updatedView = ResourceInfoView(context)
            updatedView.setText(item.first.updated())
            holder.descriptionContainer.addView(updatedView)
            val createdView = ResourceInfoView(context)
            createdView.setText(item.first.created())
            holder.descriptionContainer.addView(createdView)

            holder.itemView.setOnClickListener {
                // TODO
            }
        }

        override val basicItemCount: Int
            get() = items.size
    }

    class SearchResourceItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleView by bindView<TextView>(R.id.titleView)
        val itemContainer by bindView<LinearLayout>(R.id.itemContainer)
        val descriptionContainer by bindView<LinearLayout>(R.id.descriptionContainer)
    }

    override fun getTitle(): String? {
        return null
    }
}