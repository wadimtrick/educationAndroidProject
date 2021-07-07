package com.example.contactdetailsmodule.presentation

import com.example.contactdetailsmodule.domain.ContactDetailsInteractor
import com.example.coremodule.domain.ContactBirthdayInfo
import com.example.coremodule.domain.ContactFullInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ContactDetailsPresenter @Inject constructor(
    private val contactId: String,
    private val contactDetailsInteractor: ContactDetailsInteractor
) :
    MvpPresenter<ContactDetailsView>() {
    private var isFirstAttach = true
    private var contactBirthdayInfo: ContactBirthdayInfo? = null
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
        disposable = contactDetailsInteractor.getContactDetails(contactId)
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
                    viewState.showToast(it.message ?: contactDetailsInteractor.getErrorText())
                }
            )
    }

    private fun setSwitchChecked(contactFullInfo: ContactFullInfo) {
        val contactBirthdayInfo = ContactBirthdayInfo(contactFullInfo)
        viewState.setSwitchChecked(
            contactDetailsInteractor.isBirthdayNotificationPendingIntentExist(
                contactBirthdayInfo
            )
        )
    }

    fun onSwitchChanged(isChecked: Boolean) {
        contactBirthdayInfo?.let {
            contactDetailsInteractor.switchAlarmBirthdayNotification(
                isChecked,
                it
            )
        }
    }
}