package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.coremodule.domain.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.AppDelegate
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.presenters.ContactDetailsPresenter
import com.wadim.trick.gmail.com.androideducationapp.views.ContactDetailsView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

const val CONTACT_DETAILS_ARGUMENT_KEY = "CONTACT_DETAILS_ARGUMENT_KEY"

class ContactDetailsFragment : MvpAppCompatFragment(R.layout.fragment_contact_details),
    ContactDetailsView {

    @Inject
    @InjectPresenter
    lateinit var contactDetailsPresenter: ContactDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): ContactDetailsPresenter = contactDetailsPresenter

    companion object {
        fun newInstance(contactID: String) = ContactDetailsFragment().apply {
            arguments = bundleOf(CONTACT_DETAILS_ARGUMENT_KEY to contactID)
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (requireActivity().application as AppDelegate)
            .appComponent
            .plusContactDetailsComponent()
            .contactID(arguments?.getString(CONTACT_DETAILS_ARGUMENT_KEY) ?: "")
            .build()
            .inject(this)
    }

    override fun showContactDetails(contact: ContactFullInfo) {
        setNotifySwitchClickListener(contact)
        setContactPhoto(contact.imageURI)
        setContactBirthdayInfo(contact.birthday)
        setContactTextInfo(contact)
    }

    private fun setNotifySwitchClickListener(contact: ContactFullInfo) {
        if (contact.birthday == null)
            return
        val switch: Switch? = view?.findViewById(R.id.contactDetailsBirthdayNotifySwitch)
        switch?.isVisible = true
        switch?.setOnCheckedChangeListener { _, isChecked ->
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
            SimpleDateFormat(
                "d MMMM",
                Locale.getDefault()
            ).format(contactBirthday.time) else getString(R.string.date_missing)
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