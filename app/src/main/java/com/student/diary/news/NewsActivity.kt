package com.student.diary.news

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
import com.student.diary.adapters.EventsAdapter
import com.student.diary.adapters.NewsAdapter
import com.student.diary.databinding.ActivityNewsBinding
import com.student.diary.models.Course
import com.student.diary.models.Courses
import com.student.diary.models.Event
import com.student.diary.models.News
import com.student.diary.util.SharedPreferencesHelper

/**
 * Activity for displaying and managing news items.
 */
class NewsActivity : AppCompatActivity() {

    /** SharedPreferencesHelper instance for managing preferences. */
    private lateinit var preference: SharedPreferencesHelper

    /** View binding instance for the activity layout. */
    private lateinit var binding: ActivityNewsBinding

    /** List to hold all news items. */
    private var allNews = mutableListOf<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        // Initializing SharedPreferencesHelper
        preference = SharedPreferencesHelper(this)

        // Setting up UI elements
        binding.apply {
            tvTitle.text = "News"
        }

        // Loading data initially
        loadData()
    }

    /**
     * Loads news data from SharedPreferences and populates the RecyclerView.
     */
    private fun loadData() {
        val data = preference.getString("news")
        if (data != null) {
            try {
                // Parsing JSON data into list of News objects
                val type = object : TypeToken<List<News>>() {}.type
                val newsLists: List<News> = Gson().fromJson(data, type)

                // Clearing and updating the list of all news
                allNews.clear()
                allNews.addAll(newsLists)

                // Creating and setting up adapter for RecyclerView
                val adapter = NewsAdapter(allNews, this)
                binding.apply {
                    recyclerView.layoutManager = LinearLayoutManager(this@NewsActivity)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            // Reloads data on resume
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Handles up navigation
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        // Handles back button press
        super.onBackPressed()
    }
}
