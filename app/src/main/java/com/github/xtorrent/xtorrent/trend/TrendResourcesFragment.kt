package com.github.xtorrent.xtorrent.trend

import android.content.Context
import android.os.Bundle
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
import com.github.xtorrent.xtorrent.search.detail.SearchResourceDetailActivity
import com.github.xtorrent.xtorrent.trend.model.TrendResource

/**
 * Created by grubber on 16/12/27.
 */
class TrendResourcesFragment : ContentFragment(), TrendResourcesContract.View {
    companion object {
        const val TYPE_MONTH = "month"
        const val TYPE_WEEK = "week"

        private const val EXTRA_TYPE = "type"

        fun newInstance(type: String): TrendResourcesFragment {
            val fragment = TrendResourcesFragment()
            val args = Bundle()
            args.putString(EXTRA_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_trend_resources, container, false)
    }

    private val _type by lazy {
        arguments.getString(EXTRA_TYPE)
    }

    private lateinit var _presenter: TrendResourcesContract.Presenter

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)

    private val _adapter by lazy {
        TrendResourceItemAdapter(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)
        _recyclerView.adapter = _adapter

        _presenter.setType(_type)
        _presenter.subscribe()
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: TrendResourcesContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(resources: List<TrendResource>) {
        _adapter.addItems(resources, PagingRecyclerViewAdapter.STATE_LOADING_COMPLETED)
        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }

    class TrendResourceItemAdapter(private val context: Context) : PagingRecyclerViewAdapter<TrendResource>() {
        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return TrendResourceItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trend_resources, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as TrendResourceItemViewHolder
            val item = getItem(position)
            holder.titleView.text = item.title
            holder.createdView.text = "创建于 ${item.created}"
            holder.downloadsView.text = "下载 ${item.downloads} 次"
            holder.filesView.text = "文件数 ${item.files}"
            holder.itemView.setOnClickListener {
                SearchResourceDetailActivity.start(context, item.title, item.url)
            }
        }

        override fun onRetry(pageNumber: Int) {
            // Ignored.
        }

        override fun onLoadMore(pageNumber: Int) {
            // Ignored.
        }

        override fun getLoadCount(): Int {
            return 0
        }

        class TrendResourceItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            val titleView by bindView<TextView>(R.id.titleView)
            val downloadsView by bindView<TextView>(R.id.downloadsView)
            val createdView by bindView<TextView>(R.id.createdView)
            val filesView by bindView<TextView>(R.id.filesView)
        }
    }
}