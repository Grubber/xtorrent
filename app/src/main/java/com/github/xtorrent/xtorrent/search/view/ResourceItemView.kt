package com.github.xtorrent.xtorrent.search.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.xtorrent.R

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class ResourceItemView : FrameLayout {
    constructor(context: Context?) : super(context) {
        _init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        _init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        _init()
    }

    private val _itemView by bindView<TextView>(R.id.itemView)

    private fun _init() {
        LayoutInflater.from(context).inflate(R.layout.layout_resource_item, this, true)
    }

    fun setText(text: String) {
        _itemView.text = text
    }
}