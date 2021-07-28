package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao.CacheFileDao
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.CachedImage


@Database(
    entities = [
        CachedImage::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ImageDatabase : RoomDatabase() {

    abstract val imageDao: CacheFileDao
    companion object {
        private var db: ImageDatabase? = null
        private const val DB_NAME: String = "local_image.db"

        fun getInstance() : ImageDatabase = db!!

        fun create(context: Context) {
            if (db == null) {
                db = Room.databaseBuilder(context, ImageDatabase::class.java, DB_NAME).build()
            }
        }
    }
}