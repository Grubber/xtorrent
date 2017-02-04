package com.github.xtorrent.xtorrent.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by grubber on 16/11/29.
 */
abstract class BasicRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FOOTER = TYPE_HEADER + 1
        const val TYPE_NORMAL = TYPE_FOOTER + 1
    }

    private val _items by lazy {
        arrayListOf<T>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return when (viewType) {
            TYPE_HEADER -> onCreateHeaderViewHolder(parent, viewType)

            TYPE_FOOTER -> onCreateFooterViewHolder(parent, viewType)

            else -> return onCreateBasicItemViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder.itemViewType) {
            TYPE_HEADER -> onBindHeaderView(holder, position)

            TYPE_FOOTER -> onBindFooterView(holder, position)

            else -> onBindBasicItemView(holder, if (hasHeader()) position - 1 else position)
        }
    }

    override fun getItemCount(): Int {
        var itemCount = _items.size
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

    fun addItems(items: List<T>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return _items[position]
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
}