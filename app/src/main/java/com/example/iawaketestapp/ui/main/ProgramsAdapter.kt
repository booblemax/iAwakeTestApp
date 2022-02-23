package com.example.iawaketestapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iawaketestapp.databinding.ItemProgramBinding
import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.util.base64ToBitmap

class ProgramsAdapter(
    private val itemClickCallback: (ProgramModel) -> Unit
) : ListAdapter<ProgramModel, ProgramViewHolder>(diffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder =
        ItemProgramBinding
            .inflate(LayoutInflater.from(parent.context))
            .let(::ProgramViewHolder)
            .apply {
                itemView.setOnClickListener { itemClickCallback(getItem(bindingAdapterPosition)) }
            }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffItemCallback = object : DiffUtil.ItemCallback<ProgramModel>() {
            override fun areItemsTheSame(oldItem: ProgramModel, newItem: ProgramModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProgramModel, newItem: ProgramModel): Boolean =
                oldItem == newItem
        }
    }
}

class ProgramViewHolder(
    private val binding: ItemProgramBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ProgramModel) {
        with(binding) {
            programTitle.text = model.title
            Glide.with(cover)
                .load(model.cover.url)
                .thumbnail(
                    Glide.with(cover)
                        .load(model.cover.thumbnail.base64ToBitmap())
                )
                .into(cover)
        }
    }
}