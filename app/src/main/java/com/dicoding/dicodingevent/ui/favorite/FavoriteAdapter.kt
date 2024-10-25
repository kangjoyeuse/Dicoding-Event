package com.dicoding.dicodingevent.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.FavoriteEvent
import com.dicoding.dicodingevent.databinding.ItemEventBinding
import com.dicoding.dicodingevent.ui.detail.DetailEventActivity

class FavoriteAdapter : ListAdapter<FavoriteEvent, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    inner class FavoriteViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteEvent) {
            binding.tvEventName.text = favorite.name
            binding.tvEventDate.text = favorite.beginTime // Assuming you have a date field

            Glide.with(itemView.context)
                .load(favorite.imageLogo) // Assuming you have an imageUrl field
                .into(binding.ivEventLogo)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailEventActivity::class.java).apply {
                    putExtra(DetailEventActivity.EXTRA_EVENT, favorite.id)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FavoriteEvent>() {
        override fun areItemsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent) = oldItem == newItem
    }
}