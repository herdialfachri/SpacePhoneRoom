package com.herdialfachri.spacephoneroom.repository

import android.content.Context
import com.herdialfachri.spacephoneroom.entitiy.AppDatabase
import com.herdialfachri.spacephoneroom.entitiy.UserLogin
import java.util.UUID

class UserRepository(context: Context) {

    private val userLoginDao = AppDatabase.getInstance(context).userLoginDao()

    fun register(email: String, password: String): UserLogin? {
        val userLogin = UserLogin(email = email, password = password)
        userLoginDao.insert(userLogin)
        return userLogin
    }

    fun login(email: String, password: String): String? {
        val userLogins = userLoginDao.getAll()
        val userLogin = userLogins.find { it.email == email && it.password == password }

        return if (userLogin != null) {
            val token = UUID.randomUUID().toString()
            userLogin.token = token
            userLoginDao.update(userLogin)
            token
        } else {
            null
        }
    }

    fun resetPassword(email: String, newPassword: String): Boolean {
        val userLogins = userLoginDao.getAll()
        val userLogin = userLogins.find { it.email == email }
        return if (userLogin != null) {
            userLogin.password = newPassword
            userLoginDao.update(userLogin)
            true
        } else {
            false
        }
    }

    fun forgotPassword(email: String): UserLogin? {
        val userLogins = userLoginDao.getAll()
        return userLogins.find { it.email == email }
    }

    fun logout(token: String): Boolean {
        val userLogins = userLoginDao.getAll()
        val userLogin = userLogins.find { it.token == token }
        return if (userLogin != null) {
            userLogin.token = null
            userLoginDao.update(userLogin)
            true
        } else {
            false
        }
    }
}
