package com.auxilitos.mis_primeros_auxilitos.content

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.auxilitos.mis_primeros_auxilitos.MainActivity
import com.auxilitos.mis_primeros_auxilitos.R
import com.auxilitos.mis_primeros_auxilitos.classesImport.ToastCustom
import com.auxilitos.mis_primeros_auxilitos.client.ApiClient
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityContentPostBinding
import com.auxilitos.mis_primeros_auxilitos.model.request.ContentRequest
import com.auxilitos.mis_primeros_auxilitos.model.response.User
import com.auxilitos.mis_primeros_auxilitos.model.response.UserManager
import com.auxilitos.mis_primeros_auxilitos.registro.Profile
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class ContentPostActivity : AppCompatActivity() {

  private lateinit var binding: ActivityContentPostBinding
  private val toast = ToastCustom()
  private lateinit var imageUri: Uri

  var userData: User? = null
  private var userId = 0

  private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    uri?.let {
      imageUri = it
      binding.imageUrl.setImageURI(it)

      val borderDrawable = ContextCompat.getDrawable(this, R.drawable.bordernavigation) // Obtén el fondo redondeado desde los recursos
      binding.imageUrl.background = borderDrawable // Establece el fondo redondeado en el ImageButton

      val requestOptions = RequestOptions()
        .placeholder(R.drawable.image_preview) // Imagen de placeholder mientras se carga la imagen
        .error(R.drawable.error) // Imagen de error si la carga falla
        .diskCacheStrategy(DiskCacheStrategy.NONE) // Evita el almacenamiento en caché de la imagen para que se vuelva a cargar cada vez

      Glide.with(this)
        .load(imageUri)
        .apply(requestOptions)
        .centerCrop() // Escala la imagen para llenar el área del ImageButton mientras mantiene las proporciones y corta el exceso
        .into(binding.imageUrl)

    }
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityContentPostBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnChooseImage.setOnClickListener {
      contract.launch("image/*")
    }

    binding.btnReturn.setOnClickListener {
      startActivity(Intent(this, Profile::class.java))
    }

    createContent()

  }

  @SuppressLint("Recycle")
  private fun createContent() {

    userId = UserManager.getUserId()

    getUserProfile(userId.toString())

    binding.btnUploadContent.setOnClickListener {

      val filesDir = applicationContext.filesDir
      val file = File(filesDir, "image.png")

      val inputStream = imageUri.let { contentResolver.openInputStream(it) }
      val outputStream = FileOutputStream(file)
      inputStream!!.copyTo(outputStream)

      val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
      val part = MultipartBody.Part.createFormData("url", file.name, requestBody)

      val title = binding.title.text.toString()
      val description = binding.description.text.toString()

      if (title.isNotEmpty() && description.isNotEmpty()) {

        // Object of content
        val contentRequest = userData?.let { user ->
          ContentRequest(
            title,
            part,
            autor = user.name,
            description,
            user_id = user.id
          )
        }

        // Llamar a la función para enviar los datos al servidor
        if (contentRequest != null) {
          postContent(contentRequest)
        }

      } else {

        toast.toastWarning(this, "Campos incompletos", "Completa los campos y selecciona una imagen")

      }
    }
  }

  private fun postContent(contentRequest: ContentRequest) {

    CoroutineScope(Dispatchers.IO).launch {
      try {
        val apiService = ApiClient.getApiService()

        val titleRequestBody = contentRequest.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val authorRequestBody = contentRequest.autor.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionRequestBody = contentRequest.description.toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdRequestBody = contentRequest.user_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val response = titleRequestBody.let {
          apiService.createContent(
            titleRequestBody,
            contentRequest.url!!,
            authorRequestBody,
            descriptionRequestBody,
            userIdRequestBody
          ).execute()
        }

        withContext(Dispatchers.Main) {
          if (response.isSuccessful) {
            // Solicitud exitosa
            toast.toastSuccess(this@ContentPostActivity, "Mis primeros auxilitos", "Contenido creado exitosamente, se revisará lo más pronto posible!!! 😊😊😊😊😊")
            startActivity(Intent(applicationContext, MainActivity::class.java))
          } else {
            // Manejar error
            toast.toastError(this@ContentPostActivity, "Error", "Por favor, llena todos los campos")
          }
        }
      } catch (e: Exception) {

        // Manejar excepciones
        withContext(Dispatchers.Main) {
          toast.toastError(this@ContentPostActivity, "Error", "e " + e.localizedMessage)
        }

      }
    }
  }

  /**
   *  Get data of User by id login
   */
  private fun getUserProfile(userId: String) {
    val apiService = ApiClient.getApiService()

    val userProfileCall: Call<User> = apiService.getUserProfile(userId)
    userProfileCall.enqueue(object : Callback<User> {
      override fun onResponse(call: Call<User>, response: Response<User>) {
        if (response.isSuccessful) {
          userData = response.body()
          userData?.let {}
        }
      }

      override fun onFailure(call: Call<User>, t: Throwable) {
        toast.toastError(this@ContentPostActivity, "Conexión", "Error de conexión")
      }
    })
  }

}
