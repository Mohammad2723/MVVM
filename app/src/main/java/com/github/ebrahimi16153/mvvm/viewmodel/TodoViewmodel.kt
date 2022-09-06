package com.github.ebrahimi16153.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.ebrahimi16153.mvvm.model.Todo
import com.github.ebrahimi16153.mvvm.repository.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = TodoRepository(application)
    private var  getAllTodoList = repository.getAllTodo()



    suspend fun insert(todo: Todo) {
        repository.insert(todo)
    }

    suspend fun update(todo: Todo) {
        if (todo.todoID <= 0) return
        repository.update(todo)
    }

    suspend fun delete(todo: Todo) {
        if (todo.todoID <= 0) return
        repository.delete(todo)
    }

//    suspend fun getTodo(id: Int) {
//        repository.getTodo(id)
//    }
//
//    suspend fun deleteAll() {
//        repository.deleteAll()
//    }

    fun getAllTodo(): LiveData<List<Todo>> {
        return getAllTodoList
    }



}