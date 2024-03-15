package com.example.das_entrega1

import android.app.AlertDialog
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.DialogFragment

class EmailDialog(titulo : String) : DialogFragment() {

    private var titulo = titulo

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_email_dialog, null)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editText_email)

        return AlertDialog.Builder(requireActivity())
            .setTitle("Introduce el correo electrónico del destinatario")
            .setView(dialogView)
            .setPositiveButton("Enviar") { dialog, _ ->
                val email = editTextEmail.text.toString()
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
        val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "default_channel_id"
        val channelName = "Default Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Correo enviado exitosamente")
            .setContentText("Tu invitación para ver " + titulo + " se ha enviado correctamente")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(0, notificationBuilder.build())
    }

}
