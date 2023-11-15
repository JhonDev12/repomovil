package com.auxilitos.mis_primeros_auxilitos.model.response

data class ContentResponse(
    val id: String,
    val title: String,
    val slug: String,
    val url: String,
    val autor: String,
    val description: String,
    //val verified: Boolean,
    val user_id: String,
    val created_at: String,
    val updated_at: String
)
