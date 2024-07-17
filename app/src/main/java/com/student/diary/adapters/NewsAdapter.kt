package com.student.diary.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.student.diary.R
import com.student.diary.events.EventEditActivity
import com.student.diary.models.News
import com.student.diary.news.NewsEditActivity
import com.student.diary.util.SharedPreferencesHelper

/**
 * Adapter class for managing and displaying News items in a RecyclerView
 */
class NewsAdapter(
    private var entryList: List<News>,
    private val context: Context
) : RecyclerView.Adapter<NewsAdapter.Pager2ViewHolder>() {

    /**
     * ViewHolder class for holding and managing individual item views in the RecyclerView
     */
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        /** TextView for displaying the news title */
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)

        /** TextView for displaying the news keyword */
        val tvKeyword: TextView = itemView.findViewById(R.id.tv_keyword)

        /** TextView for displaying the news highlights */
        val tvHighlight: TextView = itemView.findViewById(R.id.tv_highlight)

        /** LinearLayout containing the item view */
        val linearView: LinearLayout = itemView.findViewById(R.id.linearView)

        /** Button for editing the news */
        val btnEdit: MaterialButton = itemView.findViewById(R.id.btn_edit)

        /** Button for deleting the news */
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btn_delete)

        init {
            /** Set click listeners for the item view and the LinearLayout */
            itemView.setOnClickListener(this)
            linearView.setOnClickListener {
                // Handle click on the linear view
            }
        }

        /** Handle click events */
        override fun onClick(p0: View) {
            // Handle click on the item view
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        /** Inflate the item layout and create the ViewHolder */
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        /** Get the data model based on position */
        val data = entryList[position]

        /** Set the data to the views */
        holder.tvTitle.text = data.title
        holder.tvKeyword.text = data.keyword
        holder.tvHighlight.text = data.highlights

        /** Set click listeners for the LinearLayout and buttons */
        holder.linearView.apply {
            setOnClickListener {
                // Handle click on the linear view
            }
        }
        holder.btnEdit.apply {
            setOnClickListener {
                /** Save the current news data to SharedPreferences and start the NewsEditActivity */
                val gson = Gson()
                SharedPreferencesHelper(context).putString("all_news", gson.toJson(entryList))
                SharedPreferencesHelper(context).putString("current_news", gson.toJson(data))
                SharedPreferencesHelper(context).putString("current_position", position.toString())
                context.startActivity(Intent(context, NewsEditActivity::class.java))
            }
        }
        holder.btnDelete.apply {
            setOnClickListener {
                // Handle click on the delete button
            }
        }
    }

    /**
     * Return the size of the dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return entryList.size // Return the total number of items in the list
    }
}
