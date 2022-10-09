package com.example.mymovieapp.data.local.database.dao

import androidx.room.*
import com.example.mymovieapp.data.local.database.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT EXISTS (SELECT * FROM USERS WHERE username == :username AND password == :password)")
    fun getUserBoolean(username: String, password: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM USERS WHERE username == :username)")
    fun getUsernameBoolean(username: String): Boolean

    @Query("SELECT id FROM USERS WHERE username == :username AND password == :password")
    fun getUserId(username: String, password: String): Int

    @Query("SELECT * FROM USERS WHERE id == :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Insert
    suspend fun insertUser(note: UserEntity): Long

    @Update
    suspend fun updateUser(note: UserEntity): Int

}