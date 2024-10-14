package com.dicoding.dicodingevent.ui.past

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActiveViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is past Fragment"
    }
    val text: LiveData<String> = _text
}