package com.dicoding.dicodingevent.ui.active

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentActiveBinding
import com.dicoding.dicodingevent.ui.EventAdapter

class ActiveFragment : Fragment() {

    private var _binding: FragmentActiveBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ActiveViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ActiveViewModel::class.java)
        adapter = EventAdapter()

        binding.rvActiveEvents.layoutManager = LinearLayoutManager(context)
        binding.rvActiveEvents.adapter = adapter

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