package com.alves.livrosfirebase.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alves.livrosfirebase.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)




// marcando que isso eh um composable (uma tela ou parte da tela)
@Composable
fun DetalhesLivro(
    navController: NavController, // pra navegar entre telas
    titulo: String,               // titulo do livro que chegou da tela anterior (ph)
    autor: String,                // autor do livro (ph)
    genero: String                // genero do livro (ph)
) {





    Scaffold(
        topBar = { // barra de cima da tela
            TopAppBar(


                title = { Text("Detalhes do Livro") }, // titulo da barra
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueDark, // cor de fundo da barra
                    titleContentColor = White  // cor do texto da barra
                )
            )
        }




    ) { innerPadding -> // esse innerpadding vem do scaffold pra respeitar barras e etc
        Column(


            modifier = Modifier
                .fillMaxSize()               // ocupa toda a tela
                .background(GrayLight)       // cor de fundo da coluna
                .padding(innerPadding)       // respeita o padding do scaffold
                .padding(16.dp),             // mais um padding interno
            verticalArrangement = Arrangement.Top // os itens ficam em cima
        )




        {
            // mostrando os dados do livro
            Text("Título: $titulo", fontWeight = FontWeight.Bold, color = BlueDark)
            Text("Autor: $autor", color = GrayDark)
            Text("Gênero: $genero", color = GrayDark)


            Spacer(modifier = Modifier.height(20.dp)) // espaço em branco entre os textos e o botao




            // botao pra voltar pra lista
            Button(


                onClick = { navController.popBackStack() }, // volta pra tela anterior
                colors = ButtonDefaults.buttonColors(containerColor = Orange), // cor do botao
                shape = MaterialTheme.shapes.medium // cantos arredondados
            )


            {
                Text("Voltar para lista", color = White) // texto do botao
            }
        }
    }
}
