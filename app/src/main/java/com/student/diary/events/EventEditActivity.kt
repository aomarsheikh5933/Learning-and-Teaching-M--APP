package com.student.diary.events

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.student.diary.R
import com.student.diary.databinding.ActivityCourseEditBinding
import com.student.diary.databinding.ActivityEventEditBinding
import com.student.diary.models.Courses
import com.student.diary.models.Event
import com.student.diary.models.News
import com.student.diary.util.SharedPreferencesHelper

/**
 * Activity to edit an existing event.
 */
class EventEditActivity : AppCompatActivity() {

    /** Shared preferences helper for accessing stored data */
    private lateinit var preference: SharedPreferencesHelper
    /** Binding object for accessing views in the activity layout */
    private lateinit var binding: ActivityEventEditBinding

    /**
     * Called when the activity is starting
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize shared preferences helper
        preference = SharedPreferencesHelper(this)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        binding.apply {
            tvTitle.text = "Edit Event"
        }

        // Load data of the current event for editing
        loadData()
    }

    /**
     * Load data of the current event from shared preferences and populate the UI fields.
     */
    private fun loadData() {
        val eventJson = preference.getString("current_event")
        if (eventJson != null) {
            try {
                val event = Gson().fromJson(eventJson, Event::class.java)

                // Populate UI fields with current event data
                binding.apply {
                    tieDay.setText(event.day)
                    tieNote.setText(event.note)
                    tieTime.setText(event.time)
                    tieType.setText(event.type)

                    btnSubmit.setOnClickListener {
                        // Retrieve the current position and all events list from preferences
                        val pos = preference.getString("current_position")
                        val allEventsJson = preference.getString("all_events")
                        if (pos != null && allEventsJson != null) {
                            // Deserialize the all events list
                            val type = object : TypeToken<List<Event>>() {}.type
                            val allEvents: MutableList<Event> =
                                Gson().fromJson(allEventsJson, type)

                            // Update the event at the specified position
                            allEvents.removeAt(pos.toInt())
                            val updatedEvent = Event(
                                day = tieDay.text.toString(),
                                note = tieNote.text.toString(),
                                time = tieTime.text.toString(),
                                type = tieType.text.toString()
                            )
                            allEvents.add(updatedEvent)

                            // Save updated event list back to preferences
                            val gson = Gson()
                            preference.putString("events", gson.toJson(allEvents))

                            // Show success message and finish activity
                            Toast.makeText(
                                this@EventEditActivity,
                                "Event Updated Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            this@EventEditActivity.finish()
                        }
                    }
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
