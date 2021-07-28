package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Maybe
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.CachedImage

@Dao
interface CacheFileDao {

    @Insert
    fun insert(image: CachedImage)

    @Insert
    fun insert(vararg image: CachedImage)

    @Insert
    fun insert(image: List<CachedImage>)

    @Delete
    fun delete(image: CachedImage)

    @Delete
    fun delete(vararg image: CachedImage)

    @Delete
    fun delete(image: List<CachedImage>)

    @Update
    fun update(image: CachedImage)

    @Update
    fun update(vararg image: CachedImage)

    @Update
    fun update(image: List<CachedImage>)

    @Query("SELECT * FROM CachedImage")
    fun getAllImage() : List<CachedImage>

    @Query("SELECT * FROM CachedImage WHERE url=:url")
    fun getImageByUrl(url: String) : Maybe<CachedImage>

}