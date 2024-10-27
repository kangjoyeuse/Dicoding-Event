package com.dicoding.dicodingevent.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.AppDatabase
import com.dicoding.dicodingevent.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel
    private val favoriteAdapter = FavoriteAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val viewModelFactory = FavoriteViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]

        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFavorites.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeFavorites() {
        viewModel.allFavorites.observe(viewLifecycleOwner) { favorites ->
            favoriteAdapter.submitList(favorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}