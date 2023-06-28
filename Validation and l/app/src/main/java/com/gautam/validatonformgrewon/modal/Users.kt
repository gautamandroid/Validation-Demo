package com.gautam.validatonformgrewon.modal

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class Users(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String? = null,
    var image: String? = null,
    var email: String? = null,
    var passworld: String? = null,
    var conformpassworld: String? = null,
    var gender: String? = null,
    var googlefacebook: String? = null,
    var sociallogin: String? = null,
    var google: String? = null,
    var loginType: String? = null,
    val phonenumber: String? = null,
    val dob: String? = null
)
