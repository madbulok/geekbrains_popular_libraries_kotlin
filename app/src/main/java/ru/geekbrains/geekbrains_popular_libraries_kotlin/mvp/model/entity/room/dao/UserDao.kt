package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao

import androidx.room.*
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: RoomGithubUser)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg users: RoomGithubUser)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: List<RoomGithubUser>)


    @Update
    fun update(user: RoomGithubUser)

    @Update
    fun update(vararg users: RoomGithubUser)

    @Update
    fun update(user: List<RoomGithubUser>)


    @Delete
    fun delete(user: RoomGithubUser)

    @Delete
    fun delete(vararg users: RoomGithubUser)

    @Delete
    fun delete(user: List<RoomGithubUser>)


    @Query("SELECT * FROM RoomGithubUser")
    fun getAll() : List<RoomGithubUser>


    @Query("SELECT * FROM RoomGithubUser WHERE login=:login LIMIT 1")
    fun findByLogin(login: String) : RoomGithubUser
}