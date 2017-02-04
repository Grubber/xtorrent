package com.github.xtorrent.xtorrent.search.model

import com.github.xtorrent.xtorrent.db.model.ResourceItemModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 16/11/29.
 */
@AutoValue
abstract class ResourceItem : ResourceItemModel {
    @AutoValue
    abstract class ForResource : ResourceItemModel.For_resourceModel<Resource, ResourceItem>

    companion object {
        private val _creator: ResourceItemModel.Creator<ResourceItem> by lazy {
            ResourceItemModel.Creator<ResourceItem>(::AutoValue_ResourceItem)
        }
        val FACTORY by lazy {
            ResourceItemModel.Factory(_creator)
        }

        fun create(title: String, resource: String): ResourceItem {
            return _creator.create(0, title, resource)
        }

        private val _forResourceCreator: ResourceItemModel.For_resourceCreator<Resource, ResourceItem, ForResource> by lazy {
            ResourceItemModel.For_resourceCreator<Resource, ResourceItem, ForResource>(::AutoValue_ResourceItem_ForResource)
        }
        val FOR_RESOURCE_MAPPER = FACTORY.for_resourceMapper(_forResourceCreator, Resource.FACTORY)

        fun create(resource: Resource, resourceItem: ResourceItem): ForResource {
            return _forResourceCreator.create(resource, resourceItem)
        }
    }
}