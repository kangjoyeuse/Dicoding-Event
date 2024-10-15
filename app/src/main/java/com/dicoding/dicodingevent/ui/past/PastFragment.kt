package com.dicoding.dicodingevent.ui.past

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentPastBinding
import com.dicoding.dicodingevent.ui.EventAdapter

class PastFragment : Fragment() {

    private var _binding: FragmentPastBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PastViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PastViewModel::class.java)
        adapter = EventAdapter()

        binding.rvPastEvents.layoutManager = LinearLayoutManager(context)
        binding.rvPastEvents.adapter = adapter

        viewModel.events.observe(viewLifecycleOwner) { events ->
            adapter.setEvents(events)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            // Handle error, e.g., show a Toast or Snackbar
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}