package com.dicoding.dicodingevent.ui.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.api.ApiConfig
import com.dicoding.dicodingevent.data.ListEventsItem
import kotlinx.coroutines.launch

class ActiveViewModel : ViewModel() {
    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        getActiveEvents()
    }

    private fun getActiveEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.apiService.getEvents(1) // 1 for active events
                if (!response.error) {
                    _events.value = response.listEvents
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = "Failed to fetch events: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}