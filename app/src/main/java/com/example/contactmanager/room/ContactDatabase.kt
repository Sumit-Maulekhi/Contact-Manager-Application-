package com.example.contactmanager.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [contacts::class] , version = 1)
 abstract class ContactDatabase:RoomDatabase() {
     abstract fun methods():methods
}