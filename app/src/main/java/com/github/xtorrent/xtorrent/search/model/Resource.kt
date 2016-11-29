package com.github.xtorrent.xtorrent.search.model

import com.github.xtorrent.xtorrent.db.model.ResourceModel
import com.google.auto.value.AutoValue

/**
 * Created by zhihao.zeng on 16/11/29.
 */
@AutoValue
abstract class Resource : ResourceModel {
    companion object {
        private val _creator: ResourceModel.Creator<Resource> by lazy {
            ResourceModel.Creator<Resource>(::AutoValue_Resource)
        }
        val FACTORY = ResourceModel.Factory(_creator)

        fun create(url: String, title: String, magnet: String, type: String, size: String, files: String,
                   downloads: String, updated: String, created: String): Resource {
            return _creator.create(url, title, magnet, type, size, files, downloads, updated, created)
        }
    }
}