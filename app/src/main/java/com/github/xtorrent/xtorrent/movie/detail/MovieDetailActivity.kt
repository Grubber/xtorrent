package com.github.xtorrent.xtorrent.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.XApplication
import com.github.xtorrent.xtorrent.base.XActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by zhihao.zeng on 16/12/26.
 */
class MovieDetailActivity : XActivity() {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_URL = "url"

        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: MovieDetailPresenter
    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val url = intent.getStringExtra(EXTRA_URL)
        val fragment = MovieDetailFragment.newInstance(title, url)
        XApplication.from(this)
                .movieRepositoryComponent
                .plus(MovieDetailPresenterModule(fragment))
                .inject(this)
        fragment.setPicasso(picasso)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}