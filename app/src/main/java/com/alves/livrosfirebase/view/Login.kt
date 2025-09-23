package com.alves.livrosfirebase.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.alves.livrosfirebase.datasource.Authentication
import com.alves.livrosfirebase.ui.theme.Purple40
import kotlinx.coroutines.launch





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {





    // variaveis do formulário
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }




    val auth = Authentication()




    // se já estiver logado, vai direto pra lista
    if (auth.verificaLogado()) {
        navController.navigate("ListaLivros")
    }





    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()




    // drawer de navegação
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu de Opções",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )




                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Login do Usuário") },
                    selected = false,
                    onClick = { navController.navigate("Login") }
                )




                NavigationDrawerItem(
                    label = { Text(text = "Cadastro do Usuário") },
                    selected = false,
                    onClick = { navController.navigate("Cadastro") }
                )
            }
        }
    )



    {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Login do Usuário", color = Color.White, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            },
                        )



                        {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Icone do Menu",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple40,
                    )




                )
            }
        )



        { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )



            {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Digite seu email.") }
                )

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text("Digite sua senha.") }
                )



                Text(
                    mensagem,
                    modifier = Modifier.padding(5.dp),
                    color = Color.Red
                )



                Button(
                    onClick = {

                        if (email.isNotBlank() && senha.isNotBlank()) {
                            auth.login(email, senha) { resultado ->
                                if (resultado == "ok") {
                                    navController.navigate("ListaLivros")
                                }


                                else {
                                    mensagem = resultado
                                }


                            }
                        }



                        else {
                            mensagem = "Email ou Senha não informado"
                        }



                    }
                )




                {
                    Text("Login")
                }





                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    "Cadastre-se aqui!",
                    color = Color.DarkGray
                )
            }
        }
    }
}
