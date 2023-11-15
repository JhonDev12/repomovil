package com.auxilitos.mis_primeros_auxilitos.content.my_content

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auxilitos.mis_primeros_auxilitos.registro.Profile
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.auxilitos.mis_primeros_auxilitos.classesImport.ToastCustom
import com.auxilitos.mis_primeros_auxilitos.client.ApiClient
import com.auxilitos.mis_primeros_auxilitos.content.ContentUpdate
import com.auxilitos.mis_primeros_auxilitos.databinding.ActivityMyContentBinding
import com.auxilitos.mis_primeros_auxilitos.model.response.ContentResponse
import com.auxilitos.mis_primeros_auxilitos.model.response.User
import com.auxilitos.mis_primeros_auxilitos.model.response.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt

class MyContentActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMyContentBinding
  private val toast = ToastCustom()

  var userData: User? = null
  private var userId = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMyContentBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnReturn.setOnClickListener {
      startActivity(Intent(this, Profile::class.java))
    }

    userId = UserManager.getUserId()

    getUserProfile(userId.toString())

    initRecyclerView()

  }// Fin onCreate

  private fun initRecyclerView()
  {
    binding.recyclerMyContent.layoutManager = LinearLayoutManager(this)
    getMyContent()
  }

  private fun getMyContent() {
    CoroutineScope(Dispatchers.IO).launch {
      val apiGetContent = ApiClient.getApiService().getMyContent(userId.toString())

      apiGetContent.enqueue(object : Callback<List<ContentResponse>> { // Cambiado a List<ContentResponse>
        override fun onResponse(
          call: Call<List<ContentResponse>>,
          response: Response<List<ContentResponse>>
        ) {
          if (response.isSuccessful) {
            val contentResponseList = response.body()
            contentResponseList?.let {
              val myContent = MyContentAdapter(contentResponseList) { myContent ->
                onItemSelected(
                  myContent
                )
              }
              binding.recyclerMyContent.adapter = myContent
            }
          }
        }

        override fun onFailure(call: Call<List<ContentResponse>>, t: Throwable) {
          toast.toastError(this@MyContentActivity, "Error 😢😢😢😢😢", "Ups!, ha ocurrido un error")
          Log.e("Error content", t.toString())
        }
      })
    }
  }

  fun onItemSelected(myContentResponse: ContentResponse) {
    val intent = Intent(this, ContentUpdate::class.java)
    intent.putExtra("CONTENIDO_ID", myContentResponse.id)
    startActivity(intent)
  }


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
        toast.toastError(this@MyContentActivity, "Conexión", "Error de conexión")
      }
    })
  }

}