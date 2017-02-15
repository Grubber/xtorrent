package com.github.grubber.xtorrent.movie.source

import com.github.grubber.xtorrent.core.BASE_MOVIE_URL
import com.github.grubber.xtorrent.movie.model.Movie
import com.github.grubber.xtorrent.movie.model.MoviePicture
import com.github.grubber.xtorrent.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable
import java.util.regex.Pattern
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
                        movies += Movie(
                                title = title,
                                coverImage = coverImage,
                                url = url,
                                headerImage = null,
                                moviePictures = null,
                                description = null
                        )
                    }
                    it.onNext(movies)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getMovie(url: String): Observable<Movie> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    var movie: Movie? = null
                    val document = newJsoupConnection(url).get()
                    document.getElementsByClass("main")[0].let {
                        val headerNode = it.getElementsByClass("top")[0]

                        val styleText = headerNode.attr("style")
                        val styleTextRegex = "background: url\\('(.*)'\\);"
                        val styleTextPattern = Pattern.compile(styleTextRegex)
                        val styleTextMatcher = styleTextPattern.matcher(styleText)
                        var headerImage: String? = null
                        if (styleTextMatcher.find()) {
                            headerImage = styleTextMatcher.group(1)
                        }

                        val title = headerNode.getElementsByClass("title")[0].text()

                        val moviePictures = arrayListOf<MoviePicture>()
                        it.getElementsByClass("picture-container").map {
                            moviePictures += MoviePicture(it.select("img")[0].attr("src"))
                        }

                        val contentRegex = "<!-- Content -->([\\s\\S]*)<!-- Tags -->"
                        val contentPattern = Pattern.compile(contentRegex)
                        val contentMatcher = contentPattern.matcher(it.html())
                        var description = ""
                        if (contentMatcher.find()) {
                            description = contentMatcher.group(1)
                        }

                        movie = Movie(
                                title = title,
                                headerImage = headerImage,
                                moviePictures = moviePictures,
                                coverImage = null,
                                url = null,
                                description = description)
                    }
                    it.onNext(movie)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}