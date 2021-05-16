package com.wadim.trick.gmail.com.androideducationapp.models

import android.net.Uri
import java.util.Calendar

data class ContactShortInfo(val id: String, val name: String, val phone: String, val imageURI: Uri)

data class ContactFullInfo(
    val id: String, val name: String, val phone: String, val birthday: Calendar?, val phoneSecondary: String, val email: String,
    val emailSecondary: String, val description: String, internal val imageURI: Uri
)

data class ContactBirthdayInfo(val id: String, val name: String, val birthday: Calendar?) {
    constructor(contact: ContactFullInfo): this(contact.id, contact.name, contact.birthday)
}