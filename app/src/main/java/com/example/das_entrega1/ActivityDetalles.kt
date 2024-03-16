package com.example.das_entrega1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActivityDetalles : AppCompatActivity() {

    private lateinit var boton_compartir : Button
    private lateinit var textView_titulo : TextView
    private lateinit var ratingBar : RatingBar
    private lateinit var textView_genero : TextView
    private lateinit var textView_descr : TextView
    private lateinit var textView_tags : TextView
    private lateinit var imageView_poster : ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        boton_compartir = findViewById(R.id.btn_compartir)
        textView_titulo = findViewById(R.id.textView_title)
        textView_genero = findViewById(R.id.textView_genre)
        textView_descr = findViewById(R.id.textView_descr)
        textView_tags = findViewById(R.id.textView_tags)
        ratingBar = findViewById(R.id.ratingBar)
        imageView_poster = findViewById(R.id.imageView_poster)

        // Obtener extras pasados a la actividad
        val titulo = intent.getStringExtra("titulo") ?: ""
        val genero = intent.getStringExtra("genero") ?: ""
        val descripcion = intent.getStringExtra("descripcion") ?: ""
        val tags = intent.getStringExtra("tags") ?: ""
        val rating = intent.getFloatExtra("rating", 0f)

        // Asignar valores a los elementos del layout
        textView_titulo.text = titulo
        textView_genero.text = genero
        textView_descr.text = descripcion
        textView_tags.text = tags
        ratingBar.rating = rating
        val posterDrawableId = when (genero) {
            "Acción" -> R.drawable.poster_accion
            "Comedia" -> R.drawable.poster_comedia
            "Drama" -> R.drawable.poster_drama
            "Romance" -> R.drawable.poster_romance
            "Fantasía" -> R.drawable.poster_fantasia
            "Musical" -> R.drawable.poster_musical
            "Terror" -> R.drawable.poster_terror
            "Ciencia ficción" -> R.drawable.poster_scifi
            else -> {R.drawable.poster_default}
        }
        imageView_poster.setImageResource(posterDrawableId)

        boton_compartir.setOnClickListener {
            // Mostrar el diálogo de introducción de email y enviar notificación de resultado
            val emailDialog = EmailDialog(titulo)
            emailDialog.show(supportFragmentManager, "EmailDialog")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("titulo", textView_titulo.text.toString())
        outState.putString("genero", textView_genero.text.toString())
        outState.putString("descripcion", textView_descr.text.toString())
        outState.putString("tags", textView_tags.text.toString())
        outState.putFloat("rating", ratingBar.rating)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textView_titulo.text = savedInstanceState.getString("titulo") ?: ""
        textView_genero.text = savedInstanceState.getString("genero") ?: ""
        textView_descr.text = savedInstanceState.getString("descripcion") ?: ""
        textView_tags.text = savedInstanceState.getString("tags") ?: ""
        ratingBar.rating = savedInstanceState.getFloat("rating", 0f)
    }

}