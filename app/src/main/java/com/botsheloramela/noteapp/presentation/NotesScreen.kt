package com.botsheloramela.noteapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.botsheloramela.noteapp.R
import com.botsheloramela.noteapp.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notes: List<Note>,
    navController: NavController,
    eventHandler: (NotesEvent) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                IconButton(onClick = { eventHandler(NotesEvent.SortNotes) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Sort,
                        contentDescription = "Sort notes",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("AddNotesScreen") }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add note",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(notes.size) { index ->
                NoteCard(
                    note = notes[index],
                    onEvent = eventHandler
                )
            }
        }
    }
}

@Composable
fun NoteCard(
    note: Note,
    onEvent: (NotesEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = note.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = note.content,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            IconButton(onClick = { onEvent(NotesEvent.DeleteNote(note))}) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete note",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}