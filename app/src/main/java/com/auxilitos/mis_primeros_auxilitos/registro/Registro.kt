package com.auxilitos.mis_primeros_auxilitos.registro

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import com.auxilitos.mis_primeros_auxilitos.classesImport.DatePicker
import com.auxilitos.mis_primeros_auxilitos.classesImport.KeyBoard
import com.auxilitos.mis_primeros_auxilitos.client.ApiClient
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityRegistroBinding
import com.auxilitos.mis_primeros_auxilitos.model.request.RegisterRequest
import com.auxilitos.mis_primeros_auxilitos.model.response.RegisterResponse
import com.auxilitos.mis_primeros_auxilitos.classesImport.ToastCustom
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern

class Registro : AppCompatActivity(), View.OnClickListener {//Fin

    private lateinit var binding: ActivityRegistroBinding
    private val toast = ToastCustom()
    private var keyBoard = KeyBoard()
    private val PASSWORDPATTERN = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=!|°()?¡¿*.:,])" +  // at least 1 special character
                "(?=\\S+$)" +                     // no white spaces
                ".{8,}" +                         // at least 4 characters
                "$"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDate()

    }//Fin oncreate

    private fun initDate()
    {

        checkBoxValidate()
        keyBoard
        binding.btnSeleccionarFecha.setOnClickListener(this)

        binding.btnRegister.setOnClickListener {
            validate()
            getInputs()
        }

        binding.btnRegresar.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

    }

    private fun getInputs()
    {
        val name            = binding.name.text.toString()
        val email           = binding.email.text.toString()
        val fechaNacimiento = binding.fechaNacimiento.text.toString()
        val password        = binding.password.text.toString()
        val passwordConfirm = binding.passwordConfirm.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty()  && fechaNacimiento.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty())
        {
            if(validateEmail() and  validatePassword() and validateNameAndDate() and validateCheckBox())
            {
                registerUser(name, email, checkBoxValidate(), fechaNacimiento, password, passwordConfirm)
            }
        }
        else
        {
            toast.toastWarning(this, "Campos incompletos", "Completa los campos")
        }
    }

    private fun registerUser(name: String, email: String, genero: String, fechaNacimiento: String, password: String, passwordConfirm: String) {
        val registerRequest = RegisterRequest(name, email, genero, fechaNacimiento, password, passwordConfirm)
        val apiCall = ApiClient.getApiService().registerUser(registerRequest)
        apiCall.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if(response.isSuccessful)
                {
                    move()
                    finish()
                }
                else
                {
                    toast.toastError(this@Registro, "Error", "Sucedio un error inesperado o corrige tus credenciales")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                toast.toastError(this@Registro, "Error", "Ha ocurrido un error inesperado " + t.localizedMessage)
            }

        })


    }

    private fun move() {
        startActivity(Intent(this@Registro, Login::class.java))
        toast.toastSuccess(this@Registro, "Mis Primeros Auxilitos", "Registrado con exito!!!")
    }

    private fun checkBoxValidate(): String{
        val checkM = binding.checkBoxMasculino
        val checkF = binding.checkBoxFemenino
        val checkO = binding.checkBoxOtro
        checkM.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->

            if (checkM.isChecked) {
                checkF.isEnabled = false
                checkO.isEnabled = false

            } else if (!checkM.isChecked) {
                checkF.isEnabled = true
                checkO.isEnabled = true
            }

        }

        checkF.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->

            if (checkF.isChecked) {
                checkM.isEnabled = false
                checkO.isEnabled = false

            } else if (!checkF.isChecked) {
                checkM.isEnabled = true
                checkO.isEnabled = true
            }
        }

        checkO.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->

            if (checkO.isChecked) {
                checkM.isEnabled = false
                checkF.isEnabled = false

            } else if (!checkO.isChecked) {
                checkM.isEnabled = true
                checkF.isEnabled = true
            }
        }

        return if(checkM.isChecked) {
            "Masculino"
        } else if(checkF.isChecked) {
            "Femenino"
        } else{
            "Otro"
        }

    }

    private fun validate() {
        val result = arrayOf(validateEmail(), validatePassword(), validateNameAndDate(), validateCheckBox())
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

    @SuppressLint("SetTextI18n")
    private fun validateCheckBox(): Boolean {
        val checkMasculino = binding.checkBoxMasculino.isChecked
        val checkFemenino  = binding.checkBoxFemenino.isChecked
        val checkOtro      = binding.checkBoxOtro.isChecked
        val checkTerms     = binding.terms.isChecked

        val tvCheckBox     = binding.tvCheckBox
        val tvCheckTerms   = binding.tvCheckBoxTerms

        return if((!checkMasculino) and (!checkFemenino) and (!checkOtro) and (!checkTerms))
        {
            tvCheckBox.text   = "Marca alguna casilla"
            tvCheckTerms.text = "Acepta los terminos y condiciones para continuar"
            false
        }
        else if((!checkMasculino) and (!checkFemenino) and (!checkOtro))
        {
            tvCheckBox.text = "Marca alguna casilla"
            false
        }
        else if(!checkTerms)
        {
            tvCheckTerms.text = "Acepta los terminos y condiciones para continuar"
            false
        }
        else
        {
            tvCheckBox.text = null
            tvCheckTerms.text = null
            true
        }

    }

    private fun validateNameAndDate(): Boolean {
        val name = binding.name.text.toString()
        val fechaNacimiento = binding.fechaNacimiento.text.toString()
        return if(name.isEmpty()) {
            binding.name.error = "El campo no puede estar vacio"
            false
        } else if(fechaNacimiento.isEmpty()){
            binding.fechaNacimiento.error = "El campo no puede estar vacio"
            false
        } else {
            binding.name.error = null
            binding.fechaNacimiento.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.password.text.toString()
        val passwordConfirmation = binding.passwordConfirm.text.toString()

        return if(password != passwordConfirmation)
        {
            binding.password.error = "Las contraseñas no coinciden"
            binding.passwordConfirm.error = "Las contraseñas no coinciden"
            false
        }
        else if(password.isEmpty() and passwordConfirmation.isEmpty())
        {
            binding.password.error = "El campo contraseña no debe estar vacio"
            binding.passwordConfirm.error = "El campo contraseña no debe estar vacio"
            false
        }
        else if (password.length < 8) {
            binding.password.error = ("La contraseña debe contener al menos 8 caracteres")
            binding.passwordConfirm.error = ("La contraseña debe contener al menos 8 caracteres")
            false
        }
        else if (!PASSWORDPATTERN.matcher(password).matches() and !PASSWORDPATTERN.matcher(passwordConfirmation).matches()) {
            binding.password.error = ("Contraseña debil, incluye al menos un caracter especial sin espacios")
            binding.passwordConfirm.error = ("Contraseña debil, incluye al menos un caracter especial sin espacios")
            false
        }
        else {
            binding.password.error = null
            binding.passwordConfirm.error = null
            true
        }
    }

    //Mostrar calendario
    override fun onClick(p0: View?) {
        val dialogfecha = DatePicker.DatePickerFragment { year, month, day -> mostrarResultado(year, month, day) }
        dialogfecha.show(supportFragmentManager, "DatePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun mostrarResultado(year: Int, month: Int, day: Int) {
        binding.fechaNacimiento.setText("$year-$month-$day")
    }

}

