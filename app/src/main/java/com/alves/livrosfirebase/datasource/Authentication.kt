package com.alves.livrosfirebase.datasource

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth






class Authentication {

    private val auth = Firebase.auth
    val user = auth.currentUser





    fun login(email: String, senha: String, onResult: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->



                if (task.isSuccessful) {
                    Log.d("Authentication", "Login efetuado com sucesso.")
                    onResult("ok")




                } else {
                    Log.d("Authentication", "Erro durante login.")
                    Log.d("Authentication", task.exception?.message.toString())
                    onResult(task.exception.toString())
                }
            }
    }





    fun cadastro(email: String, senha: String, onResult: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->




                if (task.isSuccessful) {
                    Log.d("Authentication", "Conta criada com sucesso.")
                    onResult("ok")
                }




                else {
                    Log.d("Authentication", "Erro durante criação da conta.")
                    Log.d("Authentication", task.exception?.message.toString())
                    onResult(task.exception.toString())
                }
            }
    }





    fun verificaLogado(): Boolean {
        return auth.currentUser != null
    }



    fun logout() {
        auth.signOut()
    }
}
