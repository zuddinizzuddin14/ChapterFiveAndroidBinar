package com.example.mymovieapp.data.local.database.datasource

import com.example.mymovieapp.data.local.database.dao.UserDao
import com.example.mymovieapp.data.local.database.entity.UserEntity

interface UserDataSource {

    fun getUserBoolean(username: String, password: String): Boolean
    fun getUsernameBoolean(username: String): Boolean
    fun getUserId(username: String, password: String): Int
    suspend fun getUserById(id: Int): UserEntity?
    suspend fun insertUser(user: UserEntity): Long
    suspend fun updateUser(user: UserEntity): Int

}

class UserDataSourceImpl(private val userDao: UserDao) : UserDataSource {
    override fun getUserBoolean(username: String, password: String): Boolean {
        return userDao.getUserBoolean(username, password)
    }

    override fun getUsernameBoolean(username: String): Boolean {
        return userDao.getUsernameBoolean(username)
    }

    override fun getUserId(username: String, password: String): Int {
        return userDao.getUserId(username, password)
    }

    override suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    override suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    override suspend fun updateUser(user: UserEntity): Int {
        return userDao.updateUser(user)
    }

}