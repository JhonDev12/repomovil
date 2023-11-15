package com.auxilitos.mis_primeros_auxilitos.content.my_content

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.auxilitos.mis_primeros_auxilitos.R
import com.auxilitos.mis_primeros_auxilitos.client.ApiClient
import com.auxilitos.mis_primeros_auxilitos.content.ContentUpdate
import com.auxilitos.mis_primeros_auxilitos.databinding.MyItemContentBinding
import com.auxilitos.mis_primeros_auxilitos.model.response.ContentResponse
import com.bumptech.glide.Glide

class MyContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: MyItemContentBinding = MyItemContentBinding.bind(view)

    fun render(contentModel: ContentResponse, onClickListener:(ContentResponse) -> Unit) {
        binding.title.text = contentModel.title
        binding.author.text = contentModel.autor


        itemView.setOnClickListener { onClickListener(contentModel) }

        // Verificar si la URL es para un video o una imagen
        if (contentModel.url.matches(Regex(".+\\.(mp4|avi|mov|mkv|wmv|flv|webm)$", RegexOption.IGNORE_CASE))) {
            // Si es un video, usar VideoView
            val videoUri = Uri.parse(ApiClient.baseUrl + contentModel.url)
            binding.videoView.visibility = View.VISIBLE
            binding.imageView.visibility = View.GONE

            binding.videoView.setVideoURI(videoUri)
            binding.videoView.setOnPreparedListener { mediaPlayer ->
                // El video está preparado para reproducirse
                mediaPlayer.start() // Comenzar la reproducción automáticamente
            }

            // Manejar clics en el VideoView para alternar la reproducción/pausa
            binding.videoView.setOnClickListener {
                if (binding.videoView.isPlaying) {
                    binding.videoView.pause() // Pausar el video si se está reproduciendo
                } else {
                    binding.videoView.start() // Reanudar la reproducción si está pausado
                }
            }

            // Manejar clics en otro lugar para detener la reproducción
            binding.myItemLayout.setOnClickListener {
                if (binding.videoView.isPlaying) {
                    binding.videoView.stopPlayback() // Detener la reproducción si se está reproduciendo
                }
            }
        } else {
            // Si es una imagen, usar Glide para cargar la imagen en el ImageView
            binding.imageView.visibility = View.VISIBLE
            binding.videoView.visibility = View.GONE // Ocultar VideoView si es una imagen
            Glide.with(itemView.context)
                .load(ApiClient.baseUrl + contentModel.url)
                .placeholder(R.drawable.logo)
                .error(R.drawable.error)
                .into(binding.imageView)
        }

    }
}
