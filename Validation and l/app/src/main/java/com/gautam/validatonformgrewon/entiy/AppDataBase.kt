package com.gautam.validatonformgrewon.entiy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gautam.validatonformgrewon.dao.UserDao
import com.gautam.validatonformgrewon.modal.Users


@Database(entities = [Users::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var dataBase: AppDataBase? = null
        private var DATABASE_NAME = "userDatabase"

        @Synchronized
        fun getInstance(context: Context): AppDataBase {
            if (dataBase == null) {
                dataBase =
                    Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return dataBase as AppDataBase
        }
    }


}