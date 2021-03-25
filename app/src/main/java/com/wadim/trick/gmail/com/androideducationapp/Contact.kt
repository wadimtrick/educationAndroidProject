package com.wadim.trick.gmail.com.androideducationapp

import java.util.*

data class ContactShortInfo(val id: Int, val name: String, val phone: String, val imageId: Int)

data class ContactFullInfo(
    val id: Int, val name: String, val phone: String, val birthday: Calendar, val phoneSecondary: String, val email: String,
    val emailSecondary: String, val description: String, val imageId: Int)

data class ContactBirthdayInfo(val id: Int, val name: String, val birthday: Calendar, val imageId: Int) {
    constructor(contact: ContactFullInfo): this(contact.id, contact.name, contact.birthday, contact.imageId)
}