package com.herdialfachri.spacephoneroom.repository

import android.content.Context
import com.herdialfachri.spacephoneroom.entitiy.AppDatabase
import com.herdialfachri.spacephoneroom.entitiy.UserLogin
import java.util.UUID

class UserRepository(context: Context) {

    private val userLoginDao = AppDatabase.getInstance(context).userLoginDao()
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun register(email: String, password: String): UserLogin {
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
            sharedPreferences.edit().putString("token", token).apply()
            sharedPreferences.edit().putString("email", email).apply()
            token
        } else {
            null
        }
    }

    fun logout(email: String) {
        // Hapus token dari database
        userLoginDao.clearTokenByEmail(email)

        // Hapus token dari SharedPreferences
        with(sharedPreferences.edit()) {
            remove("token")
            remove("email")
            apply()
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
}
