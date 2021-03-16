package com.wadim.trick.gmail.com.androideducationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf

class ContactListElementFragment : Fragment() {
    companion object {
        fun newInstance() = ContactListElementFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillDataForContact()

        setActivityToolbarTitle()

        view.setOnClickListener {
            (requireActivity() as? ClickableContactListElement)?.showContactDetails(5)
        }
    }

    private fun fillDataForContact() {
        val contactPhotoView: ImageView? = view?.findViewById(R.id.contactPhotoImage)
        contactPhotoView?.setImageResource(R.drawable.contact_photo)

        val contactName: TextView? = view?.findViewById(R.id.contactNameTV)
        contactName?.text = "Хрюня"

        val contactPhone: TextView? = view?.findViewById(R.id.contactPhoneTV)
        contactPhone?.text = "8-800-555-35-35"
    }

    private fun setActivityToolbarTitle() {
        requireActivity().title = getString(R.string.contact_list_title)
    }
}