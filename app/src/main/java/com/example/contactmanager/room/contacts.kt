package com.example.contactmanager.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts_list")
data class contacts(
    @PrimaryKey val id:Int,
    var image:String?,
    var name:String,
    var number:String,
    var desc:String
)
