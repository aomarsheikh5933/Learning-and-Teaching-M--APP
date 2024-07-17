package com.student.diary.events

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
import com.student.diary.adapters.CourseAdapter
import com.student.diary.adapters.EventsAdapter
import com.student.diary.databinding.ActivityEventBinding
import com.student.diary.models.Contact
import com.student.diary.models.Event
import com.student.diary.util.SharedPreferencesHelper

/**
 * Activity to display a list of events
 */
class EventActivity : AppCompatActivity() {

    /** Shared preferences helper for accessing stored data */
    private lateinit var preference: SharedPreferencesHelper
    /** Binding object for accessing views in the activity layout */
    private lateinit var binding: ActivityEventBinding

    /**
     * Called when the activity is starting
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        // Initialize shared preferences helper
        preference = SharedPreferencesHelper(this)

        binding.apply {
            tvTitle.text = "Events"
        }
    }

    /**
     * Called when the activity will start interacting with the user
     */
    override fun onResume() {
        super.onResume()
        try {
            // Load event data from shared preferences
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Load event data from shared preferences and populate the RecyclerView
     */
    private fun loadData() {
        val data = preference.getString("events")
        if (data != null) {
            try {
                // Deserialize JSON string to list of Event objects using Gson
                val type = object : TypeToken<List<Event>>() {}.type
                val eventList: List<Event> = Gson().fromJson(data, type)

                // Set up RecyclerView with EventsAdapter
                val adapter = EventsAdapter(eventList, this)
                binding.apply {
                    recyclerView.layoutManager = LinearLayoutManager(this@EventActivity)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Called when the user presses the back button on the toolbar
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Default back button behavior
     */
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
