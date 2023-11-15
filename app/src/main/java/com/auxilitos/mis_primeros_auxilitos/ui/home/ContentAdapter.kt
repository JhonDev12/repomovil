package com.auxilitos.mis_primeros_auxilitos.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auxilitos.mis_primeros_auxilitos.R
import com.auxilitos.mis_primeros_auxilitos.model.response.ContentResponse

class ContentAdapter(private val contentList: List<ContentResponse>) : RecyclerView.Adapter<ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContentViewHolder(layoutInflater.inflate(R.layout.item_content, parent, false))
    }

    override fun getItemCount(): Int =  contentList.size

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val item = contentList[position]
        holder.render(item)
    }

}