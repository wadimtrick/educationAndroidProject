package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wadim.trick.gmail.com.androideducationapp.models.ContactShortInfo
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.interfaces.ClickableContactListElement
import com.wadim.trick.gmail.com.androideducationapp.presenters.ContactListPresenter
import com.wadim.trick.gmail.com.androideducationapp.recycler.ContactAdapter
import com.wadim.trick.gmail.com.androideducationapp.recycler.ContactItemDecoration
import com.wadim.trick.gmail.com.androideducationapp.repositories.toDp
import com.wadim.trick.gmail.com.androideducationapp.views.ContactListView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ContactListFragment : MvpAppCompatFragment(R.layout.fragment_contact_list_element), ContactListView {
    private var contactAdapter: ContactAdapter? = null
    @InjectPresenter
    lateinit var contactListPresenter: ContactListPresenter

    @ProvidePresenter
    fun providePresenter(): ContactListPresenter = ContactListPresenter(requireActivity().applicationContext)

    companion object {
        fun newInstance() = ContactListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactAdapter = ContactAdapter { contactId -> contactListPresenter.onClickShowDetails(contactId) }
        setupContactRecyclerView()
        setOnSearchViewTextListener()
    }

    override fun onDestroyView() {
        contactAdapter = null
        super.onDestroyView()
    }

    override fun showContactList(contactShortInfoList: List<ContactShortInfo>) {
        val progressBar: ProgressBar? = view?.findViewById(R.id.contactProgressBar)
        progressBar?.isVisible = false
        updateContactListForAdapter(contactShortInfoList)
    }

    private fun updateContactListForAdapter(contactList: List<ContactShortInfo>) = contactAdapter?.submitList(contactList)

    private fun setupContactRecyclerView() {
        val recyclerView: RecyclerView = view?.findViewById(R.id.contactsRecyclerView) ?: return
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            if (adapter == null)
                adapter = contactAdapter
            val borderDrawable = resources.getDrawable(R.drawable.back, context?.theme)
            addItemDecoration(ContactItemDecoration(20.toDp(), borderDrawable))
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

    private fun setOnSearchViewTextListener() {
        val searchView = view?.findViewById<SearchView>(R.id.contactSearchView)
        searchView?.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        contactListPresenter.getContactList(newText ?: "")
                        return true
                    }

                }
        )
    }
}