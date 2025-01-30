package com.samp.books.presentation.components

import com.samp.books.presentation.BookType

sealed interface AddEditBookEvent {
    data class EnteredAuthor(val author: String) : AddEditBookEvent
    data class EnteredTitle(val title: String) : AddEditBookEvent
    data object BookRead: AddEditBookEvent
    data object SaveBook: AddEditBookEvent
    data class TypeChanged(val bookType: BookType) : AddEditBookEvent
}