package com.student.diary.news

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
import com.student.diary.databinding.ActivityNewsEditBinding
import com.student.diary.models.Courses
import com.student.diary.models.News
import com.student.diary.util.SharedPreferencesHelper

/**
 * Activity for editing news details.
 */
class NewsEditActivity : AppCompatActivity() {

    /** SharedPreferencesHelper instance for managing preferences. */
    private lateinit var preference: SharedPreferencesHelper

    /** View binding instance for the activity layout. */
    private lateinit var binding: ActivityNewsEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing SharedPreferencesHelper
        preference = SharedPreferencesHelper(this)

        // Setting up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        // Setting title for the activity
        binding.apply {
            tvTitle.text = "Edit News"
        }

        // Loading existing news data
        loadData()
    }

    /**
     * Loads existing news data into the UI for editing.
     */
    private fun loadData() {
        val contact = preference.getString("current_news")
        if (contact != null) {
            try {
                // Parsing JSON data into News object
                val contactData = Gson().fromJson(contact, News::class.java)

                // Populating UI fields with existing news data
                binding.apply {
                    tieHighlight.setText(contactData.highlights)
                    tieTitle.setText(contactData.title)
                    tieKeyword.setText(contactData.keyword)

                    // Handling update button click
                    btnSubmit.apply {
                        setOnClickListener {
                            val pos = preference.getString("current_position")
                            val all_news = preference.getString("all_news")
                            if (pos != null && all_news != null) {
                                // Parsing all news data
                                val type = object : TypeToken<List<News>>() {}.type
                                val newsLists: List<News> = Gson().fromJson(all_news, type)

                                // Updating news list with edited news
                                val newsList = ArrayList<News>()
                                newsList.clear()
                                newsList.addAll(newsLists)
                                newsList.removeAt(pos.toInt())
                                val newNews = News(
                                    highlights = tieHighlight.text.toString(),
                                    title = tieTitle.text.toString(),
                                    keyword = tieKeyword.text.toString()
                                )
                                newsList.add(newNews)

                                // Saving updated news list to SharedPreferences
                                val gson = Gson()
                                preference.putString("news", gson.toJson(newsList))

                                // Displaying success message
                                Toast.makeText(
                                    this@NewsEditActivity,
                                    "News Updated Successfully",
                                    Toast.LENGTH_LONG
                                ).show()

                                // Finishing activity after update
                                this@NewsEditActivity.finish()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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
