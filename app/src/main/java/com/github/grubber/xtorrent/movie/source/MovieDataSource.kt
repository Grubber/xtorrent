package com.github.grubber.xtorrent.movie.source

import com.github.grubber.xtorrent.movie.model.Movie
import rx.Observable

/**
 * Created by grubber on 2016/12/25.
 */
interface MovieDataSource {
    fun getMovies(pageNumber: Int): Observable<List<Movie>>
    fun getMovie(url: String): Observable<Movie>
}