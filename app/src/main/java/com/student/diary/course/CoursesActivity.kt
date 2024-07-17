package com.student.diary.course

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.student.diary.R
import com.student.diary.adapters.CoursesAdapter
import com.student.diary.adapters.EventsAdapter
import com.student.diary.databinding.ActivityCoursesBinding
import com.student.diary.models.Contact
import com.student.diary.models.Course
import com.student.diary.models.Courses
import com.student.diary.util.SharedPreferencesHelper

/**
 * Activity to display a list of courses
 */
class CoursesActivity : AppCompatActivity() {

    /** Shared preferences helper for accessing stored data */
    private lateinit var preference: SharedPreferencesHelper
    /** Binding object for accessing views in the activity layout */
    private lateinit var binding: ActivityCoursesBinding
    /** List to hold all courses */
    private var allCourses = mutableListOf<Courses>()

    /**
     * Called when the activity is starting
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preference = SharedPreferencesHelper(this)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.apply {
            tvTitle.text = "Courses"
        }

        // Load data into the RecyclerView
        loadData()
    }

    /**
     * Load course data from shared preferences and populate the RecyclerView
     */
    private fun loadData() {
        val data = preference.getString("courses")
        if (data != null) {
            try {
                // Deserialize JSON string to list of Course objects using Gson
                val type = object : TypeToken<List<Courses>>() {}.type
                val courseLists: List<Courses> = Gson().fromJson(data, type)
                allCourses.clear()
                allCourses.addAll(courseLists)

                // Set up RecyclerView adapter and layout manager
                val adapter = CoursesAdapter(allCourses, this)
                binding.apply {
                    recyclerView.layoutManager = LinearLayoutManager(this@CoursesActivity)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Called when the activity has detected the user's press of the back key
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Called when the activity has detected the user's press of the back key
     */
    override fun onBackPressed() {
        super.onBackPressed()
    }

    /**
     * Called when the activity will start interacting with the user
     */
    override fun onResume() {
        super.onResume()
        try {
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
