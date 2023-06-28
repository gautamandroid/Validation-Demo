package com.gautam.validatonformgrewon.singuperrorbody

import com.gautam.validatonformgrewon.apimodal.RegisterResponse
import com.google.gson.annotations.SerializedName

data class SingUpErrorBody(
    @SerializedName("data")
    val `data`: RegisterResponse.Data,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("user_api")
        val userApi: String,
        @SerializedName("user_created_datetime")
        val userCreatedDatetime: String,
        @SerializedName("user_dob")
        val userDob: String,
        @SerializedName("user_email")
        val userEmail: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_is_verify")
        val userIsVerify: String,
        @SerializedName("user_mobile_no")
        val userMobileNo: String,
        @SerializedName("user_name")
        val userName: String,
        @SerializedName("user_password")
        val userPassword: String,
        @SerializedName("user_profilepic")
        val userProfilepic: String,
        @SerializedName("user_update_datetime")
        val userUpdateDatetime: String
    )
}