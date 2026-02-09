package com.stargazer.noteme.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.stargazer.noteme.data.local.NoteEntity
import com.stargazer.noteme.ui.screens.addnote.AddNoteScreen
import com.stargazer.noteme.ui.screens.home.HomeScreenContent
import com.stargazer.noteme.ui.screens.notedetail.NoteDetailScreen
import com.stargazer.noteme.ui.viewmodel.NoteViewModel


@Composable
fun NoteNavHost(
    navController: NavHostController,
    viewModel: NoteViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController, //We have called the captain of the routes within the application
        startDestination = HomeRoute, //We specified the starting point of the routes
        modifier = Modifier.padding(paddingValues)
    ) {

        //We call our homepage scaffoldScreen inside our first route homeroute
        composable<HomeRoute> {
            HomeScreenContent(viewModel = viewModel,navController=navController)
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

        composable<NoteDetailRoute> { backStackEntry ->
            val detailRoute: NoteDetailRoute = backStackEntry.toRoute()
            val noteId = detailRoute.noteId

            NoteDetailScreen(
                noteId = noteId,
                viewModel = viewModel
            )
        }
    }
}