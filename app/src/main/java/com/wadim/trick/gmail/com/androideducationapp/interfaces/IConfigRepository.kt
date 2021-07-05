package com.wadim.trick.gmail.com.androideducationapp.interfaces

interface IConfigRepository {
    fun createBirthdayNotificationChannel()
    fun checkSelfPermission(permission: String): Int
}