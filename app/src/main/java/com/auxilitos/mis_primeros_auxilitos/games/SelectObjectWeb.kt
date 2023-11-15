package com.auxilitos.mis_primeros_auxilitos.games

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.auxilitos.mis_primeros_auxilitos.classesImport.CustomWebViewClient
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivitySelectObjectWebBinding

class SelectObjectWeb : AppCompatActivity() {

  private lateinit var binding: ActivitySelectObjectWebBinding

  private val url: String = "https://www.sesamestreet.org/games?id=20838"

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    binding = ActivitySelectObjectWebBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    supportActionBar?.setDisplayShowHomeEnabled(false)

    supportActionBar?.hide()

    val webView = binding.webView
    val customWebViewClient = CustomWebViewClient()
    webView.webViewClient = customWebViewClient

    webView.settings.javaScriptEnabled = true // Habilita JavaScript si es necesario
    webView.webChromeClient = WebChromeClient() // Permite mostrar diálogos de alerta, confirmación, etc.
    webView.loadUrl(url)


    // Configurar el clic del botón de regreso
    binding.backButton.setOnClickListener {
      // Verificar si el WebView puede retroceder en el historial
      if (webView.canGoBack()) {
        webView.goBack() // Retroceder en el historial del WebView
      } else {
        // Si no puede retroceder más, cerrar la actividad actual
        finish()
      }
    }

  }

}