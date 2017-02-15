package com.github.grubber.xtorrent.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.grubber.xtorrent.core.di.qualifier.ForApplication
import com.github.grubber.xtorrent.core.di.scope.ApplicationScope
import com.github.grubber.xtorrent.db.model.HomeResourceModel
import com.github.grubber.xtorrent.db.model.ResourceItemModel
import com.github.grubber.xtorrent.db.model.ResourceModel
import javax.inject.Inject

/**
 * Created by grubber on 16/11/29.
 */
@ApplicationScope
class DelightfulOpenHelper @Inject constructor(private @ForApplication val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(HomeResourceModel.CREATE_TABLE)
        db?.execSQL(ResourceModel.CREATE_TABLE)
        db?.execSQL(ResourceItemModel.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}

const val DB_NAME = "xtorrent.db"
const val DB_VERSION = 1