package com.auxilitos.mis_primeros_auxilitos.model.response

data class RegisterResponse (
    val id:              Int,
    val name:            String,
    val email:           String,
    val genero:          String,
    val fechaNacimiento: String,
    val password:        String,
    val passwordconfirm: String,
)