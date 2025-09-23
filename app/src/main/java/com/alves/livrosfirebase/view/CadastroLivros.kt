package com.alves.livrosfirebase.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alves.livrosfirebase.datasource.DataSource
import kotlinx.coroutines.launch






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroLivros(navController: NavController) {






    // Estados dos campos do formulário
    var genero by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }





    val scope = rememberCoroutineScope()
    val dataSource = DataSource()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)





    // Drawer de navegação
    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFFE8F5E9)) {
                Text(
                    text = "Menu do app de livros",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold
                )






                HorizontalDivider(color = Color(0xFF81C784))
                NavigationDrawerItem(
                    label = { Text(text = "Lista de livros") },
                    selected = false,
                    onClick = { navController.navigate("ListaLivros") },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )





                NavigationDrawerItem(
                    label = { Text(text = "Cadastro de livros") },
                    selected = true,
                    onClick = { navController.navigate("CadastroLivros") },
                    icon = { Icon(Icons.Default.MailOutline, contentDescription = null) }
                )
            }
        }
    )




    {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Cadastrar Livro") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2E7D32),
                        titleContentColor = Color.White
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
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                )
            },





            bottomBar = {
                BottomAppBar(containerColor = Color(0xFF388E3C)) {
                    Text(
                        "VAF",
                        color = Color.White,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            },



            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("ListaLivros") },
                    containerColor = Color(0xFF66BB6A),
                    contentColor = Color.White
                )

                {
                    Icon(Icons.Default.Menu, contentDescription = "Confirmar")
                }

            }
        )




        { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )




            {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text(text = "Título do Livro") }
                )




                Spacer(modifier = Modifier.size(12.dp))

                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text(text = "Autor do Livro") }
                )




                Spacer(modifier = Modifier.size(16.dp))

                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text(text = "Genero do Livro") }
                )



                Spacer(modifier = Modifier.size(16.dp))

                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text(text = "Descricao do Livro") }
                )




                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        if (titulo.isNotBlank() && autor.isNotEmpty() && genero.isNotEmpty() && descricao.isNotEmpty()) {
                            dataSource.salvarTarefa(
                                titulo,
                                autor,
                                genero,
                                descricao,
                                onSuccess = { mensagem = "Livro Cadastrado" },
                                onFailure = { mensagem = "Erro no Cadastro" }
                            )


                            // Limpa os campos após cadastro
                            titulo = ""
                            autor = ""
                            genero = ""
                            descricao = ""
                        } else {
                            mensagem = "Preencha todos os campos!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1B5E20),
                        contentColor = Color.White
                    )
                )




                {
                    Text(text = "Cadastrar Livro")
                }

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = mensagem,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
