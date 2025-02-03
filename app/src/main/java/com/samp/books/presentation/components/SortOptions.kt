package com.samp.books.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samp.books.presentation.BookVM

@Composable
fun SortOptions(bookOrder: SortOrder = SortByAuthor, onSortOrderChange: (SortOrder) -> Unit) {

    Row(modifier = Modifier.fillMaxWidth()) {
        BooksRadioButton(text = "Author",
            selected = bookOrder is SortByAuthor,
            onClick = {onSortOrderChange(SortByAuthor)}
        )

        Spacer(modifier = Modifier.width(8.dp))

        BooksRadioButton(text = "Read",
            selected = bookOrder is SortByRead,
            onClick = {onSortOrderChange(SortByRead)}
        )
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        BooksRadioButton(text = "Fictional",
            selected = bookOrder is SortByFictional,
            onClick = {onSortOrderChange(SortByFictional)}
        )

        Spacer(modifier = Modifier.width(8.dp))

        BooksRadioButton(text = "Title",
            selected = bookOrder is SortByTitle,
            onClick = {onSortOrderChange(SortByTitle)}
        )
    }
}

sealed class SortOrder()
data object SortByAuthor : SortOrder()
data object SortByTitle : SortOrder()
data object SortByRead : SortOrder()
data object SortByFictional : SortOrder()

data class NotesState(
    val books: List<BookVM> = emptyList(),
    val bookOrder: SortOrder = SortByAuthor
)

sealed class BookEvent {
    data class Order(val order: SortOrder) : BookEvent()
    data class Delete(val book: BookVM) : BookEvent()
}
