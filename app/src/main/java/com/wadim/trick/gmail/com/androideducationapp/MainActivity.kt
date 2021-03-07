package com.wadim.trick.gmail.com.androideducationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(),
    ClickableContactListElement {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            showContactList()
    }

    private fun showContactList() {
        val fragment = ContactListElementFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    override fun showContactDetails(contactID: Int) {
        showContactDetailsFragment(contactID)
    }

    private fun showContactDetailsFragment(contactID: Int) {
        val fragment = ContactDetailsFragment.newInstance(contactID)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("contactDetails")
            .commit()
    }
}