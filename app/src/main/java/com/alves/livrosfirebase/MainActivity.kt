package com.alves.livrosfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alves.livrosfirebase.ui.theme.LivrosFirebaseTheme
import com.alves.livrosfirebase.view.CadastroLivros
import com.alves.livrosfirebase.view.ListaLivros
import com.alves.livrosfirebase.view.DetalhesLivro

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LivrosFirebaseTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "CadastroLivros"
                ) {
                    // Tela de cadastro
                    composable("CadastroLivros") {
                        CadastroLivros(navController)
                    }

                    // Tela de lista de livros
                    composable("ListaLivros") {
                        ListaLivros(navController)
                    }

                    // Tela de detalhes do livro (com argumentos)
                    composable(
                        route = "DetalhesLivro/{titulo}/{autor}/{genero}",
                        arguments = listOf(
                            navArgument("titulo") { type = NavType.StringType },
                            navArgument("autor") { type = NavType.StringType },
                            navArgument("genero") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        DetalhesLivro(
                            navController = navController,
                            titulo = backStackEntry.arguments?.getString("titulo") ?: "",
                            autor = backStackEntry.arguments?.getString("autor") ?: "",
                            genero = backStackEntry.arguments?.getString("genero") ?: ""
                        )
                    }
                }
            }
        }
    }
}
