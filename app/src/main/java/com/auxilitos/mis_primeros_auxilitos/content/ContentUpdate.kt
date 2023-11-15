package com.auxilitos.mis_primeros_auxilitos.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auxilitos.mis_primeros_auxilitos.R
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.auxilitos.mis_primeros_auxilitos.classesImport.ToastCustom
import com.auxilitos.mis_primeros_auxilitos.client.ApiClient
import com.auxilitos.mis_primeros_auxilitos.content.my_content.MyContentActivity
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityContentUpdateBinding
import com.auxilitos.mis_primeros_auxilitos.model.request.ContentRequest
import com.auxilitos.mis_primeros_auxilitos.model.response.ContentResponse
import com.auxilitos.mis_primeros_auxilitos.model.response.User
import com.auxilitos.mis_primeros_auxilitos.model.response.UserManager
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
import java.lang.Integer.parseInt

class ContentUpdate : AppCompatActivity() {

  private lateinit var binding: ActivityContentUpdateBinding
  private val toast = ToastCustom()

  private var contentId = 0

  private var myContentToUpdate: ContentResponse? = null

  private var imageUriToUpdate: Uri? = null

  private var userData: User? = null
  private var userId = 0

  private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    uri?.let {
      imageUriToUpdate = it
      binding.imageUrlUpdate.setImageURI(it)

      val borderDrawable = ContextCompat.getDrawable(this, R.drawable.bordernavigation) // Obtén el fondo redondeado desde los recursos
      binding.imageUrlUpdate.background = borderDrawable // Establece el fondo redondeado en el ImageButton

      val requestOptions = RequestOptions()
        .placeholder(R.drawable.image_preview) // Imagen de placeholder mientras se carga la imagen
        .error(R.drawable.error) // Imagen de error si la carga falla
        .diskCacheStrategy(DiskCacheStrategy.NONE) // Evita el almacenamiento en caché de la imagen para que se vuelva a cargar cada vez

      Glide.with(this)
        .load(imageUriToUpdate)
        .apply(requestOptions)
        .centerCrop() // Escala la imagen para llenar el área del ImageButton mientras mantiene las proporciones y corta el exceso
        .into(binding.imageUrlUpdate)

    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityContentUpdateBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnChooseImage.setOnClickListener {
      contract.launch("image/*")
    }

    getIdMyContent()

    getMyContentById(contentId)

    binding.btnReturn.setOnClickListener {
      startActivity(Intent(this, MyContentActivity::class.java))
    }

    sendContentToUpdate()

    binding.btnDeleteContent.setOnClickListener {
      deleteContent(contentId.toString())
      startActivity(Intent(this@ContentUpdate, MyContentActivity::class.java))
    }

  }

  private fun deleteContent(idContent: String) {
    deleteContentById(idContent)
  }

  private fun getIdMyContent()
  {
    val intent = intent
    if (intent != null && intent.hasExtra("CONTENIDO_ID")) {
      contentId = intent.getStringExtra("CONTENIDO_ID")?.let { parseInt(it) }!!
    } else {
      toast.toastError(this, "Error", "Ups!, ha ocurrido un error inesperado, intentalo de nuevo o más tarde")
    }
  }

  @SuppressLint("Recycle")
  private fun sendContentToUpdate() {

    userId = UserManager.getUserId()

    getUserProfile(userId.toString())

    binding.btnUploadContent.setOnClickListener {

      val part: MultipartBody.Part? = imageUriToUpdate?.let { uri ->
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(applicationContext.filesDir, "image.png")
        inputStream?.use { input ->
          FileOutputStream(file).use { output ->
            input.copyTo(output)
          }
        }
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("url", file.name, requestBody)
      }


      val title = binding.titleUpdate.text.toString()

      val description = binding.descriptionUpdate.text.toString()

      if (title.isNotEmpty() && description.isNotEmpty()) {

        // Object of content
        val contentRequest = userData?.let { user ->
          ContentRequest(
            title,
            part,
            autor = user.name,
            description,
            user_id = myContentToUpdate?.user_id?.toIntOrNull() ?: 0
          )
        }

        // Llamar a la función para enviar los datos al servidor
        if (contentRequest != null) {
          Log.e("CONTENT", "$contentRequest")
          updateContent(contentRequest)
        }

      } else {

        toast.toastWarning(this, "Campos incompletos", "Completa los campos y selecciona una imagen")

      }
    }
  }

  private fun updateContent(contentRequest: ContentRequest) {

    CoroutineScope(Dispatchers.IO).launch {
      try {
        val apiService = ApiClient.getApiService()

        val titleRequestBody = contentRequest.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val authorRequestBody = contentRequest.autor.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionRequestBody = contentRequest.description.toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdRequestBody = contentRequest.user_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val response = titleRequestBody.let {
          apiService.updateContent(
            contentId.toString(),
            titleRequestBody,
            contentRequest.url,
            authorRequestBody,
            descriptionRequestBody,
            userIdRequestBody
          ).execute()
        }

        withContext(Dispatchers.Main) {
          if (response.isSuccessful) {
            // Solicitud exitosa
            toast.toastSuccess(this@ContentUpdate, "Mis primeros auxilitos", "Contenido actualizado exitosamente, se revisará lo más pronto posible!!! 😊😊😊😊😊")
            startActivity(Intent(applicationContext, MyContentActivity::class.java))
          } else {
            // Manejar error
            toast.toastError(this@ContentUpdate, "Error", "Por favor, llena todos los campos")
          }
        }
      } catch (e: Exception) {

        // Manejar excepciones
        withContext(Dispatchers.Main) {
          toast.toastError(this@ContentUpdate, "Error", "e " + e.localizedMessage)
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
        toast.toastError(this@ContentUpdate, "Conexión", "Error de conexión")
      }
    })
  }

  private fun getMyContentById(contentId: Int)
  {
    val apiService = ApiClient.getApiService()

    val myContentCall: Call<ContentResponse> = apiService.getOneContent(contentId.toString())
    myContentCall.enqueue(object : Callback<ContentResponse> {
      @SuppressLint("SetTextI18n")
      override fun onResponse(call: Call<ContentResponse>, response: Response<ContentResponse>) {
        if (response.isSuccessful) {
          val myContent = response.body()
          myContentToUpdate = myContent
          myContent?.let {
            findViewById<TextView>(R.id.title_update).text          = it.title
            findViewById<TextView>(R.id.description_update).text    = it.description
            Glide.with(this@ContentUpdate)
              .load(ApiClient.baseUrl + it.url)
              .placeholder(R.drawable.logo)
              .error(R.drawable.logo)
              .into(binding.imageUrlUpdate)
          }
        }
      }

      override fun onFailure(call: Call<ContentResponse>, t: Throwable) {
        toast.toastError(this@ContentUpdate, "Conexión", "Error de conexión")
      }
    })
  }

  private fun deleteContentById(contentId: String) {
    val apiService = ApiClient.getApiService()

    apiService.deleteContent(contentId).enqueue(object : Callback<Void> {
      override fun onResponse(call: Call<Void>, response: Response<Void>) {
        if (response.isSuccessful) {
          toast.toastSuccess(this@ContentUpdate, "Contenido", "El contenido fue eliminado exitosamente!!!")
        } else {
          toast.toastError(this@ContentUpdate, "Contenido", "Ups, ha ocurrido un error inesperado")
        }
      }

      override fun onFailure(call: Call<Void>, t: Throwable) {
        toast.toastError(this@ContentUpdate, "Error", "Error de conexión")
      }
    })
  }

}