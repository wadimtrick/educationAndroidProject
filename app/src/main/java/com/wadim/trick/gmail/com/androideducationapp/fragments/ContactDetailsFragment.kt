package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.net.Uri
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.interfaces.ClickableContactListElement
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
        setNotifySwitchClickListener(contact)
        setContactPhoto(contact.imageURI)
        setContactBirthdayInfo(contact.birthday)
        setContactTextInfo(contact)
        setShowMapButtonClickListener()
    }

    private fun setNotifySwitchClickListener(contact: ContactFullInfo) {
        if (contact.birthday == null)
            return
        val switch: Switch? = view?.findViewById(R.id.contactDetailsBirthdayNotifySwitch)
        switch?.isVisible = true
        switch?.setOnCheckedChangeListener { buttonView, isChecked ->
            onSwitchChanged(isChecked)
        }
    }

    override fun setToolbarTitle() {
        requireActivity().title = getString(R.string.contact_details_title)
    }

    override fun setProgressBarVisible(isVisible: Boolean) {
        view?.findViewById<ProgressBar>(R.id.contactDetailsProgressBar)?.isVisible = isVisible
    }

    override fun setSwitchChecked(isChecked: Boolean) {
        val switch: Switch? = view?.findViewById(R.id.contactDetailsBirthdayNotifySwitch)
        switch?.isChecked = isChecked
    }

    private fun onSwitchChanged(isChecked: Boolean) {
        contactDetailsPresenter.onSwitchChanged(isChecked)
    }

    override fun showToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showMap(contactId: String) {
        (requireActivity() as ClickableContactListElement).showMap(contactId)
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

    private fun setShowMapButtonClickListener() {
        val button = view?.findViewById(R.id.buttonShowMap) as Button
        button?.setOnClickListener {
            contactDetailsPresenter.onShowMapButtonClick()
        }
    }
}