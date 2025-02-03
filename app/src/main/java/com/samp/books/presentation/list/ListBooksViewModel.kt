package com.samp.books.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samp.books.data.source.BooksDao
import com.samp.books.presentation.BookVM
import com.samp.books.presentation.Fiction
import com.samp.books.presentation.components.BookEvent
import com.samp.books.presentation.components.SortByAuthor
import com.samp.books.presentation.components.SortByFictional
import com.samp.books.presentation.components.SortByRead
import com.samp.books.presentation.components.SortByTitle
import com.samp.books.presentation.components.SortOrder
import com.samp.books.presentation.toEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListBooksViewModel(val dao: BooksDao) : ViewModel() {

    private val _books: MutableState<List<BookVM>> = mutableStateOf(emptyList())
    var books: State<List<BookVM>> = _books

    private var _sortOrder: MutableState<SortOrder> = mutableStateOf(SortByAuthor)
    var sortOrder: State<SortOrder> = _sortOrder

    var job: Job? = null

    init {
        loadBooks(sortOrder.value)
    }

    private fun loadBooks(sortOrder: SortOrder) {
        job?.cancel()

        job = dao.getBooks().onEach { books ->
            _books.value = books.map {
                BookVM.fromEntity(it)
            }
            sortBooks(sortOrder)
        }.launchIn(viewModelScope)
    }

    private fun sortBooks(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder

        _books.value = when(sortOrder) {
            SortByRead -> books.value.sortedBy{!it.read}
            SortByAuthor -> books.value.sortedBy { it.author}
            SortByTitle -> books.value.sortedBy {it.title}
            SortByFictional -> books.value.sortedBy { it.bookType == Fiction }
        }
    }

    //event handler
    fun onEvent(event: BookEvent) {
        when(event) {
            is BookEvent.Delete -> {deleteBook(event.book)}
            is BookEvent.Order -> {
                _sortOrder.value = event.order
                loadBooks(event.order)
            }
        }
    }

    private fun upsertBook(book: BookVM) {
        viewModelScope.launch {
            dao.upsert(book.toEntity())
        }
    }

    private fun deleteBook(book: BookVM) {
        viewModelScope.launch {
            dao.deleteBook(book.toEntity())
        }
    }
}