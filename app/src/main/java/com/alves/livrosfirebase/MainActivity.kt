package com.alves.livrosfirebase


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alves.livrosfirebase.ui.theme.LivrosFirebaseTheme
import com.alves.livrosfirebase.view.ListaLivros
import com.alves.livrosfirebase.view.CadastroLivros


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    // tela main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // borda a borda

        setContent {
            LivrosFirebaseTheme {

                val navController = rememberNavController()
                // navegacao

                NavHost(
                    navController = navController,
                    startDestination = "CadastroLivros"
                ) {
                    // CadastroLivros como tela main
                    composable("CadastroLivros") { CadastroLivros(navController) }
                    composable("ListaLivros") { ListaLivros(navController) }
                }
            }
        }
    }
}
