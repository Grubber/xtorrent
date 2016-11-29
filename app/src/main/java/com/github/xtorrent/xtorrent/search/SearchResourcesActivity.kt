package com.github.xtorrent.xtorrent.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.XApplication
import com.lapism.searchview.SearchAdapter
import com.lapism.searchview.SearchHistoryTable
import com.lapism.searchview.SearchItem
import com.lapism.searchview.SearchView
import javax.inject.Inject

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class SearchResourcesActivity : AppCompatActivity() {
    companion object {
        private val EXTRA_URL = "url"

        fun start(context: Context, url: String) {
            val intent = Intent(context, SearchResourcesActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: SearchResourcesPresenter

    private val _searchView by bindView<SearchView>(R.id.searchView)

    private val _searchHistoryTable by lazy {
        SearchHistoryTable(this)
    }
    private val _searchAdapter by lazy {
        SearchAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_content_with_search)

        _setSearchView()

        val url = intent.getStringExtra(EXTRA_URL)
        val fragment = SearchResourcesFragment.newInstance(url)
        XApplication.from(this)
                .searchResourcesRepositoryComponent
                .plus(SearchResourcesPresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    private fun _setSearchView() {
        _searchView.setArrowOnly(false)
        _searchView.setVoice(false)

        _searchView.setHint(R.string.hint_search)
        _searchView.setOnMenuClickListener {
            finish()
        }
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                _searchHistoryTable.addItem(SearchItem(query))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        _searchAdapter.addOnItemClickListener { view, position ->
            // TODO
        }

        _searchView.adapter = _searchAdapter
    }
}