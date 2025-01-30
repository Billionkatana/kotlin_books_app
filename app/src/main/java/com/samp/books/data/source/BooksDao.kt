package com.samp.books.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.samp.books.domain.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM Books")
    fun getBooks() : Flow<List<Book>>

    @Query("SELECT * FROM Books WHERE ID = :id")
    suspend fun getBook(id: Int) : Book?

    @Upsert
    suspend fun upsert(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

}