package com.github.ebrahimi16153.mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ebrahimi16153.mvvm.staticvariable.Const


@Entity(tableName = Const.TABLE_NAME)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val todoID: Int,
    val title: String,
    val description: String,
    val done: Boolean
)