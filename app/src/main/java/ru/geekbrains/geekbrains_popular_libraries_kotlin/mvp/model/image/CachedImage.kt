package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image

import androidx.room.Entity

@Entity
data class CachedImage(
    val url:String,
    val imagePath: String
)