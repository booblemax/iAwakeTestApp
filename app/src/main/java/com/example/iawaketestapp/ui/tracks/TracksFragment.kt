package com.example.iawaketestapp.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iawaketestapp.databinding.TracksFragmentBinding
import com.example.iawaketestapp.domain.Resource
import com.example.iawaketestapp.domain.models.TrackModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltAndroidApp
class TracksFragment : Fragment() {

    private val viewModel by viewModels<TracksViewModel>()
    private var binding: TracksFragmentBinding? = null
    private val adapter = TracksAdapter {
        //todo open list of tracks
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = TracksFragmentBinding.inflate(inflater, container, false)
        .apply {
            binding = this
        }
        .root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.tracksState
            .onEach { processState(it) }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        binding?.let {
            it.tracks.layoutManager = LinearLayoutManager(requireContext())
            it.tracks.adapter = adapter
            it.tracks.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun processState(state: Resource<List<TrackModel>>) {
        when (state) {
            Resource.Loading -> showLoading()
            is Resource.Error -> {
                hideLoading()
            }
            is Resource.Success -> {
                hideLoading()
                adapter.submitList(state.data)
            }
        }
    }

    private fun showLoading() {
        binding?.let {
            it.progress.isVisible = true
            it.tracks.isVisible = false
        }
    }

    private fun hideLoading() {
        binding?.let {
            it.progress.isVisible = false
            it.tracks.isVisible = true
        }
    }

    companion object {
        fun newInstance() = TracksFragment()
    }
}