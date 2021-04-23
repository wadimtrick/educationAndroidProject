package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.presenters.PermissionWarningPresenter
import com.wadim.trick.gmail.com.androideducationapp.views.PermissionWarningView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

const val PERMISSION_REQUEST_CODE = 1231

class PermissionWarningFragment : MvpAppCompatFragment(R.layout.fragment_permission_warning), PermissionWarningView {
    @InjectPresenter
    lateinit var permissionWarningPresenter: PermissionWarningPresenter

    @ProvidePresenter
    fun providePresenter(): PermissionWarningPresenter = PermissionWarningPresenter()

    companion object {
        fun newInstance() = PermissionWarningFragment()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonOnClick()
    }

    override fun setToolbarTitle() {
        requireActivity().title = getString(R.string.permission_warning_title)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setButtonOnClick() {
        val permissionButton: Button? = view?.findViewById(R.id.permissionButton)
        permissionButton?.setOnClickListener {
            permissionWarningPresenter.buttonRequestPermissionClick()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onButtonClick() {
        requireActivity().requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), PERMISSION_REQUEST_CODE)
    }
}