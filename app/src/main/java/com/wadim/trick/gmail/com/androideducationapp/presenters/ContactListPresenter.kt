package com.wadim.trick.gmail.com.androideducationapp.presenters

import android.content.Context
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.models.ContactsSource
import com.wadim.trick.gmail.com.androideducationapp.views.ContactListView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit

@InjectViewState
class ContactListPresenter(private val context: Context) : MvpPresenter<ContactListView>() {
    private var isFirstAttach = true
    private var contactsDisposable: Disposable? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var contactsSource = ContactsSource(context)
    private var observableEmitter: ObservableEmitter<String>? = null

    override fun attachView(view: ContactListView?) {
        super.attachView(view)
        createDisposableAndSearchObservable()
        if (isFirstAttach) {
            viewState.setToolbarTitle()
            getContactList("")
            isFirstAttach = false
        }
    }

    override fun detachView(view: ContactListView?) {
        compositeDisposable?.dispose()
        super.detachView(view)
    }

    private fun getContactList(contactName: String) {
        if (contactsDisposable != null)
            compositeDisposable?.remove(contactsDisposable)
        contactsDisposable = contactsSource.getContactList(contactName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.setProgressBarVisible(true) }
            .doFinally { viewState.setProgressBarVisible(false) }
            .subscribe(
                {
                    viewState.showContactList(it)
                },
                {
                    viewState.showToast(it.message ?: context.resources.getString(R.string.error))
                }
            )
        compositeDisposable?.add(contactsDisposable)
    }

    private fun createDisposableAndSearchObservable() {
        compositeDisposable = CompositeDisposable()
        setSearchViewObservable()
    }

    private fun setSearchViewObservable() {
        compositeDisposable?.add(
            Observable.create<String> {
                observableEmitter = it
            }
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    getContactList(it)
                }
        )
    }

    fun searchViewTextChanged(newText: String) {
        observableEmitter?.onNext(newText)
    }

    fun onClickShowDetails(contactId: String) {
        viewState.onClickShowDetails(contactId)
    }
}