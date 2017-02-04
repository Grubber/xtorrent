package com.github.xtorrent.xtorrent.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.XApplication
import com.lapism.searchview.SearchAdapter
import com.lapism.searchview.SearchHistoryTable
import com.lapism.searchview.SearchItem
import com.lapism.searchview.SearchView
import javax.inject.Inject

/**
 * Created by grubber on 16/11/29.
 */
class SearchResourcesActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_KEYWORD = "keyword"

        fun start(context: Context, keyword: String) {
            val intent = Intent(context, SearchResourcesActivity::class.java)
            intent.putExtra(EXTRA_KEYWORD, keyword)
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

    private val _keyword by lazy {
        intent.getStringExtra(EXTRA_KEYWORD)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_content_with_search)

        _setSearchView()
        _replaceFragment(_keyword)
    }

    private fun _replaceFragment(keyword: String) {
        val fragment = SearchResourcesFragment.newInstance(keyword)
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

        _keyword?.let {
            _searchView.textOnly = it
            _searchHistoryTable.addItem(SearchItem(it))
        }
        _searchView.setHint(R.string.hint_search)
        _searchView.setOnMenuClickListener {
            finish()
        }
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    _searchHistoryTable.addItem(SearchItem(it))
                    _searchView.close(true)
                    _replaceFragment(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        _searchAdapter.addOnItemClickListener { view, position ->
            val textView = view.findViewById(R.id.textView_item_text) as TextView
            val query = textView.text.toString()
            _searchView.textOnly = query
            _searchView.close(true)
            _replaceFragment(query)
        }

        _searchView.adapter = _searchAdapter
    }
}