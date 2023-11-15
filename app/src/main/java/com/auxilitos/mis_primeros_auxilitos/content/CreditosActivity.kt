package com.auxilitos.mis_primeros_auxilitos.content

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auxilitos.mis_primeros_auxilitos.MainActivity
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityCreditosBinding

class CreditosActivity : AppCompatActivity() {

  private lateinit var binding: ActivityCreditosBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCreditosBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnReturn.setOnClickListener {
      startActivity(Intent(this@CreditosActivity, MainActivity::class.java))
    }

  }


}