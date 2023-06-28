package com.gautam.validatonformgrewon.param


import com.google.gson.annotations.SerializedName

data class ChangePassParam(
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("old_password")
    val oldPassword: String
)