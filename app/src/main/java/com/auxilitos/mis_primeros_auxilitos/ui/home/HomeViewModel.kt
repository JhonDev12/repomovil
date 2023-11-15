package com.auxilitos.mis_primeros_auxilitos.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auxilitos.mis_primeros_auxilitos.client.ApiClient
import com.auxilitos.mis_primeros_auxilitos.model.response.ContentResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    init {
        getAllContent()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Diviertete aprendiendo!!!"
    }
    val text: LiveData<String> = _text

    private val _contentData = MutableLiveData<List<ContentResponse>>()
    val contentData: LiveData<List<ContentResponse>> get() = _contentData

    private fun getAllContent() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiGetContent = ApiClient.getApiService().getContent()

            apiGetContent.enqueue(object : Callback<List<ContentResponse>> { // Cambiado a List<ContentResponse>
                override fun onResponse(
                    call: Call<List<ContentResponse>>,
                    response: Response<List<ContentResponse>>
                ) {
                    if (response.isSuccessful) {
                        val contentResponseList = response.body()
                        contentResponseList?.let {
                            _contentData.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContentResponse>>, t: Throwable) {
                    Log.e("Error content", t.toString())
                }
            })
        }
    }


}