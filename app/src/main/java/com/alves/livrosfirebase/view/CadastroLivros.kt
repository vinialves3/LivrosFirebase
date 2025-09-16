package com.alves.livrosfirebase.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alves.livrosfirebase.datasource.DataSource
import com.alves.livrosfirebase.ui.theme.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroLivros(navController: NavController) {
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }
    // variavel pra armazenar os fields




    val scope = rememberCoroutineScope()
    val dataSource = DataSource()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // abbrir e fechar drawer




    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu do App Livros", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Lista de Livros") },
                    selected = false,
                    onClick = { navController.navigate("ListaLivros") }
                )
            }
        }
    ) {



        // menu lateral

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Cadastrar Livro") },
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
                FloatingActionButton(onClick = { navController.navigate("ListaLivros") }) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                }
            }



        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayLight)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )


            {
                // campos = coluna no centro

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título do Livro") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = BlueDark,
                        unfocusedBorderColor = GrayDark,
                        focusedLabelColor = BlueDark,
                        cursorColor = BlueDark
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                )



                // textfield titulo

                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text("Autor") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = BlueDark,
                        unfocusedBorderColor = GrayDark,
                        focusedLabelColor = BlueDark,
                        cursorColor = BlueDark
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                )



                // textfield autor

                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text("Gênero") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = BlueDark,
                        unfocusedBorderColor = GrayDark,
                        focusedLabelColor = BlueDark,
                        cursorColor = BlueDark
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                )



                // textfield genero

                Button(
                    onClick = {
                        if (titulo.isNotBlank() && autor.isNotBlank() && genero.isNotBlank()) {
                            dataSource.salvarLivro(
                                titulo, autor, genero,
                                onSuccess = { mensagem = "😁 Livro cadastrado" },
                                onFailure = { _ -> mensagem = "🤔 Erro no cadastro" }
                            )
                            titulo = ""
                            autor = ""
                            genero = ""
                        }
                    },



                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(top = 16.dp)
                )


                {
                    Text("Cadastrar Livro", color = White)
                }
                // botao p salvar livro e limpar o field


                Spacer(modifier = Modifier.size(20.dp))
                Text(text = mensagem)
                // exibe sucesso/erro



            }
        }
    }
}
