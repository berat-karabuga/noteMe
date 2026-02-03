package com.stargazer.noteme.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stargazer.noteme.navigation.AddNoteRoute
import com.stargazer.noteme.navigation.HomeRoute
import com.stargazer.noteme.navigation.NoteNavHost
import com.stargazer.noteme.ui.viewmodel.NoteViewModel


@Composable
fun MainScaffold(
    navController: NavHostController,
    viewModel: NoteViewModel,
    currentRoute: String?
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (currentRoute == HomeRoute::class.qualifiedName) {
                FloatingActionButton(onClick = {
                    navController.navigate(AddNoteRoute)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add note")
                }
            }
        },
        bottomBar = {
            NoteBottomBar(navController = navController)
        },
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        NoteNavHost(
            navController = navController,
            viewModel = viewModel,
            paddingValues = innerPadding
        )
    }
}

@Composable
private fun NoteBottomBar(navController: NavHostController) {
    BottomAppBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        windowInsets = WindowInsets(0, 0, 0, 0)

    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Home,
                contentDescription = "Home",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.navigate(HomeRoute) {
                            popUpTo(HomeRoute) { inclusive = true }
                        }
                    }
            )
        }
    }
}
