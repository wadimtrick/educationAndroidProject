package com.wadim.trick.gmail.com.androideducationapp

data class ContactShortInfo(val id: Int, val name: String, val phone: String, val imageId: Int)

data class ContactFullInfo(
    val id: Int, val name: String, val phone: String, val phoneSecondary: String, val email: String,
    val emailSecondary: String, val description: String, val imageId: Int)