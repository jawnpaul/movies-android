package com.example.moviesapp.movies.discover

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviesapp.databinding.FragmentDiscoverBinding
import com.example.moviesapp.model.MoviePresentation
import com.example.moviesapp.movies.adapter.MoviesAdapter
import com.example.moviesapp.utility.initRecyclerViewUsingGridLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private var SPAN_COUNT = 2
    private val discoverViewModel: DiscoverViewModel by activityViewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentOrientation = resources.configuration.orientation
        SPAN_COUNT = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            4
        } else {
            2
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDiscoverBinding.inflate(layoutInflater, container, false)
        binding.viewModel = discoverViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        val viewModel = binding.viewModel
        if (viewModel != null) {
            moviesAdapter =
                MoviesAdapter { movie -> openMovieDetails(movie) }
            context?.let {
                binding.discoverMovieRecyclerView.initRecyclerViewUsingGridLayout(it, SPAN_COUNT)
            }
            binding.discoverMovieRecyclerView.adapter = moviesAdapter
        }
    }

    private fun openMovieDetails(movie: MoviePresentation) {
        // navigate to detail
    }
}
