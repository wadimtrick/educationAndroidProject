package com.wadim.trick.gmail.com.androideducationapp

import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import com.wadim.trick.gmail.com.androideducationapp.fragments.*
import com.wadim.trick.gmail.com.androideducationapp.interfaces.ClickableContactListElement
import com.wadim.trick.gmail.com.androideducationapp.presenters.MainPresenter
import com.wadim.trick.gmail.com.androideducationapp.views.MainView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MainActivity : MvpAppCompatActivity(R.layout.activity_main),
        ClickableContactListElement, MainView {
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = MainPresenter(applicationContext)

    private var isSavedInstanceEmpty = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isSavedInstanceEmpty = savedInstanceState == null
        mainPresenter.requestPermissions(intent.getStringExtra(CONTACT_ID_KEY), isSavedInstanceEmpty)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((requestCode == PERMISSION_REQUEST_CODE) && (!grantResults.contains(PERMISSION_DENIED)))
            mainPresenter.setMainContent(intent.getStringExtra(CONTACT_ID_KEY), isSavedInstanceEmpty)
    }

    override fun showContactDetails(contactID: String) {
        mainPresenter.setContactDetailsContent(contactID)
    }

    override fun showMap(contactID: String) {
        mainPresenter.setMapContent(contactID)
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

    override fun showMapFragment(contactId: String) {
        val fragment = MapFragment.newInstance(contactId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("mapFragment")
            .commit()
    }
}