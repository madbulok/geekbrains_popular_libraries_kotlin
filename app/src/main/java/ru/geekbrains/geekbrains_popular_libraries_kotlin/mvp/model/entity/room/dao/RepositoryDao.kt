package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao

import androidx.room.*
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubRepository

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(repository: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg repositories: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(repository: List<RoomGithubRepository>)


    @Update
    fun update(repository: RoomGithubRepository)

    @Update
    fun update(vararg repositories: RoomGithubRepository)

    @Update
    fun update(repository: List<RoomGithubRepository>)


    @Delete
    fun delete(repository: RoomGithubRepository)

    @Delete
    fun delete(vararg repositories: RoomGithubRepository)

    @Delete
    fun delete(repository: List<RoomGithubRepository>)


    @Query("SELECT * FROM RoomGithubRepository")
    fun getAll() : List<RoomGithubRepository>


    @Query("SELECT * FROM RoomGithubRepository WHERE userId=:userID")
    fun findByLogin(userID: String) : List<RoomGithubRepository>
}