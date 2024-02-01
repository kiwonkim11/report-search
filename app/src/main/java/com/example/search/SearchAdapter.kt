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

class SearchAdapter(private val mContext: Context): RecyclerView.Adapter<SearchAdapter.Holder>() {

    var items = ArrayList<SearchItem>()

    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        //Glide를 통한 Image URI 형식 받기
        Glide.with(holder.itemView.context)
            .load(items[position].url)
            .into(holder.itemImage)

        holder.itemTitle.text = items[position].title

        // 시간 포맷 설정
        holder.itemDate.text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(
            OffsetDateTime.parse(items[position].dateTime))

        holder.itemLike.visibility = if(items[position].isLike) View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val itemImage = binding.searchImage
        val itemTitle = binding.searchTitle
        val itemDate = binding.searchDate
        val itemLike = binding.searchLike
        val clItem: ConstraintLayout = binding.constRecyclerview

        init {
            itemLike.visibility = View.GONE
            itemImage.setOnClickListener(this)
            clItem.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            item.isLike = !item.isLike

            if (item.isLike) {
                (mContext as MainActivity).addLikedItems(item)
            } else {
                (mContext as MainActivity).removeLikedItems(item)
            }

            notifyItemChanged(position)
        }
    }
}