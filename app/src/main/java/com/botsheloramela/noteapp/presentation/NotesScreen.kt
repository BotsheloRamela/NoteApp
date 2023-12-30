package com.botsheloramela.noteapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.botsheloramela.noteapp.domain.DateTimeUtil

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
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(notes.size) { index ->
                NoteCard(
                    note = notes[index],
                    onEvent = eventHandler,
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

    val formattedDate = remember(note.timestamp) {
        val localDateTime = DateTimeUtil.millisToLocalDateTime(note.timestamp)
        DateTimeUtil.formatNoteDate(localDateTime)
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete note",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clickable(MutableInteractionSource(), null) {
                        onEvent(NotesEvent.DeleteNote(note))
                    }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = note.content,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = formattedDate,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.End)
        )
    }
}