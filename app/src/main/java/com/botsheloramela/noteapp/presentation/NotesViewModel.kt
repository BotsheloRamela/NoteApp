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

    private val _notes: MutableState<List<Note>> = mutableStateOf(emptyList())
    val notes: State<List<Note>> by mutableStateOf(_notes)

    // State for note input
    private val _titleState: MutableState<String> = mutableStateOf("")

    private val _contentState: MutableState<String> = mutableStateOf("")

    private val isSortedByDateAdded: MutableState<Boolean> = mutableStateOf(true)

    // Event handling
    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> deleteNote(event.note)
            is NotesEvent.SaveNote -> saveNote(event.title, event.content)
            NotesEvent.SortNotes -> isSortedByDateAdded.value = !isSortedByDateAdded.value
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.deleteNote(note)
        }
    }

    private fun saveNote(title: String, content: String) {
        val note = Note(
            title = title,
            content = content,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            dao.upsertNote(note)
            // Clear input fields after saving
            _titleState.value = ""
            _contentState.value = ""
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