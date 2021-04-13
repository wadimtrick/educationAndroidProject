package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.wadim.trick.gmail.com.androideducationapp.models.ContactShortInfo
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.interfaces.ClickableContactListElement
import com.wadim.trick.gmail.com.androideducationapp.presenters.ContactListPresenter
import com.wadim.trick.gmail.com.androideducationapp.views.ContactListView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ContactListFragment : MvpAppCompatFragment(R.layout.fragment_contact_list_element), ContactListView {
    @InjectPresenter
    lateinit var contactListPresenter: ContactListPresenter

    @ProvidePresenter
    fun providePresenter(): ContactListPresenter = ContactListPresenter(requireActivity().applicationContext)

    companion object {
        fun newInstance() = ContactListFragment()
    }

    override fun showContactList(contactShortInfoList: List<ContactShortInfo>) {
        val progressBar: ProgressBar? = view?.findViewById(R.id.contactProgressBar)
        progressBar?.isVisible = false

        if (contactShortInfoList.count() > 0) {
            fillDataForContact(contactShortInfoList.first())
            setOnClickShowDetails(contactShortInfoList.first().id)
        }
    }

    private fun fillDataForContact(contact: ContactShortInfo) {
        val contactPhotoView: ImageView? = view?.findViewById(R.id.contactPhotoImage)
        if (contact.imageURI != Uri.EMPTY)
            contactPhotoView?.setImageURI(contact.imageURI)
        else
            contactPhotoView?.setImageResource(R.drawable.ic_generic_avatar)

        val contactName: TextView? = view?.findViewById(R.id.contactNameTV)
        contactName?.text = contact.name

        val contactPhone: TextView? = view?.findViewById(R.id.contactPhoneTV)
        contactPhone?.text = contact.phone
    }

    override fun setToolbarTitle() {
        requireActivity().title = getString(R.string.contact_list_title)
    }

    override fun setOnClickShowDetails(contactId: String) {
        view?.setOnClickListener {
            contactListPresenter.onClickShowDetails(contactId)
        }
    }

    override fun onClickShowDetails(contactId: String) {
        (requireActivity() as ClickableContactListElement).showContactDetails(contactId)
    }

}