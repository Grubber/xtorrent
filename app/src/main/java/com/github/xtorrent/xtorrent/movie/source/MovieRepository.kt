package com.github.xtorrent.xtorrent.movie.source

import com.github.xtorrent.xtorrent.core.BASE_MOVIE_URL
import com.github.xtorrent.xtorrent.movie.model.Movie
import com.github.xtorrent.xtorrent.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable
import javax.inject.Inject

/**
 * Created by grubber on 2016/12/25.
 */
@MovieScope
class MovieRepository @Inject constructor() : MovieDataSource {
    override fun getMovies(pageNumber: Int): Observable<List<Movie>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val movies = arrayListOf<Movie>()
                    val searchUrl = if (pageNumber == 1) BASE_MOVIE_URL else "$BASE_MOVIE_URL/page/$pageNumber"
                    val document = newJsoupConnection(searchUrl).get()
                    document.getElementsByClass("work")?.map {
                        val node = it.select("a").first()
                        val title = node.text()
                        val coverImage = node.select("img").first().attr("src")
                        val url = node.attr("abs:href")
                        movies += Movie(title, coverImage, url)
                    }
                    it.onNext(movies)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}