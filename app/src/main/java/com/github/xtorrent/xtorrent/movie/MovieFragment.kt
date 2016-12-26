package com.github.xtorrent.xtorrent.movie

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.base.PagingRecyclerViewAdapter
import com.github.xtorrent.xtorrent.movie.detail.MovieDetailActivity
import com.github.xtorrent.xtorrent.movie.model.Movie
import com.squareup.picasso.Picasso

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class MovieFragment : ContentFragment(), MovieContract.View {
    companion object {
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_movie, container, false)
    }

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)

    private val _adapter by lazy {
        MovieItemAdapter(context, _presenter, _picasso)
    }

    private lateinit var _presenter: MovieContract.Presenter
    private lateinit var _picasso: Picasso

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)
        _recyclerView.adapter = _adapter

        _presenter.subscribe()
    }

    override fun setPresenter(presenter: MovieContract.Presenter) {
        _presenter = presenter
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setContentView(list: List<Movie>?, loadedError: Boolean) {
        val loadingState = if (list == null && loadedError) {
            PagingRecyclerViewAdapter.STATE_LOADING_FAILED
        } else {
            PagingRecyclerViewAdapter.STATE_LOADING_SUCCEED
        }
        _adapter.addItems(list, loadingState)
        displayContentView()
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setPicasso(picasso: Picasso) {
        _picasso = picasso
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }

    class MovieItemAdapter(private val context: Context,
                           private val presenter: MovieContract.Presenter,
                           private val picasso: Picasso) : PagingRecyclerViewAdapter<Movie>() {
        override fun getLoadCount(): Int {
            return 6
        }

        override fun onLoadMore(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MovieItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as MovieItemViewHolder
            val item = getItem(position)
            picasso.load(item.coverImage)
                    .placeholder(ColorDrawable(R.color.colorGray))
                    .error(ColorDrawable(R.color.colorGray))
                    .fit()
                    .into(holder.coverView)
            holder.titleView.text = item.title
            holder.itemView.setOnClickListener {
                MovieDetailActivity.start(context, item.title, item.url!!)
            }
        }

        override fun onRetry(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

        override fun hasHeader(): Boolean {
            return true
        }

        override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
            return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_header, parent, false)) {}
        }
    }

    class MovieItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val coverView by bindView<ImageView>(R.id.coverView)
        val titleView by bindView<TextView>(R.id.titleView)
    }
}