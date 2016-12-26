package com.github.xtorrent.xtorrent.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.movie.model.Movie
import com.squareup.picasso.Picasso

/**
 * Created by zhihao.zeng on 16/12/26.
 */
class MovieDetailFragment : ContentFragment(), MovieDetailContract.View {
    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"

        fun newInstance(title: String, url: String): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val args = Bundle()
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_movie_detail, container, false)
    }

    private val _title by lazy {
        arguments.getString(EXTRA_TITLE)
    }
    private val _url by lazy {
        arguments.getString(EXTRA_URL)
    }

    private lateinit var _presenter: MovieDetailContract.Presenter
    private lateinit var _picasso: Picasso

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setUrl(_url)
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: MovieDetailContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(movie: Movie) {
        // TODO
        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setPicasso(picasso: Picasso) {
        _picasso = picasso
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