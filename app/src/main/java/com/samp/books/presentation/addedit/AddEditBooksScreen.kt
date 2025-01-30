package com.samp.books.presentation.addedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.samp.books.R
import com.samp.books.presentation.Fiction
import com.samp.books.presentation.NonFiction
import com.samp.books.presentation.components.AddEditBookEvent
import com.samp.books.presentation.components.HorizontalTextRadioButton
import com.samp.books.utils.BooksListScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditBooksScreen(navController: NavHostController, viewModel: AddEditBookViewModel) {

    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(AddEditBookEvent.SaveBook)

                        navController.navigate(BooksListScreen)
                    },
                    modifier = Modifier.background(Color.Transparent)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Save book",
                    )
                }
                FloatingActionButton(
                    onClick = {
                        navController.navigate(BooksListScreen)
                    },
                    modifier = Modifier.background(Color.Transparent)
                        .align(Alignment.BottomStart)
                        .padding(start = 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Back",
                    )
                }
            }
        }
    ) { contentPadding ->

        LaunchedEffect(true) {
            viewModel.eventFlow.collectLatest() { event ->
                when(event) {
                    AddEditBookUiEvent.SavedBook -> navController.navigate(BooksListScreen)
                    is AddEditBookUiEvent.ShowMessage -> TODO()
                }
            }
        }


        val book = viewModel.book.value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(color = book.bookType.backgroundColor)
        ) {
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = stringResource(id = R.string.add_edit_book),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                color = Color.White
            )
            OutlinedTextField(
                value = book.author,
                label = { Text("Author") },
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredAuthor(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    Color.White
                ),
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = book.title,
                label = { Text("Title") },
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredTitle(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Read",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.headlineMedium.copy(color =
                    book.bookType.foregroundColour),
                )
                Checkbox(
                    checked = book.read,
                    onCheckedChange = {
                        viewModel.onEvent(AddEditBookEvent.BookRead)
                    }
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                HorizontalTextRadioButton(
                    selected = book.bookType::class == Fiction::class,
                    text = "Fiction",
                    color = book.bookType.foregroundColour,
                    onOptionSelected = {
                        viewModel.onEvent(AddEditBookEvent.TypeChanged(Fiction))
                    })
                HorizontalTextRadioButton(
                    selected = book.bookType::class == NonFiction::class,
                    text = "Non-Fiction",
                    color = book.bookType.foregroundColour,
                    onOptionSelected = {
                        viewModel.onEvent(AddEditBookEvent.TypeChanged(NonFiction))
                    })

            }
        }
    }
}