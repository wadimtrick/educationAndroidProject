package com.wadim.trick.gmail.com.androideducationapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.coroutines.*

const val CONTACT_SHORT_INFO_KEY = "CONTACT_SHORT_INFO_KEY"

class ContactListElementFragment : Fragment(R.layout.fragment_contact_list_element) {
    private var scope: CoroutineScope? = null

    companion object {
        fun newInstance() = ContactListElementFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActivityToolbarTitle()

        scope?.launch {
            val contactServiceClient = requireActivity() as? ContactServiceClient ?: return@launch
            while (!contactServiceClient.isServiceBinded()) {
            }

            val contact = contactServiceClient?.getContactListShortInfoWithService().firstOrNull()
                ?: return@launch
            requireActivity().runOnUiThread {
                setOnClickShowDetails(contact.id)
                fillDataForContact(contact )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scope = CoroutineScope(Dispatchers.IO)
    }

    override fun onDetach() {
        scope?.cancel()
        super.onDetach()
    }

    private fun fillDataForContact(contact: ContactShortInfo) {
        val progressBar: ProgressBar? = view?.findViewById(R.id.contactProgressBar)
        progressBar?.isVisible = false

        val contactPhotoView: ImageView? = view?.findViewById(R.id.contactPhotoImage)
        contactPhotoView?.setImageResource(contact.imageId)

        val contactName: TextView? = view?.findViewById(R.id.contactNameTV)
        contactName?.text = contact.name

        val contactPhone: TextView? = view?.findViewById(R.id.contactPhoneTV)
        contactPhone?.text = contact.phone
    }

    private fun setOnClickShowDetails(contactId: Int) {
        view?.setOnClickListener {
            (requireActivity() as? ClickableContactListElement)?.showContactDetails(contactId)
        }
    }

    private fun setActivityToolbarTitle() {
        requireActivity().title = getString(R.string.contact_list_title)
    }
}