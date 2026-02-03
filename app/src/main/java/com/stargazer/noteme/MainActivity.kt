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
        // database oluşturuyoruz sınıfımızı çağırıp içine applicationContext verdik
        val noteDB = NoteDB.getDatabase(applicationContext)

        // repomuzu oluşturuyoruz ancak çalışması için bir daoya ihtiyaç var o yüzden db ye tanımlı noteDao yu çağırıyoruz
        val noteRepository = NoteRepository(noteDB.noteDao())

        // fabrikamızı oluşturuyoruz parametre olarak repo istiyor bizde repoyu tanımladığımız noterepo değişkenini çağırdık
        val noteViewModelFactory = NoteViewModelFactory(noteRepository)

        // son olarak viewmodel oluşturmak için önce viewmodel sağlayacı komutunu çağırıyoruz ardından owner olarak this yani
        // noteViewModel değişkeni olduğunu belirtiyoruz nasıl sağlayacığını da belirtmek için factory i çağırıyoruz
        // istediğimiz owner ve factoryi kullanarak da get komutuyla aldığımız NoteViewModeli kurmasını sağlıyoruz
        ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NoteMeTheme {

                val bottomBarColor = MaterialTheme.colorScheme.secondaryContainer
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
