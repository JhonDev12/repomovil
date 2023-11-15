package com.auxilitos.mis_primeros_auxilitos.service

import com.auxilitos.mis_primeros_auxilitos.model.request.ContentRequest
import com.auxilitos.mis_primeros_auxilitos.model.request.LoginRequest
import com.auxilitos.mis_primeros_auxilitos.model.request.RegisterRequest
import com.auxilitos.mis_primeros_auxilitos.model.request.UserRequest
import com.auxilitos.mis_primeros_auxilitos.model.response.ContentResponse
import com.auxilitos.mis_primeros_auxilitos.model.response.LoginResponse
import com.auxilitos.mis_primeros_auxilitos.model.response.RegisterResponse
import com.auxilitos.mis_primeros_auxilitos.model.response.User
import com.auxilitos.mis_primeros_auxilitos.model.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @GET("api/users/{userId}")
    fun getUserProfile(@Path("userId") userId: String): Call<User>

    @GET("api/contenidos")
    fun getContent(): Call<List<ContentResponse>>

    @GET("api/contenidos/{id}")
    fun getOneContent(@Path("id") id: String): Call<ContentResponse>

    @GET("api/my_content/{id}")
    fun getMyContent(@Path("id") id:String): Call<List<ContentResponse>>

    @Multipart
    @POST("api/contenidos")
    fun createContent(
        @Part("title") title: RequestBody,
        @Part url: MultipartBody.Part,
        @Part("autor") autor: RequestBody,
        @Part("description") description: RequestBody,
        @Part("user_id") userId: RequestBody
    ): Call<ContentResponse>

    @Multipart
    @POST("api/update_content/{id}")
    fun updateContent(
        @Path("id") id: String, // Capturar la ID como un parámetro de ruta
        @Part("title") title: RequestBody,
        @Part url: MultipartBody.Part?,
        @Part("autor") autor: RequestBody,
        @Part("description") description: RequestBody,
        @Part("user_id") userId: RequestBody
    ): Call<ContentResponse>

    @DELETE("api/contenidos/{id}")
    fun deleteContent(@Path("id") id: String): Call<Void>

    @POST("api/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/register/")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @PUT("api/users/{userId}")
    fun updateProfile(@Body userRequest: UserRequest, @Path("userId") userId: String): Call<UserResponse>

    @GET("api/logout/")
    fun logoutUser(): Call<RegisterResponse>?
    //fun getPublicaciones(): Call<List<Publicaciones?>?>?

    @GET("api/delete/")
    fun deleteUser(): Call<User>


}