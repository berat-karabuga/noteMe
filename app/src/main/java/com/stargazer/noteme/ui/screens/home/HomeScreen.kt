package com.stargazer.noteme.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.stargazer.noteme.navigation.NoteDetailRoute
import com.stargazer.noteme.ui.screens.home.components.NoteItem
import com.stargazer.noteme.ui.viewmodel.NoteViewModel

@Composable

fun HomeScreenContent(
    viewModel: NoteViewModel,
    navController: NavHostController
){
    val notes by viewModel.allNotes.collectAsStateWithLifecycle(initialValue = emptyList())
    val favoriteNotes by viewModel.favoriNotes.collectAsStateWithLifecycle(initialValue = emptyList())
    val selectedCategory = viewModel.selectedCategoryByHome
    var showDialog by remember { mutableStateOf(false) }
    var tempCategoryIsFavorite by remember { mutableStateOf(selectedCategory == "Favorites") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF2F2D1E),
                            Color(0xFF2155A1)
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
        ){
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        "All Notes",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.background,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(notes){note ->
                        NoteItem(
                            title = note.title,
                            date = note.date,
                            imageUrl = note.imageUrl,
                            onClick = {
                                navController.navigate(NoteDetailRoute(noteId = note.id))
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {showDialog = true},
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(text = selectedCategory ?: "Select Category")
        }

        if (showDialog){
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.selectedCategoryByHome = if (tempCategoryIsFavorite) "Favorites" else null
                            showDialog =false
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                title = {Text("Select Category")},
                text = {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable { tempCategoryIsFavorite = true }.padding(8.dp)
                        ) {
                            RadioButton(
                                selected = tempCategoryIsFavorite,
                                onClick = { tempCategoryIsFavorite = true },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text("Favorites", modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colorScheme.primary)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable { tempCategoryIsFavorite = false }.padding(8.dp)
                        ) {
                            RadioButton(
                                selected = !tempCategoryIsFavorite,
                                onClick = { tempCategoryIsFavorite = false },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text("No Category", modifier = Modifier.padding(start = 8.dp), color = Color.Black)
                        }
                    }
                }
            )
        }


        if (selectedCategory == "Favorites"){
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF2F2D1E),
                                Color(0xFF2155A1)
                            )
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
            ){
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

                    colors = CardDefaults.cardColors(

                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            "Favorites",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.background,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(favoriteNotes){note ->
                            NoteItem(
                                title = note.title,
                                date = note.date,
                                imageUrl = note.imageUrl,
                                onClick = { navController.navigate(NoteDetailRoute(noteId = note.id)) }
                            )
                        }
                    }
                }
            }
        }
    }
}