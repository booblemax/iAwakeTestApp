package com.example.iawaketestapp.ui.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.iawaketestapp.R
import com.example.iawaketestapp.databinding.ItemTrackBinding
import com.example.iawaketestapp.domain.models.TrackModel

class TracksAdapter(
    private val itemClickCallback: (TrackModel) -> Unit
) : ListAdapter<TrackModel, TrackViewHolder>(diffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder =
        ItemTrackBinding
            .inflate(LayoutInflater.from(parent.context))
            .let(::TrackViewHolder)
            .apply {
                itemView.setOnClickListener { itemClickCallback(getItem(bindingAdapterPosition)) }
            }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffItemCallback = object : DiffUtil.ItemCallback<TrackModel>() {
            override fun areItemsTheSame(oldItem: TrackModel, newItem: TrackModel): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(oldItem: TrackModel, newItem: TrackModel): Boolean =
                oldItem == newItem
        }
    }
}

class TrackViewHolder(
    private val binding: ItemTrackBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: TrackModel) {
        with(binding) {
            name.text = model.title
            val minutes = model.duration / SECONDS
            val seconds = model.duration % SECONDS
            time.text = root.context.getString(R.string.label_time, minutes.toString(), seconds.toString())
        }
    }

    companion object {
        private const val SECONDS = 60
    }
}