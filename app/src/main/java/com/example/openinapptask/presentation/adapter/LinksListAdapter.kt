package com.example.openinapptask.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.openinapptask.databinding.LinkCardBinding
import com.example.openinapptask.model.Link

class LinksListAdapter(private val onPressedCopy: (String) -> Unit) :
    ListAdapter<Link, LinksListAdapter.LinkItemViewHolder>(LinkDiffCallback()) {
    class LinkItemViewHolder(binding: LinkCardBinding, onPressedCopy: (String) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.image
        private val linkName = binding.linkName
        private val creationDateText = binding.linkCreationDate
        private val totalClicks = binding.linkClickCount
        private val smartLink = binding.smartLinkText
        init {
            binding.bottomLinkShape.setOnClickListener { onPressedCopy(binding.smartLinkText.text.toString()) }
        }

        fun bind(link: Link) {
            linkName.text = link.title
            creationDateText.text = link.createdAt
            totalClicks.text = link.totalClicks.toString()
            smartLink.text = link.smartLink
            imageView.load(link.originalImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkItemViewHolder {
        return LinkItemViewHolder(
            LinkCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onPressedCopy = onPressedCopy
        )
    }

    override fun onBindViewHolder(holder: LinkItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LinkDiffCallback : DiffUtil.ItemCallback<Link>() {
        override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem.urlId == newItem.urlId
        }

        override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem == newItem
        }
    }

}
