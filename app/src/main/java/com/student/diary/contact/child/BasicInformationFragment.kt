package com.student.diary.contact.child

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.student.diary.R
import com.student.diary.databinding.FragmentBasicInformationBinding
import com.student.diary.models.Contact
import com.student.diary.util.SharedPreferencesHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasicInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicInformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var preference: SharedPreferencesHelper
    private var _binding: FragmentBasicInformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * Called to have the fragment instantiate its user interface view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBasicInformationBinding.inflate(inflater, container, false)
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
     * Called immediately after onCreateView() has returned, but before any saved state has been restored in to the view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        handleFieldsStatus()
    }

    /**
     * Called when the fragment is visible to the user and actively running
     */
    override fun onResume() {
        super.onResume()
        try {
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Load contact data from shared preferences and display it in the UI
     */
    private fun loadData() {
        preference = SharedPreferencesHelper(requireContext())
        val contact = preference.getString("contact")
        if (contact != null) {
            try {
                val contactData = Gson().fromJson(contact, Contact::class.java)
                binding.apply {
                    tieName.setText(contactData.name)
                    tieEmail.setText(contactData.email)
                    tiePhone.setText(contactData.phone)
                    tiePosition.setText(contactData.position)
                    tieType.setText(contactData.type)
                    tieOffice.setText(contactData.office)
                    tieOfficeHour.setText(contactData.offile_hour)

                    fab.setOnClickListener {
                        showEditDialog(contactData)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Show a dialog to edit contact information
     * @param existingContact The current contact information to be edited
     */
    private fun showEditDialog(existingContact: Contact) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_contact, null)

        // Initialize dialog views
        val btnSubmit: MaterialButton = dialogView.findViewById(R.id.btn_submit)
        val tieName: TextInputEditText = dialogView.findViewById(R.id.tieName)
        val tieEmail: TextInputEditText = dialogView.findViewById(R.id.tieEmail)
        val tiePhone: TextInputEditText = dialogView.findViewById(R.id.tiePhone)
        val tiePosition: TextInputEditText = dialogView.findViewById(R.id.tiePosition)
        val tieType: TextInputEditText = dialogView.findViewById(R.id.tieType)
        val tieOffice: TextInputEditText = dialogView.findViewById(R.id.tieOffice)
        val tieOfficeHour: TextInputEditText = dialogView.findViewById(R.id.tieOfficeHour)

        // Set up AlertDialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Pre-fill dialog fields with existing contact data
        tieName.setText(existingContact.name)
        tieEmail.setText(existingContact.email)
        tiePhone.setText(existingContact.phone)
        tiePosition.setText(existingContact.position)
        tieType.setText(existingContact.type)
        tieOffice.setText(existingContact.office)
        tieOfficeHour.setText(existingContact.offile_hour)

        // Set the click listener for the submit button
        btnSubmit.setOnClickListener {
            // Update contact data with new values from the dialog
            val updatedContact = existingContact.copy(
                name = if (tieName.text.toString() != existingContact.name) tieName.text.toString() else existingContact.name,
                email = if (tieEmail.text.toString() != existingContact.email) tieEmail.text.toString() else existingContact.email,
                phone = if (tiePhone.text.toString() != existingContact.phone) tiePhone.text.toString() else existingContact.phone,
                position = if (tiePosition.text.toString() != existingContact.position) tiePosition.text.toString() else existingContact.position,
                type = if (tieType.text.toString() != existingContact.type) tieType.text.toString() else existingContact.type,
                office = if (tieOffice.text.toString() != existingContact.office) tieOffice.text.toString() else existingContact.office,
                offile_hour = if (tieOfficeHour.text.toString() != existingContact.offile_hour) tieOfficeHour.text.toString() else existingContact.offile_hour
            )

            // Save updated contact data to shared preferences
            val gson = Gson()
            preference.putString("contact", gson.toJson(updatedContact))

            // Dismiss the dialog and reload data
            dialog.dismiss()
            loadData()
        }

        dialog.show()
    }

    /**
     * Disable editing for a given TextInputEditText
     * @param editText The TextInputEditText to be disabled
     */
    private fun disableEditing(editText: TextInputEditText) {
        editText.keyListener = null
        editText.isEnabled = false
        editText.setTextColor(resources.getColor(R.color.black))
    }

    /**
     * Handle the status of fields by disabling editing
     */
    private fun handleFieldsStatus() {
        disableEditing(binding.tieName)
        disableEditing(binding.tieEmail)
        disableEditing(binding.tiePhone)
        disableEditing(binding.tiePosition)
        disableEditing(binding.tieType)
        disableEditing(binding.tieOffice)
        disableEditing(binding.tieOfficeHour)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BasicInformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BasicInformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}