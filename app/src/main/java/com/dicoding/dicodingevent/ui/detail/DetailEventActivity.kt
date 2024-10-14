package com.dicoding.dicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.ListEventsItem
import com.dicoding.dicodingevent.databinding.ActivityDetailEventBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event = intent.getParcelableExtra<ListEventsItem>(EXTRA_EVENT)
        event?.let { displayEventDetails(it) }
    }

    private fun displayEventDetails(event: ListEventsItem) {
        with(binding) {
            Glide.with(this@DetailEventActivity)
                .load(event.imageLogo)
                .into(ivEventLogo)

            tvEventName.text = event.name
            tvEventOrganizer.text = "Organizer: ${event.ownerName}"
            tvEventTime.text = "Time: ${formatDate(event.beginTime)}"
            tvEventQuota.text = "Available Slots: ${event.quota - event.registrants}"
            tvEventDescription.text = event.description

            btnOpenLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }
}