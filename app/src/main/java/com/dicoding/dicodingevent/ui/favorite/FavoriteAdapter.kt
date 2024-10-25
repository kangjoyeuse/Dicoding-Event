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
import com.dicoding.dicodingevent.ui.detail.FavoriteEventDetailActivity
import java.text.SimpleDateFormat
import java.util.Locale

//import com.dicoding.dicodingevent.databinding.ItemFavoriteBinding

class FavoriteAdapter : ListAdapter<FavoriteEvent, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class FavoriteViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEvent: FavoriteEvent) {
            binding.apply {
                tvEventName.text = favoriteEvent.name
                tvEventDate.text = formatDate(favoriteEvent.beginTime, favoriteEvent.endTime)
                Glide.with(binding.root.context)
                    .load(favoriteEvent.imageLogo)
                    .into(binding.ivEventLogo)

                root.setOnClickListener {
                    val intent = Intent(root.context, FavoriteEventDetailActivity::class.java).apply {
                        putExtra(FavoriteEventDetailActivity.EXTRA_FAVORITE_EVENT, favoriteEvent.id.toString())
                    }
                    root.context.startActivity(intent)
                }
            }
        }

        private fun formatDate(beginTime: String, endTime: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

            try {
                val beginDate = inputFormat.parse(beginTime)
                val endDate = inputFormat.parse(endTime)

                return if (beginDate != null && endDate != null) {
                    "${outputFormat.format(beginDate)} - ${outputFormat.format(endDate)}"
                } else {
                    "Invalid date"
                }
            } catch (e: Exception) {
                return "Invalid date format"
            }
        }
    }




    class DiffCallback : DiffUtil.ItemCallback<FavoriteEvent>() {
        override fun areItemsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent) =
            oldItem == newItem
    }
}