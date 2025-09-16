package com.alves.livrosfirebase.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alves.livrosfirebase.datasource.DataSource
import com.alves.livrosfirebase.ui.theme.*
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaLivros(navController: NavController) {
    val dataSource = DataSource()
    var listaLivros by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var mensagem by remember { mutableStateOf("") }
    var livroSelecionado by remember { mutableStateOf<Map<String, Any>?>(null) }
    val scope = rememberCoroutineScope()
    // variaveis p lembrar dos livros e detalhes de cada um



    LaunchedEffect(Unit) {
        dataSource.listarLivros(
            onResult = { listaLivros = it },
            onFailure = { e -> mensagem = "Erro: ${e.message}" }
        )
    }







    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // menu lateral

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu do App Livros", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Cadastro de Livros") },
                    selected = false,
                    onClick = { navController.navigate("CadastroLivros") }
                )
            }
        }
    )




    // drawer menu

    {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (livroSelecionado == null) "Lista de Livros"
                            else "Detalhes do Livro"
                        )

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BlueDark,
                        titleContentColor = White
                    ),

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            }
                        )
                        {
                            Icon(Icons.Default.Menu, contentDescription = "Menu",
                                tint = White, modifier = Modifier.size(30.dp))
                        }
                    }
                )



            },
            bottomBar = { BottomAppBar { } },
            floatingActionButton = {
                if (livroSelecionado == null) {
                    FloatingActionButton(onClick = { navController.navigate("CadastroLivros") }) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar")
                    }
                }
            }
        )




        { innerPadding ->

            if (livroSelecionado == null) {
                // lista livros
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GrayLight)
                        .padding(innerPadding)
                )





                {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(listaLivros) { livro ->
                            val t = livro["titulo"] as? String ?: "Sem título"
                            val a = livro["autor"] as? String ?: "Sem autor"
                            val g = livro["genero"] as? String ?: "Sem gênero"
                            // pega os dados do livro colocacos nos fields

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable { livroSelecionado = livro },
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = White)
                            )




                            {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(16.dp)
                                )




                                {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("📌 $t", fontWeight = FontWeight.Bold, color = BlueDark)
                                        Text("Autor: $a", color = GrayDark)
                                        Text("Gênero: $g", color = GrayDark)
                                    }
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Excluir livro",
                                        tint = androidx.compose.ui.graphics.Color.Red,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                dataSource.deletarLivro(t)
                                                dataSource.listarLivros(
                                                    onResult = { listaLivros = it },
                                                    onFailure = { mensagem = "Erro: ${it.message}" }
                                                )
                                            }
                                    )
                                }
                            }
                        }
                    }





                    Text(text = mensagem, modifier = Modifier.padding(8.dp))
                }
            }
            else {
                // detalhes livro especifico
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GrayLight)
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top
                )





                {
                    Text("Título: ${livroSelecionado!!["titulo"]}", fontWeight = FontWeight.Bold, color = BlueDark)
                    Text("Autor: ${livroSelecionado!!["autor"]}", color = GrayDark)
                    Text("Gênero: ${livroSelecionado!!["genero"]}", color = GrayDark)

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { livroSelecionado = null },
                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Voltar para lista", color = White)
                    }
                }
            }
        }
    }
}
