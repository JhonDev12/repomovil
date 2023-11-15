package com.auxilitos.mis_primeros_auxilitos.model.request

data class UserRequest(
  val name: String,
  val email: String,
  val genero: String,
  val fechaNacimiento: String,
  val description: String?
)