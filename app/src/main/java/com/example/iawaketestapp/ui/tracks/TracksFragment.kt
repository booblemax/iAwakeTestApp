package com.example.iawaketestapp.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iawaketestapp.R
import com.example.iawaketestapp.databinding.TracksFragmentBinding
import com.example.iawaketestapp.domain.Resource
import com.example.iawaketestapp.domain.models.TrackModel
import com.example.iawaketestapp.ui.base.PlaybackCallback
import com.example.iawaketestapp.ui.player.PlayerMiniFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TracksFragment : Fragment() {

    private val viewModel by viewModels<TracksViewModel>()
    private var binding: TracksFragmentBinding? = null
    private var playbackCallback: PlaybackCallback? = null
    private val adapter = TracksAdapter {
        viewModel.updatePlayingSong(it)
        playbackCallback?.play(it.media.url)
        showPlayer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = TracksFragmentBinding.inflate(inflater, container, false)
        .apply {
            playbackCallback = requireActivity() as PlaybackCallback
            binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()

        viewModel.tracksState
            .onEach { processState(it) }
            .launchIn(lifecycleScope)

        arguments?.getString(ID_ARG)?.let {
            viewModel.getTracksForProgram(it)
        } ?: showError()
    }

    override fun onDestroyView() {
        binding = null
        playbackCallback = null
        super.onDestroyView()
    }

    private fun initToolbar() {
        binding?.let {
            it.toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
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

    private fun showError() {
        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    private fun showPlayer() {
        childFragmentManager.findFragmentByTag("PlayerMiniFragment")?.let { return }
        childFragmentManager.beginTransaction()
            .add(
                R.id.player_container,
                PlayerMiniFragment::class.java,
                null,
                "PlayerMiniFragment"
            )
            .commit()
    }

    companion object {
        private const val ID_ARG = "id_arg"

        fun newInstance(id: String) = TracksFragment().apply {
            arguments = bundleOf(ID_ARG to  id)
        }
    }
}