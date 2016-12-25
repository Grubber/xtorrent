package com.github.xtorrent.xtorrent.movie.source

import com.github.xtorrent.xtorrent.movie.model.Movie
import rx.Observable

/**
 * Created by grubber on 2016/12/25.
 */
interface MovieDataSource {
    fun getMovies(pageNumber: Int): Observable<List<Movie>>
}