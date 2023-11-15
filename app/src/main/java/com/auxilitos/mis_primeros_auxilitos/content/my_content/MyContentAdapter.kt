package com.auxilitos.mis_primeros_auxilitos.content.my_content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auxilitos.mis_primeros_auxilitos.R
import com.auxilitos.mis_primeros_auxilitos.model.response.ContentResponse

class MyContentAdapter(private val contentList: List<ContentResponse>, private val onClickListener:(ContentResponse) -> Unit) : RecyclerView.Adapter<MyContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyContentViewHolder(layoutInflater.inflate(R.layout.my_item_content, parent, false))
    }

    override fun getItemCount(): Int =  contentList.size

    override fun onBindViewHolder(holder: MyContentViewHolder, position: Int) {
        val item = contentList[position]
        holder.render(item, onClickListener)
    }

}