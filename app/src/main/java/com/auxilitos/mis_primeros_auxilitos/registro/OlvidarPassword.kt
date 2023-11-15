package com.auxilitos.mis_primeros_auxilitos.registro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import com.auxilitos.mis_primeros_auxilitos.classesImport.ToastCustom
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityOlvidarPasswordBinding

class OlvidarPassword : AppCompatActivity() {

    private lateinit var binding: ActivityOlvidarPasswordBinding
    private val toast = ToastCustom()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOlvidarPasswordBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initDate()

    }//Fin onCreate

    private fun initDate() {


        binding.btnRegresarLogin.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }

        binding.btnRestablecerPassword.setOnClickListener{
            validate()
            if(validateEmail())
            {
                toast.toastSuccess(this, "Restablecer contraseña", "Se te envio un correo electronico 😎")
                startActivity(Intent(this, Login::class.java))
            }
        }

    }



    private fun validate(){
        val result = arrayOf(validateEmail())
        if(false in result)
        {
            return
        }


    }

    private fun validateEmail():Boolean {
        val email = binding.email.text.toString()
        return if(email.isEmpty()){
            binding.email.error = "El campo del correo no puede estar vacio"
            false
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Por favor ingresa un correo valido"
            false
        } else {
            binding.email.error = null
            true
        }
    }



}//Fin