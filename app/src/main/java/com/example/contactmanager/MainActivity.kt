package com.example.contactmanager

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.contactmanager.MyVeiwModel.MyViewModelFactory
import com.example.contactmanager.MyVeiwModel.contactVeiwModel
import com.example.contactmanager.Veiws.AddContactPanel
import com.example.contactmanager.Veiws.DeleteContactPanel
import com.example.contactmanager.Veiws.DisplayContactPanel
import com.example.contactmanager.Veiws.EditContactPanel
import com.example.contactmanager.model.contactRepositry
import com.example.contactmanager.room.ContactDatabase
import com.example.contactmanager.ui.theme.ContactManagerTheme
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactManagerTheme {

                val database = Room.databaseBuilder(
                    applicationContext,
                    ContactDatabase::class.java,
                    "contactlists"
                ).build()

                val repositry = contactRepositry(database.methods())

                val veiwModel: contactVeiwModel by viewModels { MyViewModelFactory(repositry) }


                setContent {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "displayContacts") {
                        composable("displayContacts") {
                            DisplayContactPanel(
                                veiwModel = veiwModel,
                                navController = navController
                            )
                        }
                        composable("addContact") {
                            AddContactPanel(viewModel = veiwModel, navController = navController)
                        }
                        composable("deleteContact/{contactId}") {
                            val contactId = it.arguments?.getString("contactId")?.toInt()
                            val contact =
                                veiwModel.allContacts.observeAsState(initial = emptyList()).value.find { it.id == contactId }
                            if (contact != null) {
                                DeleteContactPanel(
                                    navController = navController,
                                    veiwModel = veiwModel,
                                    contact = contact
                                )
                            }
                        }
                        composable("editContact/{contactId}") {
                            val contactId = it.arguments?.getString("contactId")?.toInt()
                            val contact =
                                veiwModel.allContacts.observeAsState(initial = emptyList()).value.find { it.id == contactId }
                            if(contact!=null){
                                EditContactPanel(contact = contact, navController = navController, viewModel = veiwModel)
                            }

                        }

                    }
                }

            }
        }
    }
}








