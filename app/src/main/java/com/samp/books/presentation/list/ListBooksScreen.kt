package com.samp.books.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samp.books.R
import com.samp.books.presentation.components.BookCard
import com.samp.books.presentation.components.BookEvent
import com.samp.books.presentation.components.SortOptions
import com.samp.books.utils.AddEditBooksScreen
import kotlinx.coroutines.launch


@Composable
fun ListBooksScreen(navController: NavController, booksViewModel: ListBooksViewModel) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddEditBooksScreen(-1))
                },
                modifier = Modifier.background(Color.White)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a book")
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = 8.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.main_heading),
                style = TextStyle(fontSize = 32.sp)
            )

            SortOptions(bookOrder = booksViewModel.sortOrder.value, onSortOrderChange = { order ->
                booksViewModel.onEvent(BookEvent.Order(order))
            })

            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(booksViewModel.books.value) { book ->
                    BookCard(book, onDeleteClick = {
                        booksViewModel.onEvent(BookEvent.Delete(book))
                        scope.launch {
                            snackbarHostState.showSnackbar("Deleted book successfully")
                        }
                    },
                        modifier = Modifier.clickable {
                            navController.navigate(AddEditBooksScreen(book.id))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}