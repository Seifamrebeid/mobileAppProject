package com.example.ebeidmovies.data

import java.util.UUID

data class Movie(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val director: String,
    val releaseYear: Int,
    val genre: String,
    val isWatched: Boolean = false,
    val notes: String = "",
    val imageUri: String? = null
)

