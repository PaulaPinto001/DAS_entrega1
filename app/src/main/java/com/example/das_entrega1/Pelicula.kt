package com.example.das_entrega1

class Pelicula(
    private var id: Long,
    private var titulo: String,
    private var descripcion: String,
    private var tags: String,
    private var genero: String,
    private var rating: Float
) {

    // Getters para los atributos
    fun getId(): Long {
        return id
    }

    fun getTitulo(): String {
        return titulo
    }

    fun getDescripcion(): String {
        return descripcion
    }

    fun getTags(): String {
        return tags
    }

    fun getGenero(): String {
        return genero
    }

    fun getRating(): Float {
        return rating
    }
}
