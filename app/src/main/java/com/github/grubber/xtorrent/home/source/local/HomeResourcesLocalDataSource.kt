package com.github.grubber.xtorrent.home.source.local

import com.github.grubber.xtorrent.db.DatabaseManager
import com.github.grubber.xtorrent.db.model.HomeResourceModel
import com.github.grubber.xtorrent.home.model.HomeResource
import com.github.grubber.xtorrent.home.source.HomeResourcesDataSource
import rx.Observable
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 16/11/29.
 */
class HomeResourcesLocalDataSource(private val databaseManager: DatabaseManager) : HomeResourcesDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getHomeResources(type: HomeResource.Type): Observable<List<HomeResource>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val query = HomeResource.FACTORY.select_all(type)
                    val cursor = _db.rawQuery(query.statement, query.args)
                    val resources = arrayListOf<HomeResource>()
                    while (cursor.moveToNext()) {
                        resources += HomeResource.MAPPER.map(cursor)
                    }
                    cursor.close()
                    it.onNext(resources)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getHomeResource(url: String): Observable<HomeResource> {
        // TODO

        return emptyObservable()
    }

    override fun saveHomeResource(resource: HomeResource) {
        val insert = HomeResourceModel.Insert_row(_db, HomeResource.FACTORY)
        insert.bind(resource.title(), resource.description(), resource.url(), resource.type())
        insert.program.execute()
    }

    fun saveHomeResources(resources: List<HomeResource>) {
        _db.beginTransaction()
        try {
            resources.forEach {
                saveHomeResource(it)
            }
            _db.setTransactionSuccessful()
        } finally {
            _db.endTransaction()
        }
    }

    fun deleteAllHomeResources(type: HomeResource.Type) {
        val delete = HomeResourceModel.Delete_all(_db, HomeResource.FACTORY)
        delete.bind(type)
        delete.program.execute()
    }
}