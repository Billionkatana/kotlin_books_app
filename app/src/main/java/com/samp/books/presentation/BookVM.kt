package com.samp.books.presentation

import com.samp.books.ui.theme.Purple80
import androidx.compose.ui.graphics.Color
import com.samp.books.domain.model.Book
import com.samp.books.presentation.components.BookEvent
import com.samp.books.presentation.components.SortByAuthor
import com.samp.books.presentation.components.SortByFictional
import com.samp.books.presentation.components.SortByRead
import com.samp.books.presentation.components.SortByTitle
import com.samp.books.ui.theme.Purple40
import com.samp.books.ui.theme.PurpleGrey40
import com.samp.books.ui.theme.PurpleGrey80
import kotlin.random.Random


data class BookVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val author: String = "",
    val read: Boolean = false,
    val bookType: BookType = Fiction,
) {
    companion object {
        fun fromEntity(entity: Book): BookVM {
            return BookVM(
                id = entity.id!!,
                title = entity.title,
                author = entity.author,
                read = entity.read,
                bookType = BookType.fromInt(entity.bookType)
            )
        }
    }
}

fun BookVM.toEntity(): Book {
    val id = if (this.id == -1) null else this.id
    return Book(
        id = id,
        title = this.title,
        author = this.author,
        read = this.read,
        bookType = bookType.toInt()
        )
}

private fun BookType.toInt(): Int =
    when (this) {
        Fiction -> 0
        NonFiction -> 1
    }



sealed class BookType(val foregroundColour: Color, val backgroundColor: Color){
    companion object {
        fun fromInt(bookType: Int) : BookType {
            return if (bookType == 0) Fiction else
                NonFiction
        }
    }
}
// singletons
data object Fiction:BookType(Purple80, PurpleGrey40)
data object NonFiction:BookType(Purple40, PurpleGrey80)