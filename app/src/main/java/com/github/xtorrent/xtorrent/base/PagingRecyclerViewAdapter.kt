package com.github.xtorrent.xtorrent.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.xtorrent.xtorrent.R
import timber.log.Timber

/**
 * Created by zhihao.zeng on 16/11/29.
 */
abstract class PagingRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FOOTER = TYPE_HEADER + 1
        const val TYPE_NORMAL = TYPE_FOOTER + 1
        const val TYPE_LOADING_MORE = TYPE_NORMAL + 1
        const val TYPE_LOADING_MORE_ERROR = TYPE_LOADING_MORE + 1

        const val STATE_LOADING_SUCCEED = 1
        const val STATE_LOADING_FAILED = 0
        const val STATE_LOADING_COMPLETE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return when (viewType) {
            TYPE_HEADER -> onCreateHeaderViewHolder(parent, viewType)

            TYPE_FOOTER -> onCreateFooterViewHolder(parent, viewType) // TODO How to show footer? type?

            TYPE_LOADING_MORE -> onCreateLoadingMoreItemViewHolder(parent, viewType)

            TYPE_LOADING_MORE_ERROR -> onCreateLoadingMoreErrorItemViewHolder(parent, viewType)

            else -> return onCreateBasicItemViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder.itemViewType) {
            TYPE_HEADER -> onBindHeaderView(holder, position)

            TYPE_FOOTER -> onBindFooterView(holder, position)

            TYPE_LOADING_MORE -> onBindLoadingMoreItemView(holder, position)

            TYPE_LOADING_MORE_ERROR -> onBindLoadingMoreErrorItemView(holder, position)

            else -> onBindBasicItemView(holder, if (hasHeader()) position - 1 else position)
        }
    }

    override fun getItemCount(): Int {
        var itemCount = basicItemCount
        if (hasHeader()) {
            itemCount += 1
        }
        if (hasFooter()) {
            itemCount += 1
        }
        if (shouldLoadingMore()) {
            itemCount += 1
        }
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && hasHeader()) {
            return TYPE_HEADER
        }
        if (position == itemCount - 1 && shouldLoadingMore()) {
            if (_loadingState == STATE_LOADING_SUCCEED) {
                return TYPE_LOADING_MORE
            } else if (_loadingState == STATE_LOADING_FAILED) {
                return TYPE_LOADING_MORE_ERROR
            } else if (_loadingState == STATE_LOADING_COMPLETE) {
                return TYPE_FOOTER
            }
        }
        if (position == itemCount - 1 && hasFooter()) {
            return TYPE_FOOTER
        }
        return TYPE_NORMAL
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView?.let {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (shouldLoadingMore() && !_isLoading && _loadingState == STATE_LOADING_SUCCEED) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            val layoutManager = it.layoutManager as LinearLayoutManager
                            if (hasFooter()) {
                                val lastChildView = layoutManager.getChildAt(layoutManager.childCount - 2)
                                // TODO padding
                                val lastChildBottom = lastChildView.bottom + (lastChildView.layoutParams as RecyclerView.LayoutParams).bottomMargin
                                val lastPosition = layoutManager.getPosition(lastChildView)
                                val footerView = layoutManager.getChildAt(layoutManager.childCount - 1)
                                // TODO padding
                                val footerTop = footerView.top - (footerView.layoutParams as RecyclerView.LayoutParams).topMargin
                                Timber.d("### $lastChildBottom, $footerTop")
                                if (lastChildBottom == footerTop && lastPosition == layoutManager.itemCount - 2) {
                                    // TODO pageNumber is 1
                                    val pageNumber = layoutManager.itemCount / getLoadCount() + 1
                                    _isLoading = true
                                    onLoadMore(pageNumber)
                                }
                            } else {
                                val lastChildView = layoutManager.getChildAt(layoutManager.childCount - 1)
                                // TODO padding
                                val lastChildBottom = lastChildView.bottom + (lastChildView.layoutParams as RecyclerView.LayoutParams).bottomMargin
                                val lastPosition = layoutManager.getPosition(lastChildView)
                                // TODO margin
                                val parentBottom = it.bottom - it.paddingBottom
                                if (lastChildBottom == parentBottom && lastPosition == layoutManager.itemCount - 1) {
                                    // TODO pageNumber is 1
                                    val pageNumber = layoutManager.itemCount / getLoadCount() + 1
                                    _isLoading = true
                                    onLoadMore(pageNumber)
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    open fun getLoadCount(): Int = 0

    fun setLoadingState(state: Int) {
        if (shouldLoadingMore()) {
            _isLoading = false
            _loadingState = state
        }
    }

    private var _loadingState = STATE_LOADING_SUCCEED
    private var _isLoading = false

    open fun onLoadMore(pageNumber: Int) {
    }

    open fun hasHeader(): Boolean = false

    open fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? = null

    open fun onBindHeaderView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    open fun hasFooter(): Boolean = false

    open fun onCreateFooterViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? = null

    open fun onBindFooterView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    abstract fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int)

    open fun onCreateLoadingMoreItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_more, parent, false)) {}
    }

    open fun shouldLoadingMore(): Boolean = false

    open fun onBindLoadingMoreItemView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    open fun onCreateLoadingMoreErrorItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_more_error, parent, false)) {}
    }

    open fun onBindLoadingMoreErrorItemView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    abstract val basicItemCount: Int
}