package com.wadim.trick.gmail.com.androideducationapp.presenters

import com.example.coremodule.data.IContactsSource
import com.example.coremodule.data.IStringManager
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
import javax.inject.Inject

@InjectViewState
class ContactListPresenter @Inject constructor(
    private val stringManager: IStringManager,
    private val contactsSource: IContactsSource
) : MvpPresenter<ContactListView>() {
    private var isFirstAttach = true
    private var contactsDisposable: Disposable? = null
    private var compositeDisposable: CompositeDisposable? = null
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
                    viewState.showToast(it.message ?: stringManager.getErrorText())
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