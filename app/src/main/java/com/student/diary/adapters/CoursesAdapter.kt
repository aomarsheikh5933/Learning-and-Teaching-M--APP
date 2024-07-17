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
import com.student.diary.course.CourseEditActivity
import com.student.diary.models.Courses
import com.student.diary.util.SharedPreferencesHelper

/**
 * Adapter class for managing and displaying Courses items in a RecyclerView
 */
class CoursesAdapter(
    private var entryList: List<Courses>,
    private val context: Context
) : RecyclerView.Adapter<CoursesAdapter.Pager2ViewHolder>() {

    /**
     * ViewHolder class for holding and managing individual item views in the RecyclerView
     */
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        /** TextView for displaying the course code */
        val tvCode: TextView = itemView.findViewById(R.id.tv_code)

        /** TextView for displaying the course title */
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)

        /** TextView for displaying the contact hours */
        val tvHour: TextView = itemView.findViewById(R.id.tv_hour)

        /** TextView for displaying the days */
        val tvDay: TextView = itemView.findViewById(R.id.tv_day)

        /** TextView for displaying the time */
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)

        /** LinearLayout containing the item view */
        val linearView: LinearLayout = itemView.findViewById(R.id.linearView)

        /** Button for editing the course */
        val btnEdit: MaterialButton = itemView.findViewById(R.id.btn_edit)

        /** Button for deleting the course */
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
                R.layout.courses_item,
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
        holder.tvCode.text = data.courseCode
        holder.tvTitle.text = data.courseTitle
        holder.tvHour.text = data.contactHours
        holder.tvDay.text = data.days
        holder.tvTime.text = data.time

        /** Set click listeners for the LinearLayout and buttons */
        holder.linearView.apply {
            setOnClickListener {
                // Handle click on the linear view
            }
        }
        holder.btnEdit.apply {
            setOnClickListener {
                /** Save the current course data to SharedPreferences and start the CourseEditActivity */
                val gson = Gson()
                SharedPreferencesHelper(context).putString("all_course", gson.toJson(entryList))
                SharedPreferencesHelper(context).putString("current_course", gson.toJson(data))
                SharedPreferencesHelper(context).putString("current_position", position.toString())
                context.startActivity(Intent(context, CourseEditActivity::class.java))
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