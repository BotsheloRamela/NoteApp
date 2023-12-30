package com.botsheloramela.noteapp.presentation

import com.botsheloramela.noteapp.data.Note

sealed interface NotesEvent {
    object SortNotes: NotesEvent

    data class DeleteNote(val note: Note): NotesEvent

    data class SaveNote(val title: String, val content: String): NotesEvent
}