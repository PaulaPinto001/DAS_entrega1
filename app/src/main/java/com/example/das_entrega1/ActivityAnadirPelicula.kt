package com.example.das_entrega1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityAnadirPelicula : AppCompatActivity() {

    private lateinit var inputTitulo: EditText
    private lateinit var inputDescripcion: EditText
    private lateinit var inputTags: EditText
    private lateinit var inputRating: RatingBar
    private lateinit var inputGenero: Spinner
    private lateinit var btnGuardar: Button

    private var tituloText: String = ""
    private var descripcionText: String = ""
    private var tagsText: String = ""
    private var ratingValue: Float = 0f
    private var generoIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_pelicula)

        inputTitulo = findViewById(R.id.inputTitulo)
        inputDescripcion = findViewById(R.id.inputDescripcion)
        inputTags = findViewById(R.id.inputTags)
        inputRating = findViewById(R.id.inputRating)
        inputGenero = findViewById(R.id.input_spinner)
        btnGuardar = findViewById(R.id.btn_guardar)

        btnGuardar.setOnClickListener {
            guardarInformacion()
        }

        // Restaurar estado si savedInstanceState no es nulo
        savedInstanceState?.let {
            tituloText = it.getString("titulo", "")
            descripcionText = it.getString("descripcion", "")
            tagsText = it.getString("tags", "")
            ratingValue = it.getFloat("rating", 0f)
            generoIndex = it.getInt("generoIndex", 0)
            actualizarUI()
        }
    }

    private fun actualizarUI() {
        inputTitulo.setText(tituloText)
        inputDescripcion.setText(descripcionText)
        inputTags.setText(tagsText)
        inputRating.rating = ratingValue
        inputGenero.setSelection(generoIndex)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("titulo", inputTitulo.text.toString())
        outState.putString("descripcion", inputDescripcion.text.toString())
        outState.putString("tags", inputTags.text.toString())
        outState.putFloat("rating", inputRating.rating)
        outState.putInt("generoIndex", inputGenero.selectedItemPosition)

        super.onSaveInstanceState(outState)
    }

    private fun guardarInformacion() {
        val titulo = inputTitulo.text.toString()
        val descripcion = inputDescripcion.text.toString()
        val tags = inputTags.text.toString()
        val genero = inputGenero.selectedItem.toString()
        val rating = inputRating.rating

        if (titulo.isNotEmpty() && descripcion.isNotEmpty() && tags.isNotEmpty() && genero.isNotEmpty()) {
            val data = Intent().apply {
                putExtra("titulo", titulo)
                putExtra("descripcion", descripcion)
                putExtra("tags", tags)
                putExtra("rating", rating)
                putExtra("genero", genero)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
        }
    }
}

