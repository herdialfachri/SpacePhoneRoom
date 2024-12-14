package com.herdialfachri.spacephoneroom.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "user",
    foreignKeys = [ForeignKey(entity = UserLogin::class,
        parentColumns = arrayOf("loginId"),
        childColumns = arrayOf("loginId"),
        onDelete = ForeignKey.CASCADE)])

data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "full_name") val fullName: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "gambar") val gambar: String? = null,
    @ColumnInfo(name = "loginId") val loginId: Int? = null,
    @ColumnInfo(name = "creatorEmail") val creatorEmail: String? = null
)
