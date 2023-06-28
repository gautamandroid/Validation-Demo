package com.gautam.validatonformgrewon.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gautam.validatonformgrewon.modal.Users

@Dao
interface UserDao {

    @Insert
    fun insertRecord(user: Users)

    @Query("select * from `user_table`")
    fun getUserList(): LiveData<List<Users>>

    @Query("select * from `user_table` where id ")
    fun getUser(): LiveData<List<Users>>

    @Query("select * from `user_table` where email =:email ")
    fun getEmail(email: String): Users?


    @Query("UPDATE user_table SET name=:name,gender=:gender,email=:email,passworld=:passeord,image=:imagebase64,conformpassworld=:conformpassword WHERE email = :email")
    fun updateRecord(
        name: String,
        gender: String,
        email: String,
        passeord: String,
        imagebase64: String,
        conformpassword: String
    )

    @Query("UPDATE USER_TABLE SET passworld = :pass WHERE email=:Email ")
    fun updaredata(Email: String?, pass: String)


}