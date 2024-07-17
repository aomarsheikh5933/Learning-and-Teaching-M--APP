package com.student.diary.contact

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.student.diary.R
import com.student.diary.adapters.ContactDetailsAdapter
import com.student.diary.contact.child.BasicInformationFragment
import com.student.diary.contact.child.CoursesOfferedFragment
import com.student.diary.databinding.ActivityContactBinding
import com.student.diary.models.Contact
import com.student.diary.util.SharedPreferencesHelper
/**
 * Activity for displaying contact details using a ViewPager and TabLayout
 */
class ContactActivity : AppCompatActivity() {
    /** Adapter for managing fragments in the ViewPager */
    private lateinit var contactDetailsAdapter: ContactDetailsAdapter

    /** Binding object for accessing views in the activity layout */
    private lateinit var binding: ActivityContactBinding

    /**
     * Called when the activity is first created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Inflate the layout for this activity */
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Set up the toolbar */
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        /** Set the title in the TextView */
        binding.apply {
            tvTitle.text = "Contact"
        }

        /** Initialize the adapter and add fragments */
        contactDetailsAdapter = ContactDetailsAdapter(supportFragmentManager).apply {
            addFragment(BasicInformationFragment(), "Basic Information")
            addFragment(CoursesOfferedFragment(), "Course Offering")
        }

        /** Set up the ViewPager with the adapter */
        binding.viewPager.adapter = contactDetailsAdapter

        /** Link the TabLayout with the ViewPager */
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    /**
     * Handle the navigation up event
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle back navigation
        return super.onSupportNavigateUp()
    }

    /**
     * Handle the back pressed event
     */
    override fun onBackPressed() {
        super.onBackPressed() // Call the super class's onBackPressed method
    }
}
