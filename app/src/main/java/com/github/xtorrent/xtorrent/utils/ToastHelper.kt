package com.github.xtorrent.xtorrent.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class ToastHelper(private val context: Context) {
    fun show(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    fun show(resId: Int) = Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
}