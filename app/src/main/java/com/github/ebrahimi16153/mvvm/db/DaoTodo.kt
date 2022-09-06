package com.github.ebrahimi16153.mvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.ebrahimi16153.mvvm.model.Todo
import com.github.ebrahimi16153.mvvm.staticvariable.Const

@Dao
interface DaoTodo {

    @Insert
    suspend fun insert(toDo: Todo)

    @Update
    suspend fun update(toDo: Todo)

    @Delete
    suspend fun delete(toDo: Todo)


    @Query("SELECT * FROM ${Const.TABLE_NAME}")
    fun getAllTodo(): LiveData<List<Todo>>


    @Query("SELECT * FROM ${Const.TABLE_NAME} WHERE todoID like :id")
    suspend fun getTodo(id: Int):Todo


    @Query("DELETE FROM ${Const.TABLE_NAME}")
    suspend fun deleteAll()

}