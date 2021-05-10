package com.wadim.trick.gmail.com.androideducationapp.presenters

import android.content.Context
import com.wadim.trick.gmail.com.androideducationapp.ContactBirthdayNotificationManager
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.models.ContactBirthdayInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactsSource
import com.wadim.trick.gmail.com.androideducationapp.views.ContactDetailsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ContactDetailsPresenter(private val context: Context, private val contactId: String) :
    MvpPresenter<ContactDetailsView>() {
    private var isFirstAttach = true
    private var contactBirthdayInfo: ContactBirthdayInfo? = null
    private var contactBirthdayNotificationManager = ContactBirthdayNotificationManager()
    private var disposable: Disposable? = null

    override fun attachView(view: ContactDetailsView?) {
        super.attachView(view)
        if (isFirstAttach) {
            viewState.setToolbarTitle()
            setContactDetailsContent()
            isFirstAttach = false
        }

    }

    override fun detachView(view: ContactDetailsView?) {
        disposable?.dispose()
        super.detachView(view)
    }

    private fun setContactDetailsContent() {
        disposable = ContactsSource(context).getContactDetails(contactId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.setProgressBarVisible(true) }
            .doFinally { viewState.setProgressBarVisible(false) }
            .subscribe(
                {
                    contactBirthdayInfo = ContactBirthdayInfo(it)
                    viewState.showContactDetails(it)
                    setSwitchChecked(it)
                },
                {
                    viewState.showToast(it.message ?: context.resources.getString(R.string.error))
                }
            )

    }

    private fun setSwitchChecked(contactFullInfo: ContactFullInfo) {
        val contactBirthdayInfo = ContactBirthdayInfo(contactFullInfo)
        viewState.setSwitchChecked(
            contactBirthdayNotificationManager.isBirthdayNotificationPendingIntentExist(
                context,
                contactBirthdayInfo
            )
        )
    }

    fun onSwitchChanged(isChecked: Boolean) {
        contactBirthdayInfo?.let {
            contactBirthdayNotificationManager.switchAlarmBithdayNotification(
                context,
                isChecked,
                it
            )
        }
    }

    fun onShowMapButtonClick() {
        viewState.showMap(contactId)
    }
}