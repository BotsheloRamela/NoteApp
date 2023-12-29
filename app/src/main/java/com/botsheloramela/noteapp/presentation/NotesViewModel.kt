package com.botsheloramela.noteapp.presentation

import androidx.lifecycle.ViewModel
import com.botsheloramela.noteapp.data.NoteDao

class NotesViewModel(
    private val dao: NoteDao
): ViewModel() {

}