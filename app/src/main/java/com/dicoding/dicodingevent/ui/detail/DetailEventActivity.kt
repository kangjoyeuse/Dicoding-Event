package com.dicoding.dicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
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
            tvEventName.text = event.name
            tvEventOrganizer.text = "Organizer: ${event.ownerName}"
            tvEventTime.text = "Time: ${formatDate(event.beginTime)} - ${formatDate(event.endTime)}"
            tvEventQuota.text = "Available Slots: ${event.quota - event.registrants}"
            tvEventDescription.text = HtmlCompat.fromHtml(event.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)

            Glide.with(this@DetailEventActivity)
                .load(event.imageLogo)
                .into(ivEventLogo)

            btnOpenLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
        }
    }

    private fun formatDate(dateString: String): String {
        val possibleFormats = arrayOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd HH:mm:ss"
        )
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        for (format in possibleFormats) {
            try {
                val inputFormat = SimpleDateFormat(format, Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date = inputFormat.parse(dateString)
                return date?.let { outputFormat.format(it) } ?: "Invalid date"
            } catch (e: Exception) {
                // Continue to the next format if parsing fails
            }
        }
        return "Invalid date format"
    }
}