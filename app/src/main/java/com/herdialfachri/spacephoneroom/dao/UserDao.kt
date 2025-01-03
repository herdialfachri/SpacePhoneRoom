package com.herdialfachri.spacephoneroom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.herdialfachri.spacephoneroom.entitiy.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun get(uid: Int): User

    @Update
    fun update(user: User)
}
