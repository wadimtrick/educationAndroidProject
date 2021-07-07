package com.wadim.trick.gmail.com.androideducationapp.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.coremodule.domain.ContactShortInfo

class ContactDiffItemCallback : DiffUtil.ItemCallback<ContactShortInfo>() {
    override fun areItemsTheSame(oldItem: ContactShortInfo, newItem: ContactShortInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactShortInfo, newItem: ContactShortInfo): Boolean = oldItem == newItem
}