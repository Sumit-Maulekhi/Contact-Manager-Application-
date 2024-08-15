package com.example.contactmanager.room

import android.provider.ContactsContract.DeletedContacts
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface methods {

    @Insert
    suspend fun InsertContact(contact: contacts)

    @Update
    suspend fun UpdateContact(contact: contacts)

    @Delete
    suspend fun DeletedContacts(contact: contacts)

    @Query("Select * from Contacts_list")
    fun GetAllContact():Flow<List<contacts>>
}