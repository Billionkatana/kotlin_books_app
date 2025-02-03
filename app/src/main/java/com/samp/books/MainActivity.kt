package com.samp.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.samp.books.data.source.BooksDatabase
import com.samp.books.presentation.addedit.AddEditBookViewModel
import com.samp.books.presentation.addedit.AddEditBooksScreen
import com.samp.books.presentation.list.ListBooksScreen
import com.samp.books.presentation.list.ListBooksViewModel
import com.samp.books.ui.theme.BooksTheme
import com.samp.books.utils.AddEditBooksScreen
import com.samp.books.utils.BooksListScreen

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BooksDatabase::class.java,
            BooksDatabase.DATABASE_NAME
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = BooksListScreen,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable<BooksListScreen> {
                            val books = viewModel<ListBooksViewModel>() {
                                ListBooksViewModel(db.dao)
                            }
                            ListBooksScreen(navController, books)
                        }
                        composable<AddEditBooksScreen> { navBackStackEntry ->

                            val args: AddEditBooksScreen =
                                navBackStackEntry.toRoute<AddEditBooksScreen>()

                            val book = viewModel<AddEditBookViewModel>() {
                                AddEditBookViewModel(db.dao)
                            }
                            AddEditBooksScreen(navController, book)
                        }
                    }

                }
            }
        }
    }
}