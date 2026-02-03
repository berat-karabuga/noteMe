package com.stargazer.noteme.ui.screens.addnote

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AddNoteScreen(
    onSaveButton:(String,String,Uri?,Boolean)->Unit,
    onShowCategory:(Boolean) -> Unit
) {

    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var showCategory by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    var showDialog by remember { mutableStateOf(false) }
    var selectedCategoryIsFavorite by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = subject,
                onValueChange = {subject = it},
                label = { Text("Subject") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .size(130.dp,130.dp)
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    if (selectedImageUri != null){
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Image",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Text("Add Image", style = MaterialTheme.typography.labelLarge)
                        }
                    }

                }

            }


        }



        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = body,
            onValueChange = {body = it},
            label = { Text("Your note")},
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

        Row(modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)) {

            if (showDialog){
                AlertDialog(
                    onDismissRequest = {showDialog = false},
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog =false
                        }) {
                            Text("Okay")
                        }
                    },
                    title = { Text("Select Category")},
                    text = {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedCategoryIsFavorite = !selectedCategoryIsFavorite }
                                    .padding(8.dp)
                            ) {
                                RadioButton(
                                    selected = selectedCategoryIsFavorite,
                                    onClick = {selectedCategoryIsFavorite = !selectedCategoryIsFavorite}
                                )
                                Text("Favorites", modifier = Modifier.padding(start = 8.dp))
                            }
                        }
                    }
                )
            }


            Button(
                onClick = {showDialog = true},
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp,
                    hoveredElevation = 10.dp
                )
                ) {
                Text("Select Category")
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    onSaveButton(subject,body,selectedImageUri,selectedCategoryIsFavorite)
                },
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp,
                    hoveredElevation = 10.dp
                )
            ) {
                Text("Save")
            }


        }
    }
}



