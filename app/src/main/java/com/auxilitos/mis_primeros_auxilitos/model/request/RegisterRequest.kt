package com.auxilitos.mis_primeros_auxilitos.model.request

data class RegisterRequest (
    val name:            String,
    val email:           String,
    val genero:          String,
    val fechaNacimiento: String,
    val password:        String,
    val passwordconfirm: String,
)