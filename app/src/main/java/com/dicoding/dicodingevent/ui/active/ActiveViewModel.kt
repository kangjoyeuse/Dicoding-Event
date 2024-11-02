package com.dicoding.dicodingevent.ui.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.data.ListEventsItem
import com.dicoding.dicodingevent.data.Repository
import kotlinx.coroutines.launch

class ActiveViewModel(private val repository: Repository) : ViewModel() {
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
                val activeEvents = repository.getActiveEvents(limit = 20) // Menggunakan limit 20 sebagai contoh
                _events.value = activeEvents
            } catch (e: Exception) {
                _error.value = "Tidak dapat terhubung dengan server: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}