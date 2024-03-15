package com.example.das_entrega1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GestorBD(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val writableDb: SQLiteDatabase
    private val readableDb: SQLiteDatabase

    init {
        writableDb = writableDatabase
        readableDb = readableDatabase
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PeliculasDB"
        private const val TABLE_PELICULAS = "Peliculas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_DESCRIPCION = "descripcion"
        private const val COLUMN_TAGS = "tags"
        private const val COLUMN_GENERO = "genero"
        private const val COLUMN_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PELICULAS_TABLE = ("CREATE TABLE $TABLE_PELICULAS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITULO TEXT," +
                "$COLUMN_DESCRIPCION TEXT," +
                "$COLUMN_TAGS TEXT," +
                "$COLUMN_GENERO TEXT," +
                "$COLUMN_RATING REAL)")
        db.execSQL(CREATE_PELICULAS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PELICULAS")
        onCreate(db)
    }

    fun insertarPelicula(titulo: String, descripcion: String, tags: String, genero: String, rating: Float): Long {
        val values = ContentValues().apply {
            put(COLUMN_TITULO, titulo)
            put(COLUMN_DESCRIPCION, descripcion)
            put(COLUMN_TAGS, tags)
            put(COLUMN_GENERO, genero)
            put(COLUMN_RATING, rating)
        }
        val id = writableDb.insert(TABLE_PELICULAS, null, values)
        return id
    }

    fun eliminarPelicula(idPelicula: Long): Int {
        val db = this.writableDatabase
        val resultado = db.delete(TABLE_PELICULAS, "$COLUMN_ID=?", arrayOf(idPelicula.toString()))
        db.close()
        return resultado
    }

    @SuppressLint("Range")
    fun obtenerTodasLasPeliculas(): ArrayList<Pelicula> {
        val peliculas = ArrayList<Pelicula>()

        val cursor = readableDb.query(
            TABLE_PELICULAS,
            arrayOf(COLUMN_ID, COLUMN_TITULO, COLUMN_DESCRIPCION, COLUMN_TAGS, COLUMN_GENERO, COLUMN_RATING),
            null,
            null,
            null,
            null,
            null
        )

        // Iterar sobre el cursor y agregar las películas a la lista
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            val titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
            val descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION))
            val tags = cursor.getString(cursor.getColumnIndex(COLUMN_TAGS))
            val genero = cursor.getString(cursor.getColumnIndex(COLUMN_GENERO))
            val rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING))

            val pelicula = Pelicula(id, titulo, descripcion, tags, genero, rating)
            peliculas.add(pelicula)
        }

        cursor.close() // Cerrar el cursor después de leer todos los datos

        return peliculas
    }
}

