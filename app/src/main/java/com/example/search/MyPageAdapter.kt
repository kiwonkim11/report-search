package com.example.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search.databinding.ItemBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class MyPageAdapter(var mContext: Context) : RecyclerView.Adapter<MyPageAdapter.Holder>() {

    var items = ArrayList<SearchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun onBindViewHolder(holder: MyPageAdapter.Holder, position: Int) {

        //Glide를 통한 Image URI 형식 받기
        Glide.with(mContext)
            .load(items[position].url)
            .into(holder.itemImage)

        holder.itemTitle.text = items[position].title

        // 시간 포맷 설정
        holder.itemDate.text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(
            OffsetDateTime.parse(items[position].dateTime))

        holder.itemLike.visibility = View.GONE
    }
    override fun getItemCount(): Int {
        return items.size
    }
    inner class Holder (binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemImage = binding.searchImage
        val itemTitle = binding.searchTitle
        val itemDate = binding.searchDate
        val itemLike = binding.searchLike
        val clItem: ConstraintLayout = binding.constRecyclerview

        init {
            itemLike.visibility = View.GONE

            clItem.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}