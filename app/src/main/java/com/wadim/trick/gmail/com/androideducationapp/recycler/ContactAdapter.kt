package com.wadim.trick.gmail.com.androideducationapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.wadim.trick.gmail.com.androideducationapp.R
import com.wadim.trick.gmail.com.androideducationapp.models.ContactShortInfo

class ContactAdapter(private val elementOnClick: (String) -> Unit) : ListAdapter<ContactShortInfo, ContactViewHolder>(ContactDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_element, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            elementOnClick(getItem(position).id)
        }
    }
}