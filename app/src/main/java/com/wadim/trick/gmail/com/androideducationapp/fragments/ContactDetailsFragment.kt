package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.net.Uri
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.presenters.ContactDetailsPresenter
import com.wadim.trick.gmail.com.androideducationapp.views.ContactDetailsView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.text.SimpleDateFormat
import java.util.*

const val CONTACT_DETAILS_ARGUMENT_KEY = "CONTACT_DETAILS_ARGUMENT_KEY"

class ContactDetailsFragment : MvpAppCompatFragment(R.layout.fragment_contact_details), ContactDetailsView {
    @InjectPresenter
    lateinit var contactDetailsPresenter: ContactDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): ContactDetailsPresenter {
        val contactID = arguments?.getString(CONTACT_DETAILS_ARGUMENT_KEY) ?: ""
        return ContactDetailsPresenter(requireActivity().applicationContext, contactID)
    }

    companion object {
        fun newInstance(contactID: String) = ContactDetailsFragment().apply {
            arguments = bundleOf(CONTACT_DETAILS_ARGUMENT_KEY to contactID)
        }
    }

    override fun showContactDetails(contact: ContactFullInfo) {
        val switch: Switch? = view?.findViewById(R.id.contactDetailsBirthdayNotifySwitch)
        if (switch != null)
            contactDetailsPresenter.setNotifySwitchListener(contact, switch)

        val progressBar: ProgressBar? = view?.findViewById(R.id.contactDetailsProgressBar)
        progressBar?.isVisible = false

        setContactPhoto(contact.imageURI)
        setContactBirthdayInfo(contact.birthday)
        setContactTextInfo(contact)
    }

    override fun setToolbarTitle() {
        requireActivity().title = getString(R.string.contact_details_title)
    }

    private fun setContactPhoto(photoUri: Uri) {
        val contactPhoto: ImageView? = view?.findViewById(R.id.contactDetailsPhotoImage)
        if (photoUri != Uri.EMPTY)
            contactPhoto?.setImageURI(photoUri)
        else
            contactPhoto?.setImageResource(R.drawable.ic_generic_avatar)
    }

    private fun setContactBirthdayInfo(contactBirthday: Calendar?) {
        val contactBirthdayTV: TextView? = view?.findViewById(R.id.contactDetailsBirthdayTV)
        contactBirthdayTV?.text = if (contactBirthday != null)
            SimpleDateFormat("d MMMM", Locale.getDefault()).format(contactBirthday.time) else getString(R.string.date_missing)
    }

    private fun setContactTextInfo(contact: ContactFullInfo) {
        val contactName: TextView? = view?.findViewById(R.id.contactDetailsNameTV)
        contactName?.text = contact.name

        val contactPhone1: TextView? = view?.findViewById(R.id.contactDetailsPhone1TV)
        contactPhone1?.text = contact.phone

        val contactPhone2: TextView? = view?.findViewById(R.id.contactDetailsPhone2TV)
        contactPhone2?.text = contact.phoneSecondary

        val contactEmail1: TextView? = view?.findViewById(R.id.contactDetailsEmail1TV)
        contactEmail1?.text = contact.email

        val contactEmail2: TextView? = view?.findViewById(R.id.contactDetailsEmail2TV)
        contactEmail2?.text = contact.emailSecondary

        val description: TextView? = view?.findViewById(R.id.contactDetailsDescriptionTV)
        description?.text = contact.description
    }
}