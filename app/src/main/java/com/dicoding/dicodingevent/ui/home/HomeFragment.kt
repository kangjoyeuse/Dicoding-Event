package com.dicoding.dicodingevent.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.api.ApiConfig
import com.dicoding.dicodingevent.data.AppDatabase
//import com.dicoding.dicodingevent.data.ListEventsItem
import com.dicoding.dicodingevent.data.RepositoryImpl
import com.dicoding.dicodingevent.data.ThemePreferences
import com.dicoding.dicodingevent.data.dataStore
import com.dicoding.dicodingevent.databinding.FragmentHomeBinding
import com.dicoding.dicodingevent.ui.EventAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Note To-do
// Ini dikarenakan terdapat satu indikator loading untuk menghandle 2 request ada ke api ( upcoming dan finished ). Silahkan diperbaiki dahulu ya, kamu bisa gunakan indikator loading tersendiri untuk tiap section.
// Fix: buat 2 loading indicator

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var themePreferences: ThemePreferences
    private lateinit var repository: RepositoryImpl


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themePreferences = ThemePreferences.getInstance(requireContext().dataStore)
        // Inisialisasi database dan dao
        val database = AppDatabase.getDatabase(requireContext())
        val favoriteEventDao = database.favoriteEventDao()

        // Inisialisasi repository
        repository = RepositoryImpl(ApiConfig.apiService, favoriteEventDao /* pass FavoriteEventDao instance here */)

        setupTheme()
        setupActiveEvents()
        setupPastEvents()
    }

    private fun setupTheme() {
        viewLifecycleOwner.lifecycleScope.launch {
            val isDarkModeActive = themePreferences.getThemeSetting().first()
            updateTheme(isDarkModeActive)
        }
    }

    private fun updateTheme(isDarkModeActive: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun setupActiveEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                showLoadingUpcoming(true)
                val activeEvents = repository.getActiveEvents(5)
                if (isAdded && _binding != null) {
                    val activeAdapter = EventAdapter()
                    activeAdapter.setEvents(activeEvents)

                    binding.rvActiveEvents.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = activeAdapter
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Gagal meresponse active events, error: ${e.message}")
            } finally {
                if (isAdded) {
                    showLoadingUpcoming(false)
                }
            }
        }
    }

    private fun setupPastEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                showLoadingFinished(true)
                val pastEvents = repository.getPastEvents(5)
                if (isAdded && _binding != null) {
                    val pastAdapter = EventAdapter()
                    pastAdapter.setEvents(pastEvents)

                    binding.rvPastEvents.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = pastAdapter
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Gagal meresponse past events, error: ${e.message}")
            } finally {
                if (isAdded) {
                    showLoadingFinished(false)
                }
            }
        }
    }

    private fun showLoadingUpcoming(isLoading: Boolean) {
        _binding?.progressBarUpcming?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingFinished(isLoading: Boolean) {
        _binding?.progressBarFinihed?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

//    private suspend fun getActiveEvents(): List<ListEventsItem> {
//        val response = ApiConfig.apiService.getActiveEvents()
//        return if (!response.error) {
//            response.listEvents
//        } else {
//            emptyList()
//        }
//    }

//    private suspend fun getPastEvents(): List<ListEventsItem> {
//        val response = ApiConfig.apiService.getPastEvents(0, 5)
//        return if (!response.error) {
//            response.listEvents
//        } else {
//            emptyList()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}