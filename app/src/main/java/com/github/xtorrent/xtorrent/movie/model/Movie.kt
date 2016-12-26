package com.github.xtorrent.xtorrent.movie.model

/**
 * Created by grubber on 2016/12/25.
 */
data class Movie(var title: String,
                 var headerImage: String?,
                 var coverImage: String?,
                 var moviePictures: List<MoviePicture>?,
                 var url: String?)

data class MoviePicture(var img: String)