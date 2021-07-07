package com.wadim.trick.gmail.com.androideducationapp

import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.util.Log
import com.wadim.trick.gmail.com.androideducationapp.fragments.ContactDetailsFragment
import com.wadim.trick.gmail.com.androideducationapp.fragments.ContactListFragment
import com.wadim.trick.gmail.com.androideducationapp.fragments.PERMISSION_REQUEST_CODE
import com.wadim.trick.gmail.com.androideducationapp.fragments.PermissionWarningFragment
import com.wadim.trick.gmail.com.androideducationapp.interfaces.ClickableContactListElement
import com.wadim.trick.gmail.com.androideducationapp.presenters.MainPresenter
import com.wadim.trick.gmail.com.androideducationapp.views.MainView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(R.layout.activity_main),
    ClickableContactListElement, MainView {
    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = mainPresenter

    private var isSavedInstanceEmpty = true

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AppDelegate)
            .appComponent
            .inject(this)
        Log.e("testLog", "test")
        super.onCreate(savedInstanceState)

        isSavedInstanceEmpty = savedInstanceState == null
        mainPresenter.requestPermissions(
            intent.getStringExtra(CONTACT_ID_KEY),
            isSavedInstanceEmpty
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((requestCode == PERMISSION_REQUEST_CODE) && (!grantResults.contains(PERMISSION_DENIED)))
            mainPresenter.setMainContent(
                intent.getStringExtra(CONTACT_ID_KEY),
                isSavedInstanceEmpty
            )
    }

    override fun showContactDetails(contactID: String) {
        mainPresenter.setContactDetailsContent(contactID)
    }

    override fun showContactDetailsFragment(contactId: String) {
        val fragment = ContactDetailsFragment.newInstance(contactId)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("contactDetails")
            .commit()
    }

    override fun showContactListFragment() {
        val fragment = ContactListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun showPermissionWarningFragment() {
        val fragment = PermissionWarningFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}