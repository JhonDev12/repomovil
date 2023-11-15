package com.auxilitos.mis_primeros_auxilitos.classesImport

import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient : WebViewClient() {

  val urlPermited = "https://www.sesamestreet.org/games?id=20838"

  @Deprecated("Deprecated in Java",
    ReplaceWith("!(url != null && url.startsWith(\"url\"))")
  )
  override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
    return !(url != null && url.startsWith(urlPermited))
  }

}
