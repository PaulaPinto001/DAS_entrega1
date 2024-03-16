package com.example.das_entrega1

import android.app.AlertDialog
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import android.Manifest

class EmailDialog(titulo : String) : DialogFragment() {

    private var titulo : String = titulo
    private lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            titulo = it.getString("titulo").toString()
            email = it.getString("email").toString()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_email_dialog, null)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editText_email)

        return AlertDialog.Builder(requireActivity())
            .setTitle("Introduce el correo electrónico del destinatario")
            .setView(dialogView)
            .setPositiveButton("Enviar") { dialog, _ ->
                email = editTextEmail.text.toString()
                enviarCorreo(email)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    private fun enviarCorreo(email: String) {
        val asunto = "Invitación para ver " + titulo
        val cuerpo = "Te invito a ver " + titulo + " conmigo. ¡Anímate!"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, asunto)
            putExtra(Intent.EXTRA_TEXT, cuerpo)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
            lanzarNotificacion()

        } else {
            Toast.makeText(requireContext(), "No se encontró una aplicación de correo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun lanzarNotificacion() {
        // Pedir permisos de notificación
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            // PEDIR EL PERMISO
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        } else {
            val manager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Crear canal de notificación
            val channelId = "IdCanal"
            val channelName = "NombreCanal"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val elCanal = NotificationChannel(channelId, channelName, importance)
            manager.createNotificationChannel(elCanal)

            // Construir y mostrar notificación
            val builder = NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentTitle("Invitación lanzada")
                .setContentText("Tu invitación a $email se ha lanzado con éxito")
                .setVibrate(longArrayOf(0, 1000, 500, 1000))
                .setAutoCancel(true)
            manager.notify(1, builder.build())
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("titulo", titulo)
        outState.putString("email", email)
    }

}
