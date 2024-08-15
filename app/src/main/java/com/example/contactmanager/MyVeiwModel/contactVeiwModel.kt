package com.example.contactmanager.MyVeiwModel

import android.media.Image
import android.widget.ViewSwitcher.ViewFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactmanager.model.contactRepositry
import com.example.contactmanager.room.contacts
import kotlinx.coroutines.launch
import kotlin.random.Random

class contactVeiwModel(private val contactRepositry: contactRepositry):ViewModel() {

    val allContacts:LiveData<List<contacts>> = contactRepositry.allContacts.asLiveData()

    fun insertContact(image:String? , name:String , phone_no: String , desc:String){
        val contact:contacts = contacts(id = Random.nextInt(1,1000) , name = name, image = image , number = phone_no , desc = desc )
        viewModelScope.launch {
            contactRepositry.insertContact(contact = contact)
        }
    }

    fun updateContact(contact: contacts){
        viewModelScope.launch {
            contactRepositry.updateContact(contact = contact)
        }
    }

    fun deleteContact(contact: contacts){
        viewModelScope.launch {
            contactRepositry.deleteContact(contact)
        }
    }
}

class MyViewModelFactory(private val contactRepositry: contactRepositry) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(contactVeiwModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return contactVeiwModel(contactRepositry) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

