package com.auxilitos.mis_primeros_auxilitos.classesImport

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment

class ViewExtensions {

    inline fun <reified T : View> View.find(id: Int): T = findViewById<T>(id)
    inline fun <reified T : View> Activity.find(id: Int): T = findViewById<T>(id)
    inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T

}