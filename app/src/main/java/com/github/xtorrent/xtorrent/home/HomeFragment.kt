package com.github.xtorrent.xtorrent.home

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.XApplication
import com.github.xtorrent.xtorrent.base.XFragment
import com.github.xtorrent.xtorrent.home.model.HomeResource
import com.github.xtorrent.xtorrent.widget.PagingEnabledViewPager
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class HomeFragment : XFragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    @Inject
    lateinit var homeResourcesPresenter: HomeResourcesPresenter

    private val _tabLayout by bindView<TabLayout>(R.id.tabLayout)
    private val _viewPager by bindView<PagingEnabledViewPager>(R.id.viewPager)

    private val _adapter by lazy {
        TabItemAdapter(childFragmentManager)
    }

    private var _items by Delegates.notNull<List<TabItem>>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _viewPager.isPagingEnabled = false
        _viewPager.offscreenPageLimit = 2
        _viewPager.adapter = _adapter
        _tabLayout.setupWithViewPager(_viewPager)

        val hotFragment = HomeResourcesFragment.newInstance(HomeResource.Type.HOT)
        val popularityFragment = HomeResourcesFragment.newInstance(HomeResource.Type.POPULARITY)
        val newlyFragment = HomeResourcesFragment.newInstance(HomeResource.Type.NEWLY)

        XApplication.from(context)
                .homeResourcesRepositoryComponent
                .plus(HomeResourcesPresenterModule(hotFragment))
                .inject(this)
        XApplication.from(context)
                .homeResourcesRepositoryComponent
                .plus(HomeResourcesPresenterModule(popularityFragment))
                .inject(this)
        XApplication.from(context)
                .homeResourcesRepositoryComponent
                .plus(HomeResourcesPresenterModule(newlyFragment))
                .inject(this)

        _items = arrayListOf(
                TabItem(hotFragment, context.getString(R.string.tab_item_hot)),
                TabItem(popularityFragment, context.getString(R.string.tab_item_popularity)),
                TabItem(newlyFragment, context.getString(R.string.tab_item_newly))
        )
        _adapter.items = _items as ArrayList<TabItem>
    }

    override fun getTitle(): String? {
        return null
    }

    class TabItemAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
        var items = arrayListOf<TabItem>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItem(position: Int): Fragment {
            return items[position].fragment
        }

        override fun getCount(): Int {
            return items.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return items[position].title
        }
    }

    data class TabItem(val fragment: Fragment, val title: String)
}