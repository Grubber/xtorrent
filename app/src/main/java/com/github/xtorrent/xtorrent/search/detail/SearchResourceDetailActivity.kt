package com.github.xtorrent.xtorrent.search.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.XApplication
import com.github.xtorrent.xtorrent.base.XActivity
import javax.inject.Inject

/**
 * @author Grubber
 */
class SearchResourceDetailActivity : XActivity() {
    companion object {
        private const val _EXTRA_TITLE = "title"
        private const val _EXTRA_URL = "url"

        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, SearchResourceDetailActivity::class.java)
            intent.putExtra(_EXTRA_TITLE, title)
            intent.putExtra(_EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: SearchResourceDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(_EXTRA_TITLE)
        val url = intent.getStringExtra(_EXTRA_URL)
        val fragment = SearchResourceDetailFragment.newInstance(title, url)
        XApplication.from(this)
                .searchResourcesRepositoryComponent
                .plus(SearchResourceDetailPresenterModule(fragment))
                .inject(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}