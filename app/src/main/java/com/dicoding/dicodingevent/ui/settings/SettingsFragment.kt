package com.dicoding.dicodingevent.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.dicodingevent.data.ThemePreferences
import com.dicoding.dicodingevent.data.dataStore
import com.dicoding.dicodingevent.databinding.FragmentSettingsBinding
import com.dicoding.dicodingevent.manager.ReminderManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var themePreferences: ThemePreferences
    private lateinit var reminderManager: ReminderManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themePreferences = ThemePreferences.getInstance(requireContext().dataStore)
        reminderManager = ReminderManager(requireContext())

        // Initialize switch states
        viewLifecycleOwner.lifecycleScope.launch {
            // Set theme switch state
            val isDarkModeActive = themePreferences.getThemeSetting().first()
            binding.switchTheme.isChecked = isDarkModeActive
            updateTheme(isDarkModeActive)

            // Set reminder switch state
            val isReminderEnabled = themePreferences.getReminderSetting().first()
            binding.switchReminder.isChecked = isReminderEnabled
        }

        // Set listener for theme switch
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                themePreferences.saveThemeSetting(isChecked)
                updateTheme(isChecked)
            }
        }

        // Set listener for reminder switch
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            setReminderEnabled(isChecked)
        }
    }

    private fun updateTheme(isDarkModeActive: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun setReminderEnabled(isEnabled: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            themePreferences.saveReminderSetting(isEnabled)
            reminderManager.scheduleReminder(isEnabled)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}