package com.student.diary.contact.child

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.student.diary.R
import com.student.diary.adapters.CourseAdapter
import com.student.diary.databinding.FragmentCoursesOfferedBinding
import com.student.diary.models.Contact
import com.student.diary.models.Course
import com.student.diary.util.SharedPreferencesHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoursesOfferedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
/**
 * Fragment class for displaying and editing the courses offered by a contact
 */
class CoursesOfferedFragment : Fragment() {
    // Parameters to be renamed and changed as needed
    private var param1: String? = null
    private var param2: String? = null

    /** Shared preferences helper for accessing stored data */
    private lateinit var preference: SharedPreferencesHelper
    /** Binding object for accessing views in the fragment layout */
    private var _binding: FragmentCoursesOfferedBinding? = null
    /** List to hold all courses */
    private var allCourses = mutableListOf<Course>()

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    /**
     * Called to do initial creation of a fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCoursesOfferedBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called when the view created by onCreateView() has been detached from the fragment
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Called immediately after onCreateView() has returned, but before any saved state has been restored into the view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    /**
     * Load contact data from shared preferences and display the courses offered in the UI
     */
    private fun loadData() {
        preference = SharedPreferencesHelper(requireContext())
        val contact = preference.getString("contact")
        if (contact != null) {
            try {
                val contactData = Gson().fromJson(contact, Contact::class.java)
                allCourses.clear()
                allCourses.addAll(contactData.course)

                // Set up the RecyclerView with the courses offered
                val adapter = CourseAdapter(contactData, allCourses, requireContext())
                binding.apply {
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter

                    // Set the click listener for the FloatingActionButton
                    fab.setOnClickListener {
                        showEditDialog(contactData, contactData.course)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Show a dialog to add a new course to the contact's courses offered
     * @param contact The current contact information
     * @param course The current list of courses offered by the contact
     */
    private fun showEditDialog(contact: Contact, course: MutableList<Course>) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_contact_course, null)
        // Initialize dialog views
        val btnSubmit: MaterialButton = dialogView.findViewById(R.id.btn_submit)
        val tieCourseCode: TextInputEditText = dialogView.findViewById(R.id.tieCourseCode)
        val tieCourseName: TextInputEditText = dialogView.findViewById(R.id.tieCourseName)

        // Set up AlertDialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Set the click listener for the submit button
        btnSubmit.setOnClickListener {
            val code = tieCourseCode.text.toString()
            val name = tieCourseName.text.toString()
            if (code.isEmpty()) {
                tieCourseCode.error = "Enter code"
                tieCourseCode.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()) {
                tieCourseName.error = "Enter name"
                tieCourseName.requestFocus()
                return@setOnClickListener
            }
            val newCourse = Course(course_code = code, name = name)
            course.add(newCourse)
            val updatedContact = contact.copy(course = course)
            val gson = Gson()
            preference.putString("contact", gson.toJson(updatedContact))

            dialog.dismiss()
            loadData()
        }

        dialog.show()
    }

    companion object {
        /**
         * Factory method to create a new instance of this fragment using the provided parameters
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoursesOfferedFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoursesOfferedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
