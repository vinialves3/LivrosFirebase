package com.alves.livrosfirebase.datasource


import com.google.firebase.firestore.FirebaseFirestore





class DataSource {
// iniciando classe

    private val db = FirebaseFirestore.getInstance()

    // funcao p slavar o livro
    fun salvarLivro(
        titulo: String,
        autor: String,
        genero: String,
        onSuccess: () -> Unit,
        onFailure: (Any) -> Unit
    ) {

        // receber dados livro

        val livroMap = hashMapOf(
            "titulo" to titulo,
            "autor" to autor,
            "genero" to genero
        )

        // cria mapa com dados p enviar pro firestore

        db.collection("Livros")
            .document(titulo)
            .set(livroMap)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { erro -> onFailure(erro) }
        // salvando livro pelo id
        // onsucess se deu certo, onfailure se deu errado
    }






    // lista livros
    fun listarLivros(
        onResult: (List<Map<String, Any>>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {


        // buscar livros e retornar em lista

        db.collection("Livros")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.mapNotNull { it.data }
                onResult(lista)
            }
            .addOnFailureListener { e -> onFailure(e) }
        // transforma dados em uma lista
        // retorna onresult ou onfdailure
    }

    // excluir livro
    fun deletarLivro(titulo: String) {
        db.collection("Livros")
            .document(titulo)
            .delete()
        // excluir livro pelo id
    }
}
