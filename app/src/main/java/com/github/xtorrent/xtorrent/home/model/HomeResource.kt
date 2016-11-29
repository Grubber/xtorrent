package com.github.xtorrent.xtorrent.home.model

import com.github.xtorrent.xtorrent.db.model.HomeResourceModel
import com.google.auto.value.AutoValue
import com.squareup.sqldelight.EnumColumnAdapter

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@AutoValue
abstract class HomeResource : HomeResourceModel {
    enum class Type {
        HOT, POPULARITY, NEWLY
    }

    companion object {
        private val _adapter by lazy {
            EnumColumnAdapter.create(Type::class.java)
        }
        private val _creator: HomeResourceModel.Creator<HomeResource> by lazy {
            HomeResourceModel.Creator<HomeResource>(::AutoValue_HomeResource)
        }
        val FACTORY = HomeResourceModel.Factory<HomeResource>(_creator, _adapter)
        val MAPPER = FACTORY.select_allMapper()

        fun create(title: String, description: String?, url: String, type: Type): HomeResource {
            return _creator.create(0, title, description, url, type)
        }
    }
}