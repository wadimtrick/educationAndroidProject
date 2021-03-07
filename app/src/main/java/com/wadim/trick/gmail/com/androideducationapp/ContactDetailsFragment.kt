package com.wadim.trick.gmail.com.androideducationapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf

private const val CONTACT_DETAILS_ARGUMENT_KEY = "CONTACT_DETAILS_ARGUMENT_KEY"

class ContactDetailsFragment : Fragment() {
    private var contactID: Int = 0

    companion object {
        fun newInstance(contactID: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(CONTACT_DETAILS_ARGUMENT_KEY to contactID)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactID = it.getInt(CONTACT_DETAILS_ARGUMENT_KEY,0)
        }
    }

    override fun onDestroy() {
        contactID = 0
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActivityToolbarTitle()
        fillDataFroContact(contactID, view)
    }

    private fun setActivityToolbarTitle() {
        requireActivity().title = getString(R.string.contact_details_title)
    }

    fun fillDataFroContact(contactID: Int, view: View) {
        val contactPhoto: ImageView? = view.findViewById(R.id.contactDetailsPhotoImage)
        contactPhoto?.setImageResource(R.drawable.contact_photo)

        val contactName: TextView? = view.findViewById(R.id.contactDetailsNameTV)
        contactName?.text = "Хрюня"

        val contactPhone1: TextView? = view.findViewById(R.id.contactDetailsPhone1TV)
        contactPhone1?.text = "8-800-555-35-35"

        val contactPhone2: TextView? = view.findViewById(R.id.contactDetailsPhone2TV)
        contactPhone2?.text = "8-900-666-74-74"

        val contactEmail1: TextView? = view.findViewById(R.id.contactDetailsEmail1TV)
        contactEmail1?.text = "hryunya@gmail.com"

        val contactEmail2: TextView? = view.findViewById(R.id.contactDetailsEmail2TV)
        contactEmail2?.text = "-"

        val description: TextView? = view.findViewById(R.id.contactDetailsDescriptionTV)
        description?.text = "Шерстяная и мягкая. Не любит звонки после 11.00"
    }
}