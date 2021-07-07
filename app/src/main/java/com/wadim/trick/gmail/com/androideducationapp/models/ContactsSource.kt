package com.wadim.trick.gmail.com.androideducationapp.models

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.example.coremodule.data.IContactsSource
import com.example.coremodule.domain.ContactFullInfo
import com.example.coremodule.domain.ContactShortInfo
import com.wadim.trick.gmail.com.androideducationapp.R
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ContactsSource @Inject constructor(private val context: Context) :
    IContactsSource {
    override fun getContactList(contactName: String): Single<List<ContactShortInfo>> {
        return Single.create {
            val contactList = mutableListOf<ContactShortInfo>()
            val cursor = getContacts(contactName)

            cursor.use { cursor ->
                if (cursor == null)
                    return@use
                try {
                    val contactIdColumnIndex =
                        cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID)
                    val nameColumnIndex =
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val photoUriColumnIndex =
                        cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
                    while (cursor.moveToNext()) {
                        val contactRawID =
                            cursor.getString(contactIdColumnIndex)
                        val contactName =
                            cursor.getString(nameColumnIndex)
                        val contactPhones = getContactAdditionalDataByRawId(
                            contactRawID,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                        )
                        val contactPhotoString = cursor.getString(photoUriColumnIndex) ?: ""
                        contactList.add(
                            ContactShortInfo(
                                contactRawID,
                                contactName,
                                contactPhones[0],
                                Uri.parse(contactPhotoString)
                            )
                        )
                    }

                } catch (e: Exception) {
                    it.onError(Throwable(context.resources.getString(R.string.contact_list_load_error)))
                }
            }
            it.onSuccess(contactList)
        }
    }

    override fun getContactDetails(contactID: String): Single<ContactFullInfo> {
        return Single.create {
            var contact: ContactFullInfo? = null
            try {
                val contactBirthday = getContactBirthdayCalendarByRawId(
                    contactID
                )
                val contactPhones = getContactAdditionalDataByRawId(
                    contactID,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                val contactEmails = getContactAdditionalDataByRawId(
                    contactID,
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                )
                val contactDescription = getContactAdditionalDataByRawId(
                    contactID,
                    ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE
                )
                val contactPhoto = getContactMainDataByRawId(
                    contactID,
                    ContactsContract.Data.PHOTO_URI
                )
                val contactName =
                    getContactMainDataByRawId(
                        contactID,
                        ContactsContract.Data.DISPLAY_NAME
                    )
                contact = ContactFullInfo(
                    contactID,
                    contactName,
                    contactPhones[0],
                    contactBirthday,
                    contactPhones[1],
                    contactEmails[0],
                    contactEmails[1],
                    contactDescription[0],
                    Uri.parse(contactPhoto)
                )
            } catch (e: Exception) {
                it.onError(Throwable(context.resources.getString(R.string.contact_details_load_error)))
            }
            it.onSuccess(contact)
        }
    }

    private fun getContacts(contactName: String): Cursor? {
        return try {
            context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                arrayOf(
                    ContactsContract.Contacts.NAME_RAW_CONTACT_ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_URI
                ),
                ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                arrayOf("$contactName%"),
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun getContactData(contactID: String, mime: String): Cursor? {
        return try {
            context.contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                arrayOf(ContactsContract.Data.DATA1),
                "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
                arrayOf(contactID, mime),
                null
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun getContactAdditionalDataByRawId(
        contactID: String,
        mime: String
    ): List<String> {
        val values = mutableListOf<String>()
        val cursor = getContactData(contactID, mime)
        var i = 0
        cursor.use { cursor ->
            if (cursor != null) {
                val dataColumnIndex = cursor.getColumnIndex(ContactsContract.Data.DATA1)
                while (cursor.moveToNext()) {
                    values.add(cursor.getString(dataColumnIndex) ?: "")
                    i++
                }
            }
        }
        while (values.size < 2)
            values.add("")
        return values
    }

    private fun getContactMainDataByRawId(contactID: String, field: String): String {
        var fieldValue = ""
        val cursor: Cursor?
        try {
            cursor = context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                "${ContactsContract.Data.NAME_RAW_CONTACT_ID} = $contactID",
                null,
                null
            )
        } catch (e: Exception) {
            return context.getString(R.string.main_data_missing)
        }
        cursor.use {
            cursor?.moveToFirst()
            fieldValue = cursor?.getString(cursor.getColumnIndex(field)) ?: ""
        }
        return fieldValue
    }

    private fun getContactBirthdayCalendarByRawId(contactID: String): Calendar? {
        val birthdayString = getContactAdditionalDataByRawId(
            contactID,
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
        )
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = try {
            sdf.parse(birthdayString[0])
        } catch (e: Exception) {
            null
        } ?: return null
        return Calendar.getInstance().apply { time = date }
    }
}