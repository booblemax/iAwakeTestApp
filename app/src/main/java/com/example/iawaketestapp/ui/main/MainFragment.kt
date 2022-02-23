package com.example.iawaketestapp.ui.main

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
import androidx.recyclerview.widget.RecyclerView
import com.example.iawaketestapp.R
import com.example.iawaketestapp.databinding.MainFragmentBinding
import com.example.iawaketestapp.domain.Resource
import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.ui.tracks.TracksFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()
    private var binding: MainFragmentBinding? = null
    private val adapter = ProgramsAdapter {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TracksFragment.newInstance(it.id))
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater, container, false)
        .apply {
            binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.programsState
            .onEach { processState(it) }
            .launchIn(lifecycleScope)

        viewModel.loadPrograms()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        binding?.let {
            it.list.layoutManager = LinearLayoutManager(requireContext())
            it.list.adapter = adapter
            it.list.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun processState(state: Resource<List<ProgramModel>>) {
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
            it.list.isVisible = false
        }
    }

    private fun hideLoading() {
        binding?.let {
            it.progress.isVisible = false
            it.list.isVisible = true
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}