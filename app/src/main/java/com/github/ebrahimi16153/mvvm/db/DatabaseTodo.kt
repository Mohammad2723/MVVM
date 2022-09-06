package com.github.ebrahimi16153.mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.ebrahimi16153.mvvm.model.Todo
import com.github.ebrahimi16153.mvvm.staticvariable.Const

@Database(entities = [Todo::class], version = 1)
abstract class DatabaseTodo : RoomDatabase() {

    abstract fun dao(): DaoTodo


    //singleton
    companion object {
        private var instance: DatabaseTodo? = null


       fun getInstance(context: Context): DatabaseTodo {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, DatabaseTodo::class.java, Const.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance as DatabaseTodo
        }
    }
}