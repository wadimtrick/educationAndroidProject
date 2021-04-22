package com.wadim.trick.gmail.com.androideducationapp.presenters

import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.util.Log
import com.wadim.trick.gmail.com.androideducationapp.repositories.ConfigRepository
import com.wadim.trick.gmail.com.androideducationapp.views.MainView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter(private val context: Context) : MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        ConfigRepository().createBirthdayNotificationChannel(context)
    }

    fun requestPermissions(contactId: String?, isSavedInstanceEmpty: Boolean) {
        when (SDK_INT >= M && context.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
            true -> viewState.showPermissionWarningFragment()
            false -> setMainContent(contactId, isSavedInstanceEmpty)
        }
    }

    fun setMainContent(contactId: String?, isSavedInstanceEmpty: Boolean) {
        if (!isSavedInstanceEmpty)
            return
        viewState.showContactListFragment()
        if (contactId != null)
            viewState.showContactDetailsFragment(contactId)
    }

    fun setContactDetailsContent(contactId: String) {
        viewState.showContactDetailsFragment(contactId)
    }
}