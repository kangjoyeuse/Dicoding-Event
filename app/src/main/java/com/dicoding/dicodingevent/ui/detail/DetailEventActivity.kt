package com.dicoding.dicodingevent.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.data.AppDatabase
import com.dicoding.dicodingevent.data.FavoriteEvent
import com.dicoding.dicodingevent.data.ListEventsItem
import com.dicoding.dicodingevent.databinding.ActivityDetailEventBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
//    Favorite Feature
    private lateinit var database: AppDatabase
    private var isFavorite = false
    private var currentEvent: ListEventsItem? = null

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Define Database
        database = AppDatabase.getDatabase(this)


//        val event = intent.getParcelableExtra<ListEventsItem>(EXTRA_EVENT)
//        event?.let { displayEventDetails(it) }

        currentEvent = intent.getParcelableExtra(EXTRA_EVENT)
        currentEvent?.let { event ->
            displayEventDetails(event)
            checkFavoriteStatus(event.id)
        }

        binding.btnFavorite.setOnClickListener {
            toggleFavorite()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayEventDetails(event: ListEventsItem) {
        with(binding) {
            tvEventName.text = event.name
            tvEventOrganizer.text = getString(R.string.penyelengra_acara, event.ownerName)
            tvEventTime.text = getString(
                R.string.waktu_event,
                formatDate(event.beginTime),
                formatDate(event.endTime)
            )
            tvEventQuota.text = "Kuota Event yang tersedia: ${event.quota - event.registrants}"
            tvEventDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

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
                inputFormat.timeZone = TimeZone.getTimeZone("GMT+7:00")
                val date = inputFormat.parse(dateString)
                return date?.let { outputFormat.format(it) } ?: "Data tidak benar"
            } catch (e: Exception) {
                // Continue to the next format if parsing fails
            }
        }
        return "Foramt data yang dimasukan tidak benar"
    }

    private fun checkFavoriteStatus(eventId: Int) {
        lifecycleScope.launch {
            val favoriteEvent = database.favoriteEventDao().getFavoriteById(eventId.toString())
            isFavorite = favoriteEvent != null
            updateFavoriteButtonUI()
        }
    }

    private fun toggleFavorite() {
        currentEvent?.let { event ->
            lifecycleScope.launch {
                if (isFavorite) {
                    database.favoriteEventDao().deleteFavorite(
                        FavoriteEvent(
                        id = event.id,
                        name = event.name,
                        ownerName = event.ownerName,
                        beginTime = event.beginTime,
                        endTime = event.endTime,
                        quota = event.quota,
                        registrants = event.registrants,
                        description = event.description,
                        imageLogo = event.imageLogo,
                        link = event.link
                    )
                    )
                } else {
                    database.favoriteEventDao().insertFavorite(FavoriteEvent(
                        id = event.id,
                        name = event.name,
                        ownerName = event.ownerName,
                        beginTime = event.beginTime,
                        endTime = event.endTime,
                        quota = event.quota,
                        registrants = event.registrants,
                        description = event.description,
                        imageLogo = event.imageLogo,
                        link = event.link
                    ))
                }
                isFavorite = !isFavorite
                updateFavoriteButtonUI()
            }
        }
    }

    private fun updateFavoriteButtonUI() {
        binding.btnFavorite.setImageResource(
            if (isFavorite) R.drawable.hearth_fill_solid
            else R.drawable.hearth_border
        )
    }
}