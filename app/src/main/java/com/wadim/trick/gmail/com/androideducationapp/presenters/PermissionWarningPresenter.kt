package com.wadim.trick.gmail.com.androideducationapp.presenters

import android.os.Build
import androidx.annotation.RequiresApi
import com.wadim.trick.gmail.com.androideducationapp.views.PermissionWarningView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PermissionWarningPresenter() : MvpPresenter<PermissionWarningView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setToolbarTitle()
        viewState.setButtonOnClick()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun buttonRequestPermissionClick() {
        viewState.onButtonClick()
    }

}