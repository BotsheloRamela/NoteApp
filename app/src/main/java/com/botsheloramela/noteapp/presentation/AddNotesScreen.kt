package com.botsheloramela.noteapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.botsheloramela.noteapp.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotesScreen(
    navController: NavController,
    eventHandler: (NotesEvent) -> Unit
) {

    // Use state for note input
    val titleState = remember { mutableStateOf("") }
    val contentState = remember { mutableStateOf("") }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    eventHandler(NotesEvent.SaveNote(
                        title = titleState.value,
                        content = contentState.value
                    ))
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Note",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = titleState.value,
                onValueChange = {
                    titleState.value = it
                },
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
                placeholder = {
                    Text(
                        text = "Title",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = contentState.value,
                onValueChange = {
                    contentState.value = it
                },
                placeholder = {
                    Text(
                        text = "Description",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            )
        }
    }
}