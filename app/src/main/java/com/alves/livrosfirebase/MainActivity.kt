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
import com.alves.livrosfirebase.view.Cadastro
import com.alves.livrosfirebase.view.CadastroLivros
import com.alves.livrosfirebase.view.ListaLivros
import com.alves.livrosfirebase.view.Login
import com.alves.livrosfirebase.view.descricaoTarefa

class MainActivity : ComponentActivity() {






    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()





        setContent {
            LivrosFirebaseTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "Login"
                )




                {
                    composable(route = "Login") {
                        Login(navController)
                    }



                    composable(route = "Cadastro") {
                        Cadastro(navController)
                    }



                    composable(route = "CadastroLivros") {
                        CadastroLivros(navController)
                    }



                    composable(route = "ListaLivros") {
                        ListaLivros(navController)
                    }



                    composable(
                        route = "exibirDescricao/{titulo}/{genero}/{autor}/{descricao}"
                    )



                    {
                        descricaoTarefa(
                            navController = navController,
                            titulo = it.arguments?.getString("titulo") ?: "",
                            genero = it.arguments?.getString("genero") ?: "",
                            autor = it.arguments?.getString("autor") ?: "",
                            descricao = it.arguments?.getString("descricao") ?: ""
                        )
                    }
                }
            }
        }
    }
}
