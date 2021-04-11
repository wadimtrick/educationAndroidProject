package com.wadim.trick.gmail.com.androideducationapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val CONTACT_DETAILS_ARGUMENT_KEY = "CONTACT_DETAILS_ARGUMENT_KEY"

class ContactDetailsFragment : Fragment(R.layout.fragment_contact_details) {
    private var scope: CoroutineScope? = null

    companion object {
        fun newInstance(contactID: String) = ContactDetailsFragment().apply {
            arguments = bundleOf(CONTACT_DETAILS_ARGUMENT_KEY to contactID)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActivityToolbarTitle()

        scope?.launch {
            val contactServiceClient = requireActivity() as? ContactServiceClient ?: return@launch
            while (!contactServiceClient.isServiceBinded()) {
            }

            val contactID = arguments?.getString(CONTACT_DETAILS_ARGUMENT_KEY) ?: return@launch
            val contactFullInfo = contactServiceClient.getContactFullInfoWithService(contactID)
            requireActivity().runOnUiThread {
                fillDataForContact(contactFullInfo)
            }
        }
    }

    private fun setActivityToolbarTitle() {
        requireActivity().title = getString(R.string.contact_details_title)
    }

    private fun setNotifySwitchListener(contact: ContactFullInfo) {
        val contactBirthdayInfo = ContactBirthdayInfo(contact)
        val switch: Switch? = view?.findViewById(R.id.contactDetailsBirthdayNotifySwitch)
        if (contact.birthday == null)
            return
        switch?.isVisible = true
        with(ContactBirthdayNotificationManager()) {
            if (isBirthdayNotificationPendingIntentExist(requireContext(), contactBirthdayInfo))
                switch?.isChecked = true
            switch?.setOnCheckedChangeListener { buttonView, isChecked ->
                switchAlarmBithdayNotification(
                        requireContext(),
                        isChecked,
                        contactBirthdayInfo
                )
            }
        }
    }

    private fun fillDataForContact(contact: ContactFullInfo) {
        setNotifySwitchListener(contact)

        val progressBar: ProgressBar? = view?.findViewById(R.id.contactDetailsProgressBar)
        progressBar?.isVisible = false

        val contactPhoto: ImageView? = view?.findViewById(R.id.contactDetailsPhotoImage)
        if (contact.imageURI != Uri.EMPTY)
            contactPhoto?.setImageURI(contact.imageURI)
        else
            contactPhoto?.setImageResource(R.drawable.ic_generic_avatar)

        val contactBirthday: TextView? = view?.findViewById(R.id.contactDetailsBirthdayTV)
        contactBirthday?.text = if (contact.birthday != null)
            SimpleDateFormat("d MMMM", Locale.getDefault()).format(contact.birthday.time) else getString(R.string.date_missing)

        val contactName: TextView? = view?.findViewById(R.id.contactDetailsNameTV)
        contactName?.text = contact?.name

        val contactPhone1: TextView? = view?.findViewById(R.id.contactDetailsPhone1TV)
        contactPhone1?.text = contact?.phone

        val contactPhone2: TextView? = view?.findViewById(R.id.contactDetailsPhone2TV)
        contactPhone2?.text = contact?.phoneSecondary

        val contactEmail1: TextView? = view?.findViewById(R.id.contactDetailsEmail1TV)
        contactEmail1?.text = contact?.email

        val contactEmail2: TextView? = view?.findViewById(R.id.contactDetailsEmail2TV)
        contactEmail2?.text = contact?.emailSecondary

        val description: TextView? = view?.findViewById(R.id.contactDetailsDescriptionTV)
        description?.text = contact?.description
    }
}