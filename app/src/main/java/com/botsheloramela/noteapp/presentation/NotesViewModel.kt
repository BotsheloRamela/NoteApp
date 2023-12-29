package com.botsheloramela.noteapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botsheloramela.noteapp.data.Note
import com.botsheloramela.noteapp.data.NoteDao
import kotlinx.coroutines.launch

class NotesViewModel(
    private val dao: NoteDao
): ViewModel() {

    // State variables
    private val title: MutableState<String> = mutableStateOf("")
    private val content: MutableState<String> = mutableStateOf("")
    private val isSortedByDateAdded: MutableState<Boolean> = mutableStateOf(true)
    private val _notes: MutableState<List<Note>> = mutableStateOf(emptyList())

    val notes: State<List<Note>> by mutableStateOf(_notes)

    // Event handling
    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> deleteNote(event.note)
            is NotesEvent.SaveNote -> saveNote()
            NotesEvent.SortNotes -> isSortedByDateAdded.value = !isSortedByDateAdded.value
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.deleteNote(note)
        }
    }

    private fun saveNote() {
        val note = Note(
            title = title.value,
            content = content.value,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            dao.upsertNote(note)
            // Clear input fields after saving
            title.value = ""
            content.value = ""
        }
    }

    // Load notes based on sorting
    fun loadNotes() {
        viewModelScope.launch {
            val notesFlow = if (isSortedByDateAdded.value) {
                dao.getNotesOrderedByDateAdded()
            } else {
                dao.getNotesOrderedByTitle()
            }

            // Collect the Flow to get the List<Note>
            notesFlow.collect { notesList ->
                _notes.value = notesList
            }
        }
    }

}