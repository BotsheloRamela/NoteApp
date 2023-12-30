package com.botsheloramela.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.botsheloramela.noteapp.data.Note
import com.botsheloramela.noteapp.data.NotesDatabase
import com.botsheloramela.noteapp.presentation.AddNotesScreen
import com.botsheloramela.noteapp.presentation.NotesScreen
import com.botsheloramela.noteapp.presentation.NotesViewModel
import com.botsheloramela.noteapp.ui.theme.NoteAppTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NotesViewModel> (
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun<T : ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(database.noteDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    viewModel.loadNotes()

                    val notes: List<Note> by viewModel.notes
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = "NotesScreen") {
                        composable("NotesScreen") {
                            NotesScreen(
                                notes = notes,
                                navController = navController,
                                eventHandler = viewModel::onEvent
                            )
                        }
                        composable("AddNotesScreen") {
                            AddNotesScreen(
                                navController = navController,
                                eventHandler = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}
