package com.auxilitos.mis_primeros_auxilitos.model.request

import okhttp3.MultipartBody

data class ContentRequest(
  val title: String,
  val url: MultipartBody.Part?,
  val autor: String, //
  val description: String,
  val user_id: Int //
)