package com.wadim.trick.gmail.com.androideducationapp.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.gms.maps.SupportMapFragment
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.presenters.MapPresenter
import com.wadim.trick.gmail.com.androideducationapp.views.ContactMapView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

const val CONTACT_ID_KEY = "CONTACT_ID"

class MapFragment : MvpAppCompatFragment(R.layout.fragment_maps), ContactMapView {
    @InjectPresenter
    lateinit var mapPresenter: MapPresenter

    @ProvidePresenter
    fun providePresenter(): MapPresenter {
        val contactId = arguments?.getString(CONTACT_ID_KEY) ?: ""
        return MapPresenter(contactId)
    }
    companion object {
        fun newInstance(contactId: String) = MapFragment().apply {
            arguments = bundleOf(CONTACT_ID_KEY to contactId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(mapPresenter.callback)
    }

    override fun setToolbarTitle() {
        requireActivity().title = getString(R.string.map_title)
    }
}