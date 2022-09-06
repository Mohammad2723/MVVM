package com.github.ebrahimi16153.mvvm.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.github.ebrahimi16153.mvvm.db.DaoTodo
import com.github.ebrahimi16153.mvvm.db.DatabaseTodo
import com.github.ebrahimi16153.mvvm.model.Todo

class TodoRepository(application: Application) {

    private var todoDao: DaoTodo
    private var allTodosList: LiveData<List<Todo>>

    init {
        val database = DatabaseTodo.getInstance(application)
        todoDao = database.dao()
        allTodosList = todoDao.getAllTodo()
    }


    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: Todo) {
        todoDao.update(todo)
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }

    suspend fun getTodo(id: Int): Todo {
      return  todoDao.getTodo(id)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    fun getAllTodo(): LiveData<List<Todo>> {
        return allTodosList
    }

}