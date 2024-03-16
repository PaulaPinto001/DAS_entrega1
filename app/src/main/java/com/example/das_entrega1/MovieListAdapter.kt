package com.example.das_entrega1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView


class MovieListAdapter(context: Context, movies: ArrayList<Pelicula>) :
    ArrayAdapter<Pelicula>(context, 0, movies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.layout_cards, parent, false)
        }

        // Obtener la película en la posición actual
        val movie = getItem(position)

        // Obtener referencia a los elementos del layout
        val textView_titulo : TextView = itemView!!.findViewById(R.id.textView_cardTitle)
        val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar_card)
        val imageView_poster : ImageView = itemView.findViewById(R.id.imageView_cardPoster)

        // Establecer el título de la película en el TextView
        if (movie != null) {
            textView_titulo.text = movie.getTitulo()
            ratingBar.rating = movie.getRating()

            // Cargar el poster del ImageView según el género de la película
            val genero = movie.getGenero()
            val posterDrawableId = when (genero) {
                "Acción" -> R.drawable.poster_accion
                "Comedia" -> R.drawable.poster_comedia
                "Drama" -> R.drawable.poster_drama
                "Romance" -> R.drawable.poster_romance
                "Fantasía" -> R.drawable.poster_fantasia
                "Musical" -> R.drawable.poster_musical
                "Terror" -> R.drawable.poster_terror
                "Ciencia ficción" -> R.drawable.poster_scifi
                else -> R.drawable.poster_default
            }
            imageView_poster.setImageResource(posterDrawableId)
        }

        return itemView
    }

    fun anadirPelicula(pelicula: Pelicula) {
        add(pelicula)
        notifyDataSetChanged()
    }

    fun eliminarPelicula(pelicula: Pelicula) {
        remove(pelicula)
        notifyDataSetChanged()
    }
}


