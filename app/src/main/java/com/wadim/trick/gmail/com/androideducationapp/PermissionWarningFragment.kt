package com.wadim.trick.gmail.com.androideducationapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi

const val PERMISSION_REQUEST_CODE = 1231

class PermissionWarningFragment : Fragment(R.layout.fragment_permission_warning) {
    companion object {
        fun newInstance() = PermissionWarningFragment()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActivityTitle()
        val permissionButton: Button? = view?.findViewById(R.id.permissionButton)
        permissionButton?.setOnClickListener{
            requireActivity().requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), PERMISSION_REQUEST_CODE)
        }
    }

    private fun setActivityTitle() {
        requireActivity().title = getString(R.string.permission_warning_title)
    }
}