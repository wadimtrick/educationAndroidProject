package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.concurrent.TimeUnit

class ContactListFragment : MvpAppCompatFragment(R.layout.fragment_contact_list_element),
    ContactListView {
    private var contactAdapter: ContactAdapter? = null
    private var disposable: Disposable? = null

    @InjectPresenter
    lateinit var contactListPresenter: ContactListPresenter

    @ProvidePresenter
    fun providePresenter(): ContactListPresenter =
        ContactListPresenter(requireActivity().applicationContext)

    companion object {
        fun newInstance() = ContactListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactAdapter =
            ContactAdapter { contactId -> contactListPresenter.onClickShowDetails(contactId) }
        setupContactRecyclerView()
        setOnSearchViewTextListener()
    }

    override fun onDestroyView() {
        contactAdapter = null
        disposable?.dispose()
        super.onDestroyView()
    }

    override fun showContactList(contactShortInfoList: List<ContactShortInfo>) {
        updateContactListForAdapter(contactShortInfoList)
    }

    private fun updateContactListForAdapter(contactList: List<ContactShortInfo>) =
        contactAdapter?.submitList(contactList)

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

    override fun setToolbarTitle() {
        requireActivity().title = getString(R.string.contact_list_title)
    }

    override fun showToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun setProgressBarVisible(isVisible: Boolean) {
        view?.findViewById<ProgressBar>(R.id.contactProgressBar)?.isVisible = isVisible
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
                    if (newText != null)
                        contactListPresenter.searchViewTextChanged(newText)
                    return true
                }
            }
        )
    }
}