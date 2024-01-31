package com.example.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search.databinding.ItemBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchAdapter(val items: MutableList<Document>): RecyclerView.Adapter<SearchAdapter.Holder>() {

    interface ItemClick {
        fun onClick (view: View, position: Int)
    }

    private var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

//        holder.itemView.setOnClickListener{
//            itemClick?.onClick(it, position)
//        }

        //Glide를 통한 Image URI 형식 받기
        Glide.with(holder.itemView.context)
            .load(items[position].thumbnailUrl)
            .into(holder.itemImage)

        holder.itemTitle.text = items[position].siteName

        // 시간 포맷 설정
        holder.itemDate.text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(
            OffsetDateTime.parse(items[position].datetime))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){
        val itemImage = binding.searchImage
        val itemTitle = binding.searchTitle
        val itemDate = binding.searchDate
    }
}