package com.stargazer.noteme.ui.screens.notedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.stargazer.noteme.ui.util.formatLongToDate
import com.stargazer.noteme.ui.viewmodel.NoteViewModel

@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteViewModel
) {
    val note by viewModel.getsingelNote(noteId).collectAsStateWithLifecycle(initialValue = null)
    var showDialog by remember { mutableStateOf(false) }
    var isFavoriteState by remember { mutableStateOf(false) }

    LaunchedEffect(note) {
        note?.let { isFavoriteState = it.isFavorite }
        viewModel.selectedNoteForDelete = note
    }

    Box(modifier = Modifier.fillMaxSize()){

        note?.let { currentNote ->

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = currentNote.title,
                        onValueChange = {},
                        label = { Text("Subject", color =  if (isFavoriteState) MaterialTheme.colorScheme.primary else Color.Black) },
                        readOnly = true,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .size(130.dp,130.dp)
                            .clickable {

                            }
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            if ( currentNote.imageUrl != null){
                            AsyncImage(
                                model =currentNote.imageUrl ,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }else{
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "Created At",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                )
                                Text(
                                    text = formatLongToDate(currentNote.date),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.scrim
                                )
                            }
                        }

                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = currentNote.content,
                    onValueChange = {},
                    label = { Text("Your note", color = if (isFavoriteState) MaterialTheme.colorScheme.primary else Color.Black) },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Button(onClick = {showDialog= true},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFavoriteState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    ))
                {
                    Text(if (isFavoriteState) "Favorites" else "Select Category")
                }
                
            }

            if (showDialog){
                AlertDialog(
                    onDismissRequest = {showDialog= false},
                    confirmButton = {
                        TextButton(
                            onClick = {
                                note?.let { currentNote ->
                                    viewModel.updateNote(currentNote.copy(isFavorite = isFavoriteState))

                                }
                                showDialog = false
                            }
                        ) { Text("Confirm") }
                    },
                    title = {
                        Text("Change Category")
                    },
                    text = {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isFavoriteState = true }
                                    .padding(8.dp)
                            ) {
                                RadioButton(
                                    selected = isFavoriteState == true,
                                    onClick = {isFavoriteState = true}
                                )
                                Text("Favorites", modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colorScheme.primary)
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isFavoriteState = false }
                                    .padding(8.dp)
                            ) {
                                RadioButton(
                                    selected = isFavoriteState == false,
                                    onClick = {
                                        isFavoriteState = false
                                    }
                                )
                                Text("No Category (Default)", modifier = Modifier.padding(start = 8.dp), color = Color.Black)
                            }

                        }

                    }
                )
            }

        }

    }

}
