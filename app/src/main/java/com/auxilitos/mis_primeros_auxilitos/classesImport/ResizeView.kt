package com.auxilitos.mis_primeros_auxilitos.classesImport

import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityProfileBinding

@RequiresApi(Build.VERSION_CODES.M)
class ResizeView: AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Redimensiona la pantalla de la app
        val display: Display = (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
        val width: Int = display.width
        val height: Int = display.height

        this.window.setLayout(6 * width / 7, 4 * height / 5)

    }





}