package com.student.diary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.student.diary.databinding.LandingPageItemBinding
import com.student.diary.viewmodels.LayoutListViewModel

/**
 * Adapter class for managing and displaying Layout items in a RecyclerView
 */
class LayoutsRecyclerViewAdapter(private val onItemClick: (LayoutListViewModel.Layout) -> Unit) :
    ListAdapter<LayoutListViewModel.Layout, LayoutViewHolder>(LayoutDiffUtil()) {

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutViewHolder {
        /** Inflate the item layout and create the ViewHolder */
        return LayoutViewHolder(
            LandingPageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick,
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: LayoutViewHolder, position: Int) {
        /** Bind the data to the view holder */
        holder.bind(getItem(position))
    }
}

/**
 * ViewHolder class for holding and managing individual item views in the RecyclerView
 */
class LayoutViewHolder(
    val binding: LandingPageItemBinding,
    private val onItemClick: (LayoutListViewModel.Layout) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Bind the Layout data to the views
     */
    fun bind(layout: LayoutListViewModel.Layout) {
        /** Set the icon image */
        binding.componentLayoutIconImageview.setImageResource(layout.iconId)

        /** Set the text */
        binding.componentLayoutTextView.text =
            binding.componentLayoutTextView.context.getString(layout.textId)

        /** Set the click listener for the root view */
        binding.root.setOnClickListener { onItemClick(layout) }
    }
}

/**
 * DiffUtil class for calculating the differences between two non-null items in a list
 */
class LayoutDiffUtil : DiffUtil.ItemCallback<LayoutListViewModel.Layout>() {

    /**
     * Check if two items are the same
     */
    override fun areItemsTheSame(
        oldLayout: LayoutListViewModel.Layout,
        newLayout: LayoutListViewModel.Layout,
    ) = oldLayout === newLayout

    /**
     * Check if the content of two items is the same
     */
    override fun areContentsTheSame(
        oldLayout: LayoutListViewModel.Layout,
        newLayout: LayoutListViewModel.Layout,
    ) = oldLayout == newLayout
}
