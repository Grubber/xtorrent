package com.github.xtorrent.xtorrent.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by zhihao.zeng on 16/11/29.
 */
abstract class HeaderRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val TYPE_HEADER = 0
        val TYPE_FOOTER = TYPE_HEADER + 1
        val TYPE_NORMAL = TYPE_FOOTER + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, viewType)
        } else if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType)
        } else {
            return onCreateBasicItemViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0 && holder.itemViewType == TYPE_HEADER) {
            onBindHeaderView(holder, position)
        } else if (hasFooter() && position == itemCount - 1 && holder.itemViewType == TYPE_FOOTER) {
            onBindFooterView(holder, position)
        } else {
            onBindBasicItemView(holder, position)
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
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && hasHeader()) {
            return TYPE_HEADER
        }
        if (position == itemCount - 1 && hasFooter()) {
            return TYPE_FOOTER
        }
        return TYPE_NORMAL
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

    abstract val basicItemCount: Int
}