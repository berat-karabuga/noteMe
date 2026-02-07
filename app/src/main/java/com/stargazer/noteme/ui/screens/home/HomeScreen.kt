package com.stargazer.noteme.ui.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.stargazer.noteme.R
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
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // All Notes Card with Glassmorphism + Inner Shadow
        Box(
            modifier = Modifier.padding(4.dp)
        ) {
            // Bulanık arka plan layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 1f),
                                Color.White.copy(alpha = 1f)
                            )
                        )
                    )
                    .blur(
                        radius = 25.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
            )

            // Border ve içerik
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Transparent)
                    .drawWithContent {
                        drawContent()
                        // İç gölge efekti
                        drawRoundRect(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 4f)
                                ),
                                radius = size.width * 10f
                            ),
                            style = Stroke(width = 10f)
                        )
                    }

            ) {
                Column(
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFC9C9C9).copy(alpha = 0.5f), // Aynı rengin biraz daha şeffaf hali
                                Color(0xFFC9C9C9),        // Senin istediğin renk
                            )
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            "All Notes",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
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
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Button with Glassmorphism
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Bulanık arka plan layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 1f),
                                Color.White.copy(alpha = 1f)
                            )
                        )
                    )
                    .blur(
                        radius = 25.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
            )

            // Button içerik
            Button(
                onClick = {showDialog = true},
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(50.dp)
                    )
            ) {
                Text(
                    text = selectedCategory ?: "Select Category",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (selectedCategory == "Favorites"){
            // Favorites Card with Glassmorphism + Inner Shadow
            Box(
                modifier = Modifier.padding(4.dp)
            ){
                // Bulanık arka plan layer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 1f),
                                    Color.White.copy(alpha = 1f)
                                )
                            )
                        )
                        .blur(
                            radius = 25.dp,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                )

                // Border ve içerik
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                        .drawWithContent {
                            drawContent()
                            // İç gölge efekti
                            drawRoundRect(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.1f)
                                    ),
                                    radius = size.width * 0.8f
                                ),
                                style = Stroke(width = 20f)
                            )
                        }
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                "Favorites",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
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


    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.selectedCategoryByHome = if (tempCategoryIsFavorite) "Favorites" else null
                        showDialog =false }
                )
                {
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
                        RadioButton(selected = tempCategoryIsFavorite, onClick = { tempCategoryIsFavorite = true })
                        Text("Favorites", modifier = Modifier.padding(start = 8.dp))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable { tempCategoryIsFavorite = false }.padding(8.dp)
                    ) {
                        RadioButton(selected = !tempCategoryIsFavorite, onClick = { tempCategoryIsFavorite = false })
                        Text("No Category", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        )
    }
}