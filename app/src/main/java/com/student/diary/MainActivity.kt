package com.student.diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.student.diary.adapters.LayoutsRecyclerViewAdapter
import com.student.diary.contact.ContactActivity
import com.student.diary.course.CoursesActivity
import com.student.diary.databinding.ActivityMainBinding
import com.student.diary.databinding.ActivityNewsBinding
import com.student.diary.events.EventActivity
import com.student.diary.models.Contact
import com.student.diary.models.Course
import com.student.diary.models.Courses
import com.student.diary.models.News
import com.student.diary.news.NewsActivity
import com.student.diary.util.SharedPreferencesHelper
import com.student.diary.util.XmlParserFactory
import com.student.diary.viewmodels.LayoutListViewModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

/**
 * Main activity for the Diary application.
 */
class MainActivity : AppCompatActivity() {

    // ViewModels
    private val viewModel: LayoutListViewModel by viewModels()

    // View Binding
    private lateinit var binding: ActivityMainBinding

    // SharedPreferences Helper
    private lateinit var preference: SharedPreferencesHelper

    // Lists to hold data
    private var allCourses = mutableListOf<Courses>()
    private var allNews = mutableListOf<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        // Set title in layout
        binding.apply {
            tvTitle.text = "Diary"
        }

        // Initialize SharedPreferences helper
        preference = SharedPreferencesHelper(this@MainActivity)

        // Set up RecyclerView and load initial data
        setUpLayoutsRecyclerView()
        checkAndPullInitial()
    }

    /**
     * Checks for initial data in SharedPreferences and pulls data from XML files if necessary.
     */
    private fun checkAndPullInitial() {
        // Check if contact data needs to be updated
        val updateContact = preference.getString("contact")
        if (updateContact == null) {
            // Read contacts from XML and save to SharedPreferences if not already present
            val contact = readXmlFromAssets(this@MainActivity, "contacts.xml")
            if (contact != null) {
                val gson = Gson()
                preference.putString("contact", gson.toJson(contact))
            }
        }

        // Check if courses data needs to be updated
        val updateCourses = preference.getString("courses")
        if (updateCourses == null) {
            // Parse courses XML and save to SharedPreferences if not already present
            val courses =
                XmlParserFactory().parseCoursesXmlFromAssets(this@MainActivity, "courses.xml")
            if (courses != null) {
                val gson = Gson()
                allCourses.clear()
                allCourses.add(courses)
                preference.putString("courses", gson.toJson(allCourses))
            }
        }

        // Check if events data needs to be updated
        val updateEvents = preference.getString("events")
        if (updateEvents == null) {
            // Parse events XML and save to SharedPreferences if not already present
            val events =
                XmlParserFactory().parseEventsXmlFromAssets(this@MainActivity, "events.xml")
            if (events.isNotEmpty()) {
                val gson = Gson()
                preference.putString("events", gson.toJson(events))
            }
        }

        // Check if news data needs to be updated
        val updateNews = preference.getString("news")
        if (updateNews == null) {
            // Parse news XML and save to SharedPreferences if not already present
            val news = XmlParserFactory().parseNewsXmlFromAssets(this@MainActivity, "news.xml")
            if (news != null) {
                val gson = Gson()
                allNews.clear()
                allNews.add(news)
                preference.putString("news", gson.toJson(allNews))
            }
        }
    }

    /**
     * Reads XML data from assets and parses it into a Contact object.
     *
     * @param context The context to access assets.
     * @param fileName The name of the XML file in assets.
     * @return Contact object parsed from XML.
     */
    private fun readXmlFromAssets(context: Context, fileName: String): Contact? {
        var inputStream: InputStream? = null
        var contact: Contact? = null

        try {
            // Open the XML file from assets
            val assetManager = context.assets
            inputStream = assetManager.open(fileName)

            // Create XML Pull Parser
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            var currentCourse: Course? = null

            // Parse through the XML content
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (tagName) {
                            // Parse <contact> tag attributes
                            "contact" -> {
                                contact = Contact()
                                contact.name = parser.getAttributeValue(null, "name")
                                contact.email = parser.getAttributeValue(null, "email")
                                contact.office = parser.getAttributeValue(null, "office")
                                contact.offile_hour = parser.getAttributeValue(null, "offile_hour")
                                contact.phone = parser.getAttributeValue(null, "phone")
                                contact.position = parser.getAttributeValue(null, "position")
                                contact.type = parser.getAttributeValue(null, "type")
                            }

                            // Parse <course> tag attributes and add to contact's course list
                            "course" -> {
                                currentCourse = Course()
                                currentCourse.name = parser.getAttributeValue(null, "name")
                                currentCourse.course_code =
                                    parser.getAttributeValue(null, "course_code")
                                contact?.course?.add(currentCourse)
                            }
                        }
                    }
                }

                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }

        return contact
    }

    /**
     * Sets up the RecyclerView for displaying layout options.
     */
    private fun setUpLayoutsRecyclerView() {
        // Set up RecyclerView adapter
        val adapter =
            LayoutsRecyclerViewAdapter(::onItemClick).apply { submitList(viewModel.getLayoutList()) }
        val recyclerView = findViewById<RecyclerView>(R.id.sdcLayoutsRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
    }

    /**
     * Handles item click events from the RecyclerView.
     *
     * @param layout The layout item that was clicked.
     */
    private fun onItemClick(layout: LayoutListViewModel.Layout) {
        // Start respective activities based on clicked layout item
        when (layout.name) {
            "CONTACT" -> {
                startActivity(Intent(this, ContactActivity::class.java))
            }

            "COURSES" -> {
                startActivity(Intent(this, CoursesActivity::class.java))
            }

            "EVENTS" -> {
                startActivity(Intent(this, EventActivity::class.java))
            }

            "NEWS" -> {
                startActivity(Intent(this, NewsActivity::class.java))
            }

            else -> {
                Log.e("TAG", "Unknown layout clicked")
            }
        }
    }

}