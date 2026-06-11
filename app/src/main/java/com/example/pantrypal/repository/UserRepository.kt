package com.example.pantrypal.repository

import com.example.pantrypal.data.dao.UserDao
import com.example.pantrypal.data.entity.User

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun register(
        user: User
    ) {
        userDao.insert(user)
    }

    suspend fun login(
        email: String,
        password: String
    ): User? {
        return userDao.login(email, password)
    }

    suspend fun getUserByEmail(
        email: String
    ): User? {
        return userDao.getUserByEmail(email)
    }
}