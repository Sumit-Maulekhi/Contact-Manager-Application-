package com.example.contactmanager.model

import com.example.contactmanager.room.contacts
import com.example.contactmanager.room.methods
import kotlinx.coroutines.flow.Flow

class contactRepositry(private val method: methods) {

    val allContacts:Flow<List<contacts>> = method.GetAllContact()


    suspend fun insertContact(contact:contacts){
        method.InsertContact(contact)
    }

    suspend fun deleteContact(contact: contacts){
        method.DeletedContacts(contact)
    }

    suspend fun updateContact(contact:contacts){
        method.UpdateContact(contact)
    }
}