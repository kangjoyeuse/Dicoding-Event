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
import com.dicoding.dicodingevent.data.ListEventsItem
import com.dicoding.dicodingevent.data.ThemePreferences
import com.dicoding.dicodingevent.data.dataStore
import com.dicoding.dicodingevent.databinding.FragmentHomeBinding
import com.dicoding.dicodingevent.ui.EventAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var themePreferences: ThemePreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themePreferences = ThemePreferences.getInstance(requireContext().dataStore)

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
                showLoading(true)
                val activeEvents = getActiveEvents().take(5)
                if (isAdded && _binding != null) {  // Check if Fragment is still attached and binding is not null
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
                if (isAdded) {  // Check if Fragment is still attached
                    showLoading(false)
                }
            }
        }
    }

    private fun setupPastEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                showLoading(true)
                val pastEvents = getPastEvents().take(5)
                if (isAdded && _binding != null) {  // Check if Fragment is still attached and binding is not null
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
                if (isAdded) {  // Check if Fragment is still attached
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        _binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private suspend fun getActiveEvents(): List<ListEventsItem> {
        val response = ApiConfig.apiService.getActiveEvents()
        return if (!response.error) {
            response.listEvents
        } else {
            emptyList()
        }
    }

    private suspend fun getPastEvents(): List<ListEventsItem> {
        val response = ApiConfig.apiService.getPastEvents(0, 5)
        return if (!response.error) {
            response.listEvents
        } else {
            emptyList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}