package com.student.diary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.student.diary.R
import com.student.diary.models.Contact
import com.student.diary.models.Course
import com.student.diary.util.SharedPreferencesHelper


/**
 * Adapter class for managing and displaying Course items in a RecyclerView
 */
class CourseAdapter(
    private var contactData: Contact,
    private var entryList: MutableList<Course>,
    private val context: Context
) : RecyclerView.Adapter<CourseAdapter.Pager2ViewHolder>() {

    /**
     * ViewHolder class for holding and managing individual item views in the RecyclerView
     */
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        /** TextView for displaying the course code */
        val tvCode: TextView = itemView.findViewById(R.id.tv_code)

        /** TextView for displaying the course name */
        val tvName: TextView = itemView.findViewById(R.id.tv_name)

        /** Button for editing the course */
        val btnEdit: MaterialButton = itemView.findViewById(R.id.btn_edit)

        /** Button for deleting the course */
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btn_delete)

        /** LinearLayout containing the item view */
        val linearView: LinearLayout = itemView.findViewById(R.id.linearView)

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
                R.layout.course_item,
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
        val course = entryList[position]
        val tvCode = entryList[position].course_code
        val tvName = entryList[position].name

        /** Set the data to the views */
        holder.tvCode.text = tvCode
        holder.tvName.text = tvName

        /** Set click listeners for the LinearLayout and buttons */
        holder.linearView.apply {
            setOnClickListener {
                // Handle click on the linear view
            }
        }
        holder.btnEdit.apply {
            setOnClickListener {
                // Handle click on the edit button
            }
        }
        holder.btnDelete.apply {
            setOnClickListener {
                /** Create an AlertDialog to confirm deletion */
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirm Action")
                builder.setMessage("Are you sure you want to proceed?")

                builder.setPositiveButton("Confirm") { dialog, _ ->
                    /** Remove the course from the list and update the data */
                    entryList.remove(course)
                    val updatedContact = contactData.copy(
                        course = entryList
                    )
                    val gson = Gson()
                    SharedPreferencesHelper(context).putString(
                        "contact",
                        gson.toJson(updatedContact)
                    )
                    notifyDataSetChanged()

                    Toast.makeText(context, "Course Removed Successfully", Toast.LENGTH_LONG).show()
                }

                builder.setNegativeButton("Cancel") { dialog, _ ->
                    /** Handle the cancel action */
                    dialog.dismiss()
                }

                /** Show the dialog */
                val dialog = builder.create()
                dialog.show()
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