package com.example.contactlist.presentation.recycler

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.R
import com.example.coremodule.domain.ContactShortInfo

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var phoneTextView: TextView? = null
    private var nameTextView: TextView? = null
    private var photoImageView: ImageView? = null
    var contactId: String = ""

    init {
        with(itemView) {
            phoneTextView = findViewById(R.id.contactPhoneTV)
            photoImageView = findViewById(R.id.contactPhotoImage)
            nameTextView = findViewById(R.id.contactNameTV)
        }
    }

    fun bind(contact: ContactShortInfo) {
        with(contact) {
            phoneTextView?.text = phone
            nameTextView?.text = name
            if (imageURI != Uri.EMPTY)
                photoImageView?.setImageURI(imageURI)
            else
                photoImageView?.setImageResource(R.drawable.ic_generic_avatar)
            contactId = id
        }
    }
}