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
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var themePreferences: ThemePreferences

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

        // Observe theme settings
        viewLifecycleOwner.lifecycleScope.launch {
            themePreferences.getThemeSetting().collect { isDarkModeActive ->
                updateTheme(isDarkModeActive)
                binding.switchTheme.isChecked = isDarkModeActive
            }
        }

        // Set listener for theme switch
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                themePreferences.saveThemeSetting(isChecked)
            }
        }
    }

    private fun updateTheme(isDarkModeActive: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}