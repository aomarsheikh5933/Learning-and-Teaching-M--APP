package com.student.diary.course

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
import com.student.diary.databinding.ActivityCoursesBinding
import com.student.diary.models.Contact
import com.student.diary.models.Courses
import com.student.diary.util.SharedPreferencesHelper


/**
 * Activity for editing course details.
 */
class CourseEditActivity : AppCompatActivity() {

    /** Late-initialized variables for SharedPreferencesHelper and View binding */
    private lateinit var preference: SharedPreferencesHelper
    private lateinit var binding: ActivityCourseEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferencesHelper
        preference = SharedPreferencesHelper(this)

        // Set up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        // Set title for the activity
        binding.apply {
            tvTitle.text = "Edit Course"
        }

        // Load data of the current course to be edited
        loadData()
    }

    /**
     * Loads data of the current course to be edited from SharedPreferences.
     * Displays the data in EditText fields for editing.
     * Handles submit button click to update course details.
     */
    private fun loadData() {
        // Retrieve the current course data from SharedPreferences
        val contact = preference.getString("current_course")
        if (contact != null) {
            try {
                // Parse the JSON string into a Courses object
                val contactData = Gson().fromJson(contact, Courses::class.java)

                // Populate EditText fields with current course data
                binding.apply {
                    tieCode.setText(contactData.courseCode)
                    tieTitle.setText(contactData.courseTitle)
                    tieHours.setText(contactData.contactHours)
                    tieDays.setText(contactData.days)
                    tieTime.setText(contactData.time)

                    // Set click listener for submit button
                    btnSubmit.apply {
                        setOnClickListener {
                            // Retrieve current position and all courses data from SharedPreferences
                            val pos = preference.getString("current_position")
                            val all_course = preference.getString("all_course")
                            if (pos != null) {
                                if (all_course != null) {
                                    // Parse all courses from JSON
                                    val type = object : TypeToken<List<Courses>>() {}.type
                                    val courseLists: List<Courses> =
                                        Gson().fromJson(all_course, type)
                                    val courseList = ArrayList<Courses>()
                                    courseList.clear()
                                    courseList.addAll(courseLists)
                                    courseList.removeAt(pos.toInt())

                                    // Create new course with updated details
                                    val newCourse = Courses(
                                        courseCode = tieCode.text.toString(),
                                        courseTitle = tieTitle.text.toString(),
                                        contactHours = tieHours.text.toString(),
                                        days = tieDays.text.toString(),
                                        time = tieTime.text.toString()
                                    )
                                    courseList.add(newCourse)

                                    // Convert courses list back to JSON
                                    val gson = Gson()
                                    val courses = gson.toJson(courseList)

                                    // Update the courses data in SharedPreferences
                                    preference.putString("courses", gson.toJson(courseList))

                                    // Show success message
                                    Toast.makeText(
                                        this@CourseEditActivity,
                                        "Course Updated Successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    // Finish the activity
                                    this@CourseEditActivity.finish()
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /** Handle up navigation when the toolbar back button is pressed */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /** Handle back button press to navigate back */
    override fun onBackPressed() {
        super.onBackPressed()
    }
}