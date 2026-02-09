package com.stargazer.noteme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stargazer.noteme.data.local.NoteDB
import com.stargazer.noteme.data.repository.NoteRepository
import com.stargazer.noteme.ui.components.MainScaffold
import com.stargazer.noteme.ui.theme.NoteMeTheme
import com.stargazer.noteme.ui.viewmodel.NoteViewModel
import com.stargazer.noteme.ui.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {

    private val noteViewModel: NoteViewModel by lazy {
        // Initialize database using the singleton instance
        val noteDB = NoteDB.getDatabase(applicationContext)

        // We are creating our repository but it needs a DAO to work, so we are calling the noteDAO defined in the database
        val noteRepository = NoteRepository(noteDB.noteDao())

        /* We are creating our factory; it requires a repository as a parameter
         so we called the noteRepository variable where we defined the repository*/
        val noteViewModelFactory = NoteViewModelFactory(noteRepository)

        /* Finally to create the viewmodel we first call the viewmodel provider command then specify that the owner is this
         which is the noteViewModel variable o specify how to provide it, we call the factory Using the desired owner and factory
         we ensure that it creates the NoteViewModel that we obtained with the get command */
        ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NoteMeTheme() {


                val bottomBarColor = MaterialTheme.colorScheme.secondary
                SideEffect {
                    window.navigationBarColor = bottomBarColor.toArgb()
                }
                NoteMeApp(viewModel = noteViewModel)
            }
        }
    }
}

@Composable
private fun NoteMeApp(viewModel: NoteViewModel){
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    MainScaffold(
        navController = navController,
        viewModel = viewModel,
        currentRoute = currentRoute
    )
}












@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    NoteMeTheme {
    }
}
