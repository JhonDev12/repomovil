package com.auxilitos.mis_primeros_auxilitos.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auxilitos.mis_primeros_auxilitos.MainActivity
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityMisionVisionBinding

class MisionVisionActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMisionVisionBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMisionVisionBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnReturn.setOnClickListener {
      startActivity(Intent(this, MainActivity::class.java))
    }

  }

}