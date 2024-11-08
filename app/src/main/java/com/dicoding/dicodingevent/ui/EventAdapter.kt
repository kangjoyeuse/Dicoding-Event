package com.dicoding.dicodingevent.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.ListEventsItem
import com.dicoding.dicodingevent.databinding.ItemEventBinding
import com.dicoding.dicodingevent.ui.detail.DetailEventActivity
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val events = mutableListOf<ListEventsItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(newEvents: List<ListEventsItem>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount() = events.size

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val event = events[position]
                    val intent = Intent(itemView.context, DetailEventActivity::class.java).apply {
                        putExtra(DetailEventActivity.EXTRA_EVENT, event)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(event: ListEventsItem) {
            binding.tvEventName.text = event.name
            binding.tvEventDate.text = formatDate(event.beginTime, event.endTime)
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.ivEventLogo)
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
}