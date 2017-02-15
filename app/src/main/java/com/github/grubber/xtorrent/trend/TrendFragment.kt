package com.github.grubber.xtorrent.trend

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.github.grubber.xtorrent.R
import com.github.grubber.xtorrent.XApplication
import com.github.grubber.xtorrent.base.XFragment
import com.github.grubber.xtorrent.widget.PagingEnabledViewPager
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by grubber on 16/11/29.
 */
class TrendFragment : XFragment() {
    companion object {
        fun newInstance(): TrendFragment {
            return TrendFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_trend, container, false)
    }

    private val _tabLayout by bindView<TabLayout>(R.id.tabLayout)
    private val _viewPager by bindView<PagingEnabledViewPager>(R.id.viewPager)

    private val _adapter by lazy {
        TabItemAdapter(childFragmentManager)
    }

    private var _items by Delegates.notNull<List<TabItem>>()

    @Inject
    lateinit var presenter: TrendResourcesPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _viewPager.adapter = _adapter
        _tabLayout.setupWithViewPager(_viewPager)

        val monthFragment = TrendResourcesFragment.newInstance(TrendResourcesFragment.TYPE_MONTH)
        val weekFragment = TrendResourcesFragment.newInstance(TrendResourcesFragment.TYPE_WEEK)

        XApplication.from(context)
                .trendResourcesRepositoryComponent
                .plus(TrendResourcesPresenterModule(monthFragment))
                .inject(this)
        XApplication.from(context)
                .trendResourcesRepositoryComponent
                .plus(TrendResourcesPresenterModule(weekFragment))
                .inject(this)

        _items = arrayListOf(
                TabItem(monthFragment, "月下载趋势"),
                TabItem(weekFragment, "周下载趋势")
        )
        _adapter.items = _items as ArrayList<TabItem>
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

    override fun getTitle(): String? {
        return null
    }
}