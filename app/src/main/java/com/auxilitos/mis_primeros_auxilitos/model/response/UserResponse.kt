package com.auxilitos.mis_primeros_auxilitos.model.response

data class UserResponse(
    val id: String,
    val profile_photo_path: String,
    val name: String,
    val email: String,
    val email_verified_at: String,
    val genero: String,
    val fechaNacimiento: String,
    val two_factor_confirmed_at: String,
    val description: String,
    val observacion: String,
    val created_at: String,
    val updated_at: String,
    val avatar: String,
    val external_id: String,
    val external_auth: String,
    val profile_photo_url: String,
)
