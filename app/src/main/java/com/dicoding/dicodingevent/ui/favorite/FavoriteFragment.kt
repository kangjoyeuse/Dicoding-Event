package com.dicoding.dicodingevent.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.getAllFavorites().asLiveData().observe(viewLifecycleOwner) { favorites ->
            favoriteAdapter.submitList(favorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}