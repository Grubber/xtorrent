package com.github.xtorrent.xtorrent.movie.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.ContentFragment
import com.github.xtorrent.xtorrent.base.XFragment
import com.github.xtorrent.xtorrent.movie.model.Movie
import com.github.xtorrent.xtorrent.movie.view.LoopViewPager
import com.github.xtorrent.xtorrent.search.SearchResourcesActivity
import com.github.xtorrent.xtorrent.search.detail.SearchResourceDetailActivity
import com.viewpagerindicator.CirclePageIndicator
import java.net.URLDecoder
import java.util.*
import java.util.regex.Pattern

/**
 * Created by grubber on 16/12/26.
 */
class MovieDetailFragment : ContentFragment(), MovieDetailContract.View {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_URL = "url"

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setUrl(_url)
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: MovieDetailContract.Presenter) {
        _presenter = presenter
    }

    private val _headerImageView by bindView<ImageView>(R.id.headerImageView)
    private val _titleView by bindView<TextView>(R.id.titleView)
    private val _picturesViewPager by bindView<LoopViewPager>(R.id.picturesViewPager)
    private val _pictureIndicator by bindView<CirclePageIndicator>(R.id.picturesIndicator)
    private val _webView by bindView<WebView>(R.id.webView)

    override fun setContentView(movie: Movie) {
        picasso().load(movie.headerImage)
                .placeholder(ColorDrawable(R.color.colorGray))
                .error(ColorDrawable(R.color.colorGray))
                .fit()
                .into(_headerImageView)
        _titleView.text = movie.title

        _picturesViewPager.setPageTransformer(true, ZoomOutPageTransformer())
        _picturesViewPager.pageMargin = context.resources.getDimensionPixelOffset(R.dimen.page_margin)
        val pagerAdapter = MoviePicturePagerAdapter(childFragmentManager)
        movie.moviePictures?.map {
            MoviePictureFragment.newInstance(it.img)
        }?.let {
            pagerAdapter.fragments = it as ArrayList<MoviePictureFragment>
        }
        movie.moviePictures?.first()?.let {
            pagerAdapter.fragments.add(MoviePictureFragment.newInstance(it.img))
        }
        movie.moviePictures?.last()?.let {
            pagerAdapter.fragments.add(0, MoviePictureFragment.newInstance(it.img))
        }
        _picturesViewPager.adapter = pagerAdapter
        _picturesViewPager.offscreenPageLimit = pagerAdapter.count
        _pictureIndicator.setViewPager(_picturesViewPager)

        _webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    val sRegex = "http://engiy.com/s/(.*)"
                    val sPattern = Pattern.compile(sRegex)
                    val sMatcher = sPattern.matcher(it)
                    if (sMatcher.find()) {
                        SearchResourcesActivity.start(context, URLDecoder.decode(sMatcher.group(1)))
                        return@let
                    }

                    val dRegex = "http://engiy.com/d/(.*)"
                    val dPattern = Pattern.compile(dRegex)
                    val dMatcher = dPattern.matcher(it)
                    if (dMatcher.matches()) {
                        SearchResourceDetailActivity.start(context, "", it)
                        return@let
                    }
                }
                return true
            }
        })
        _webView.loadDataWithBaseURL("", movie.description, "text/html", "utf-8", "")

        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        _webView.removeAllViews()
        _webView.destroy()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return _title
    }

    class ZoomOutPageTransformer : ViewPager.PageTransformer {
        private val _MIN_SCALE = 0.85f

        override fun transformPage(page: View?, position: Float) {
            val pageWidth = page!!.width
            val pageHeight = page.height
            val scaleFactor = Math.max(_MIN_SCALE, 1 - Math.abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val horzMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                page.translationX = horzMargin - vertMargin / 2
            } else {
                page.translationX = -horzMargin + vertMargin / 2
            }

            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
        }
    }

    class MoviePicturePagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        var fragments = arrayListOf<MoviePictureFragment>()

        override fun getCount(): Int {
            return fragments.size - 2
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }
    }

    class MoviePictureFragment : XFragment() {
        companion object {
            private const val EXTRA_URL = "url"

            fun newInstance(url: String): MoviePictureFragment {
                val fragment = MoviePictureFragment()
                val args = Bundle()
                args.putString(EXTRA_URL, url)
                fragment.arguments = args
                return fragment
            }
        }

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater?.inflate(R.layout.layout_movie_picture, container, false)
        }

        private val _imageView by bindView<ImageView>(R.id.imageView)

        private val _url by lazy {
            arguments.getString(EXTRA_URL)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            picasso().load(_url)
                    .placeholder(ColorDrawable(R.color.colorGray))
                    .error(ColorDrawable(R.color.colorGray))
                    .fit()
                    .into(_imageView)
        }

        override fun getTitle(): String? {
            return null
        }
    }
}