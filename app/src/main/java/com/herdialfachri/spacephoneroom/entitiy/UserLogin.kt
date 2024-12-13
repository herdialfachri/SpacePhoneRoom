package com.herdialfachri.spacephoneroom.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_login")
data class UserLogin(
    @PrimaryKey(autoGenerate = true) val loginId: Int? = null,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "token") var token: String? = null,
    @ColumnInfo(name = "login_time") val loginTime: Long? = null
)
