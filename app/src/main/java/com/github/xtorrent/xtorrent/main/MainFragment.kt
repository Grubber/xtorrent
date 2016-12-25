package com.github.xtorrent.xtorrent.main

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.XApplication
import com.github.xtorrent.xtorrent.base.XFragment
import com.github.xtorrent.xtorrent.feedback.FeedbackActivity
import com.github.xtorrent.xtorrent.home.HomeFragment
import com.github.xtorrent.xtorrent.movie.MovieFragment
import com.github.xtorrent.xtorrent.movie.MoviePresenter
import com.github.xtorrent.xtorrent.movie.MoviePresenterModule
import com.github.xtorrent.xtorrent.search.SearchResourcesActivity
import com.github.xtorrent.xtorrent.settings.SettingsActivity
import com.github.xtorrent.xtorrent.trend.TrendFragment
import com.lapism.searchview.SearchAdapter
import com.lapism.searchview.SearchHistoryTable
import com.lapism.searchview.SearchItem
import com.lapism.searchview.SearchView
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class MainFragment : XFragment() {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    private val _toolbar by bindView<Toolbar>(R.id.toolbar)
    private val _drawerLayout by bindView<DrawerLayout>(R.id.drawerLayout)
    private val _navigationView by bindView<NavigationView>(R.id.navigationView)
    private val _searchView by bindView<SearchView>(R.id.searchView)

    private var _drawerToggle: ActionBarDrawerToggle? = null

    private val _searchHistoryTable by lazy {
        SearchHistoryTable(context)
    }
    private val _searchAdapter by lazy {
        SearchAdapter(context)
    }

    private val _fragments by lazy {
        arrayListOf(
                HomeFragment.newInstance(),
                TrendFragment.newInstance(),
                _movieFragment
        )
    }

    private val _movieFragment by lazy {
        MovieFragment.newInstance()
    }

    @Inject
    lateinit var moviePresenter: MoviePresenter
    @Inject
    lateinit var picasso: Picasso

    private var _checkItemId: Int = R.id.homeMenu

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _setDrawer()
        _setNavigationView()
        _setSearchView()

        XApplication.from(context)
                .movieRepositoryComponent
                .plus(MoviePresenterModule(_movieFragment))
                .inject(this)
        _movieFragment.setPicasso(picasso)

        _initFragments()
        _replaceContentFrame(0)
    }

    override fun onResume() {
        super.onResume()

        _navigationView.setCheckedItem(_checkItemId)
    }

    private fun _setSearchView() {
        _searchView.setVoice(false)
        _searchView.setHint(R.string.hint_search)
        _searchView.setOnMenuClickListener {
            _drawerLayout.openDrawer(GravityCompat.START)
        }
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    _searchHistoryTable.addItem(SearchItem(it))
                    SearchResourcesActivity.start(context, it)
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
            SearchResourcesActivity.start(context, query)
        }

        _searchView.adapter = _searchAdapter
    }

    private fun _setNavigationView() {
        _navigationView.setNavigationItemSelectedListener {
            var index: Int = 0

            when (it.itemId) {
                R.id.homeMenu -> index = 0

                R.id.trend -> index = 1

                R.id.movie -> index = 2

                R.id.settings -> SettingsActivity.start(context)

                R.id.feedback -> FeedbackActivity.start(context)
            }

            if (it.itemId != R.id.settings && it.itemId != R.id.feedback && !it.isChecked) {
                _replaceContentFrame(index)
                it.isChecked = true
                _checkItemId = it.itemId
            }
            _drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun _initFragments() {
        val transaction = childFragmentManager.beginTransaction()
        _fragments.forEach {
            transaction.add(R.id.content, it)
        }
        transaction.commit()
    }

    private fun _replaceContentFrame(index: Int) {
        val transaction = childFragmentManager.beginTransaction()
        _fragments.forEachIndexed { i, fragment ->
            if (i == index) {
                transaction.show(fragment)
            } else {
                transaction.hide(fragment)
            }
        }
        transaction.commit()
    }

    private fun _setDrawer() {
        _drawerToggle = object : ActionBarDrawerToggle(activity, _drawerLayout, _toolbar, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)

                if (_searchView.isSearchOpen) {
                    _searchView.close(true)
                }
            }
        }
        _drawerToggle!!.isDrawerIndicatorEnabled = false
        _drawerLayout.addDrawerListener(_drawerToggle!!)
        _drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        _drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        _drawerToggle?.let {
            if (it.onOptionsItemSelected(item)) return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun onBackPressed() {
        if (_drawerLayout.isDrawerOpen(GravityCompat.START)) {
            _drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            (activity as MainActivity).exit()
        }
    }

    override fun getTitle(): String? {
        return null
    }
}