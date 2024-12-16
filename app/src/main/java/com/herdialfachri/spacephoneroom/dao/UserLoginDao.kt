package com.herdialfachri.spacephoneroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.herdialfachri.spacephoneroom.entitiy.UserLogin

@Dao
interface UserLoginDao {
    @Query("SELECT * FROM user_login")
    fun getAll(): List<UserLogin>

    @Insert
    fun insert(userLogin: UserLogin): Long

    @Update
    fun update(userLogin: UserLogin)

    @Query("SELECT loginId FROM user_login WHERE email = :email")
    fun getLoginIdByEmail(email: String): Int

    // Fungsi baru untuk menghapus token berdasarkan email
    @Query("UPDATE user_login SET token = NULL WHERE email = :email")
    fun clearTokenByEmail(email: String)
}
