package com.alves.livrosfirebase.view

// imports basicos q o codigo usa
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
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
    // cria o objeto do banco
    val dataSource = DataSource()
    // lista q guarda os livros q vem do firebase
    var listaLivros by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    // mensagem de erro ou sucesso
    var mensagem by remember { mutableStateOf("") }
    // pra rodar coroutines (coisas assincronas)
    val scope = rememberCoroutineScope()
    // estado do menu lateral (drawer)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // quando a tela abre, busca os livros do firebase
    LaunchedEffect(Unit) {
        dataSource.listarLivros(
            onResult = { listaLivros = it },
            onFailure = { e -> mensagem = "Erro: ${e.message}" }
        )
    }

    // esse eh o menu lateral do app
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
    ) {
        // estrutura principal da tela
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                // barra de cima da tela
                TopAppBar(
                    title = { Text("Lista de Livros") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BlueDark,
                        titleContentColor = White
                    ),
                    navigationIcon = {
                        // botao do menu hamburguer (abre e fecha drawer)
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = { BottomAppBar { } }, // rodapé vazio por enquanto
            floatingActionButton = {
                // botao flutuante q leva pro cadastro de livros
                FloatingActionButton(onClick = { navController.navigate("CadastroLivros") }) {
                    Icon(Icons.Default.Info, contentDescription = "Adicionar")
                }
            }
        )


        { innerPadding ->
            // conteudo da tela (embaixo do topbar)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayLight)
                    .padding(innerPadding)
            )



            {
                // lista vertical (scroll) de livros
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(listaLivros) { livro ->


                        // pega os valores do livro, se nao tiver coloca texto default
                        val t = livro["titulo"] as? String ?: "Sem título"
                        val a = livro["autor"] as? String ?: "Sem autor"
                        val g = livro["genero"] as? String ?: "Sem gênero"





                        // card q mostra o livro
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp)
                            )





                            {
                                // coluna com as infos do livro
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("📌 $t", fontWeight = FontWeight.Bold, color = BlueDark)
                                    Text("Autor: $a", color = GrayDark)
                                    Text("Gênero: $g", color = GrayDark)
                                }





                                // botao de deletar livro
                                IconButton(
                                    onClick = {
                                        dataSource.deletarLivro(t)
                                        dataSource.listarLivros(
                                            onResult = { listaLivros = it },
                                            onFailure = { mensagem = "Erro: ${it.message}" }
                                        )
                                    }
                                )



                                {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Excluir livro",
                                        tint = androidx.compose.ui.graphics.Color.Red,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }




                                Spacer(modifier = Modifier.width(12.dp))

                                // botao q leva pra tela de detalhes
                                IconButton(
                                    onClick = {
                                        navController.navigate("DetalhesLivro/${t}/${a}/${g}")
                                    }
                                )




                                {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = "Detalhes do livro",
                                        tint = BlueDark,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }



                // mostra mensagens de erro ou info
                Text(text = mensagem, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
