package com.wadim.trick.gmail.com.androideducationapp.presenters

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wadim.trick.gmail.com.androideducationapp.views.ContactMapView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MapPresenter(val contactId: String): MvpPresenter<ContactMapView>() {
    val callback = OnMapReadyCallback { googleMap ->
        val ulyanovsk = LatLng(54.318635, 48.397777)
        googleMap.addMarker(
            MarkerOptions()
            .position(ulyanovsk)
            .title("Ulyanovsk")
            .draggable(true))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ulyanovsk, 15F))
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun attachView(view: ContactMapView?) {
        super.attachView(view)
        viewState.setToolbarTitle()
    }
}