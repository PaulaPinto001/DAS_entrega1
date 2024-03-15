package com.example.das_entrega1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
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

