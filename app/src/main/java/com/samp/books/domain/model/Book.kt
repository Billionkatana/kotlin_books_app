package com.samp.books.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val author: String,
    val read: Boolean,
    val bookType: Int
)