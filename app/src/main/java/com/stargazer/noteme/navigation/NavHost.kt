package com.stargazer.noteme.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stargazer.noteme.data.local.NoteEntity
import com.stargazer.noteme.ui.screens.addnote.AddNoteScreen
import com.stargazer.noteme.ui.screens.home.HomeScreenContent
import com.stargazer.noteme.ui.viewmodel.NoteViewModel


@Composable
fun NoteNavHost(
    navController: NavHostController,
    viewModel: NoteViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController, //uygulama içerisindeki rotaların kaptanını çağırmış olduk
        startDestination = HomeRoute, //rotaların başlangıç noktasını belirttik
        modifier = Modifier.padding(paddingValues)
    ) {

        //ilk rotamız olan homeroute un içine anasayfamız olan scaffoldScreeni çağırıyoruz
        composable<HomeRoute> {
            HomeScreenContent(viewModel = viewModel)
        }

        composable<AddNoteRoute> {
            AddNoteScreen(
                onSaveButton = {subject, body, imageUri, isFav ->
                    viewModel.insertNoteWithImage(subject, body, imageUri, isFav)
                    navController.popBackStack()
                },
                onShowCategory = {}
            )
        }
    }
}