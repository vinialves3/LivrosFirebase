@file:OptIn(ExperimentalMaterial3Api::class)
package com.alves.livrosfirebase.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alves.livrosfirebase.datasource.Authentication
import com.alves.livrosfirebase.datasource.DataSource
import kotlinx.coroutines.launch




@OptIn(ExperimentalMaterial3Api::class)
@Composable




fun ListaLivros(navController: NavController) {



    val dataSource = DataSource()
    var listaTarefas by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var mensagem by remember { mutableStateOf("") }
    val auth = Authentication()



    // Carrega a lista de livros ao abrir a tela
    LaunchedEffect(Unit) {
        dataSource.listarTarefas(
            onResult = { tarefas -> listaTarefas = tarefas },
            onFailure = { e -> mensagem = "Erro: ${e.message}" }
        )
    }





    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()






    // Menu de navegação
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("Usuário", fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = { }
                )



                NavigationDrawerItem(
                    label = { Text(auth.user?.email.toString()) },
                    selected = false,
                    onClick = { }
                )



                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    icon = { Icon(Icons.Default.Close, contentDescription = "") },
                    onClick = {
                        auth.logout()
                        navController.navigate("Login")
                    }
                )





                HorizontalDivider()



                Text(
                    "Menu de Opções",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
                NavigationDrawerItem(
                    label = { Text(text = "Lista de Tarefas") },
                    selected = false,
                    onClick = { navController.navigate("ListaTarefa") }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Cadastrar Tarefas") },
                    selected = false,
                    onClick = { navController.navigate("CadastroTarefa") }
                )
            }
        }
    )



    {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Livros Cadastrados") },
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
                    onClick = { navController.navigate("CadastroLivros") },
                    containerColor = Color(0xFF66BB6A),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                }
            }
        )



        { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp)
            )





            {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(listaTarefas) { tarefa ->
                        val titulo = tarefa["tarefa"] as? String ?: "Sem título"
                        val genero = tarefa["genero"] as? String ?: "Sem gênero"
                        val autor = tarefa["autor"] as? String ?: "Sem autor"
                        val descricao = tarefa["descricao"] as? String ?: "Sem descrição"

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )




                        {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            )



                            {
                                Text(
                                    "📖 $titulo",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20),
                                    fontSize = 18.sp
                                )
                                Row {
                                    IconButton(
                                        onClick = {
                                            dataSource.deletarTarefa(titulo)
                                            navController.navigate("ListaLivros")
                                        }
                                    )



                                    {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Apagar livro",
                                            tint = Color.Red,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(8.dp))
                                    IconButton(
                                        onClick = {
                                            navController.navigate(
                                                "exibirDescricao/$titulo/$genero/$autor/$descricao"
                                            )
                                        }
                                    )




                                    {
                                        Icon(
                                            Icons.Default.Info,
                                            contentDescription = "Descrição livro",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color(0xFF81C784)
                            )
                        }
                    }
                }





                if (mensagem.isNotBlank()) {
                    Text(
                        text = mensagem,
                        modifier = Modifier.padding(8.dp),
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
