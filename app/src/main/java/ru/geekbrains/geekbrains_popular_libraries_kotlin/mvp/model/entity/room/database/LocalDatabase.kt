package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao.RepositoryDao
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao.UserDao
import java.lang.RuntimeException

@Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepository::class
    ],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao

    companion object {
        private var db: LocalDatabase? = null
        private const val DB_NAME: String = "local.db"

        fun getInstance() : LocalDatabase = db!!

        fun create(context: Context) {
            if (db == null) {
                db = Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME).build()
            }
        }
    }
}