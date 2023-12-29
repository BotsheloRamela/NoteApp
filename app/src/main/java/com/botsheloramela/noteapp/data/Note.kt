package com.botsheloramela.noteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
