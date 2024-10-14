package com.dicoding.dicodingevent.ui.past

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.databinding.FragmentActiveBinding

class PastFragment : Fragment() {

    private var _binding: FragmentActiveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val activeViewModel =
            ViewModelProvider(this).get(ActiveViewModel::class.java)

        _binding = FragmentActiveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textActive
        activeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}