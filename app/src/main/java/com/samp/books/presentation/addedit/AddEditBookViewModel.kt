package com.samp.books.presentation.addedit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samp.books.data.source.BooksDao
import com.samp.books.presentation.BookVM
import com.samp.books.presentation.addedit.AddEditBookEvent.BookRead
import com.samp.books.presentation.addedit.AddEditBookEvent.EnteredTitle
import com.samp.books.presentation.addedit.AddEditBookEvent.SaveBook
import com.samp.books.presentation.addedit.AddEditBookEvent.TypeChanged
import com.samp.books.presentation.toEntity
import com.samp.books.utils.getBook
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddEditBookViewModel(val dao: BooksDao, bookId: Int = -1) : ViewModel() {

    private val _book = mutableStateOf(BookVM( ))
    val book : State<BookVM> = _book

    private val _eventFlow = MutableSharedFlow<AddEditBookUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun findBook(bookId: Int) {
        viewModelScope.launch {
            val bookEntity = dao.getBook(bookId)
            _book.value = bookEntity?.let { BookVM.fromEntity(it) } ?: BookVM()
        }
    }

    init {
        findBook(bookId)
    }

    fun onEvent(event: AddEditBookEvent) {
        when (event) {
            is AddEditBookEvent.EnteredAuthor -> {
                _book.value = _book.value.copy(author = event.author)
            }
            BookRead -> {
                _book.value = _book.value.copy(read = !_book.value.read)
            }
            is EnteredTitle -> {
                _book.value = _book.value.copy(title = event.title)
            }
            is TypeChanged -> {
                _book.value = _book.value.copy(bookType = event.bookType)
            }
            SaveBook -> {
                viewModelScope.launch {
                    if (book.value.title.isEmpty() || book.value.author.isEmpty()) {
                        _eventFlow.emit(AddEditBookUiEvent.ShowMessage("unable to save books"))
                    } else {
                        val entity = book.value.toEntity()
                        dao.upsert(entity)
                        _eventFlow.emit(AddEditBookUiEvent.SavedBook)
                    }

                }
            }
        }
    }
}

sealed interface AddEditBookUiEvent {
    data class ShowMessage(val message: String) : AddEditBookUiEvent
    data object SavedBook : AddEditBookUiEvent
}