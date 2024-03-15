package com.example.das_entrega1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.chip.Chip
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var listaPeliculas : ListView
    private lateinit var btn_añadir : Button
    private lateinit var btn_cambioIdioma : Button

    private lateinit var gestorBD : GestorBD
    private lateinit var adapter : MovieListAdapter

    // Variable para almacenar el estado actual del idioma
    private var ingles = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Buscar elementos de la UI
        listaPeliculas = findViewById(R.id.listView_movies)
        btn_añadir = findViewById(R.id.button_añadir)
        btn_cambioIdioma = findViewById(R.id.button_lang)

        //Inicializar base de datos y obtener lista de todas las películas almacenadas
        gestorBD = GestorBD(this)

        gestorBD.insertarPelicula("El Rey León", "Una película sobre un joven león que lucha por reclamar su lugar como rey", "Animación, Aventura", "Animación", 8.5f)

        gestorBD.insertarPelicula("Titanic", "Una película épica de romance y desastre dirigida por James Cameron", "Romance, Drama", "Romance", 7.8f)

        gestorBD.insertarPelicula("El Padrino", "Una película sobre la mafia", "Crimen, Drama", "Drama", 9.2f)

        gestorBD.insertarPelicula("Interestelar", "Una aventura épica en el espacio", "Ciencia ficción, Aventura", "Aventura", 8.6f)

        gestorBD.insertarPelicula("El Señor de los Anillos: La Comunidad del Anillo", "Un hobbit se embarca en una aventura para destruir un anillo maligno", "Fantasía, Aventura", "Fantasía", 8.8f)


        val peliculas = gestorBD.obtenerTodasLasPeliculas()

        // Crear el adaptador y asignarlo al ListView
        adapter = MovieListAdapter(this, peliculas)
        listaPeliculas.adapter = adapter

        //Gestionar resultado al clicar "Guardar" en ActivityAnadirPelicula
        val startActivityIntent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    if (data != null) {
                        //Recoger resultado
                        val titulo = data.getStringExtra("titulo") ?: ""
                        val descripcion = data.getStringExtra("descripcion") ?: ""
                        val tags = data.getStringExtra("tags") ?: ""
                        val genero = data.getStringExtra("genero") ?: ""
                        val rating = data.getFloatExtra("rating", 0f)

                        // Insertar la película en la base de datos
                        val id = gestorBD.insertarPelicula(titulo, descripcion, tags, genero, rating)

                        //Crear un objeto Pelicula
                        val pelicula = Pelicula(
                            id = id,
                            titulo = titulo,
                            descripcion = descripcion,
                            tags = tags,
                            genero = genero,
                            rating = rating
                        )

                        //Añadirla al listView
                        adapter.añadirPelicula(pelicula)

                    }
                }
            }

        btn_añadir.setOnClickListener {
            val i = Intent(this@MainActivity, ActivityAnadirPelicula::class.java)
            startActivityIntent.launch(i)
        }

        btn_cambioIdioma.setOnClickListener {
            // Cambiar el idioma según el estado actual
            if (ingles) {
                cambiarIdioma("es") // Cambiar a español
            } else {
                cambiarIdioma("en") // Cambiar a inglés
            }

            // Actualizar el estado del idioma
            ingles = !ingles

            // Recrear la actividad para que los cambios surtan efecto
            recreate()
        }

        // Gestionar clics en elementos del ListView
        listaPeliculas.setOnItemClickListener { parent, view, position, id ->
            val peliculaSeleccionada = adapter.getItem(position)
            if (peliculaSeleccionada != null) {
                abrirDetallesPelicula(peliculaSeleccionada)
            }
        }

    }

    private fun cambiarIdioma(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun abrirDetallesPelicula(pelicula: Pelicula) {
        val intent = Intent(this, ActivityDetalles::class.java).apply {
            putExtra("titulo", pelicula.getTitulo())
            putExtra("descripcion", pelicula.getDescripcion())
            putExtra("tags", pelicula.getTags())
            putExtra("genero", pelicula.getGenero())
            putExtra("rating", pelicula.getRating())
            // Puedes agregar más extras según sea necesario
        }
        startActivity(intent)
    }
}