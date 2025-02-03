package com.samp.books.utils

import com.samp.books.presentation.BookVM
import com.samp.books.presentation.Fiction
import com.samp.books.presentation.NonFiction
import com.samp.books.presentation.components.SortByAuthor
import com.samp.books.presentation.components.SortByFictional
import com.samp.books.presentation.components.SortByRead
import com.samp.books.presentation.components.SortByTitle
import com.samp.books.presentation.components.SortOrder
import kotlinx.coroutines.flow.flow

val bookList: MutableList<BookVM> = mutableListOf(
        BookVM(title = "Catch-22", author = "Joseph Heller", read = true),
        BookVM(title = "To Kill a Mockingbird", author = "Harper Lee", read = true),
        BookVM(title = "A Tale Of Two Cities", author = "Charles Dickens", read = false),
        BookVM(
            title = "On The Origin Of Species",
            author = "Charles Darwin",
            read = false,
            bookType = NonFiction
        ),
        BookVM(
            title = "A Brief History Of Time",
            author = "Stephen Hawking",
            read = true,
            bookType = NonFiction
        ),
    )

fun getBook(bookId: Int) : BookVM? {
    return bookList.find {it.id == bookId}
}