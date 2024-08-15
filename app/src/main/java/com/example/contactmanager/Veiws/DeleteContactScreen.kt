package com.example.contactmanager.Veiws

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.contactmanager.MyVeiwModel.contactVeiwModel
import com.example.contactmanager.R
import com.example.contactmanager.room.contacts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteContactPanel(
    navController: NavController,
    veiwModel: contactVeiwModel,
    contact: contacts
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        Text(text = "Contact Details", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "See Your Contact Details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.contact_detail_icon),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            Button(onClick = { navController.navigate("editContact/${contact.id}") }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    )
    { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(start = 15.dp, top = 25.dp, end = 15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                elevation = CardDefaults.cardElevation(25.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.padding(top = 15.dp , bottom = 10.dp).border(width = 1.dp , color = Color.Black , shape = CircleShape)) {
                        Image(
                            painter = rememberAsyncImagePainter(model = contact.image),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(100.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Card(elevation = CardDefaults.cardElevation(15.dp) , modifier = Modifier.padding(top = 5.dp,bottom = 5.dp , start = 10.dp , end = 10.dp )) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)) {
                            Text(text = "Name :  ", fontSize = 20.sp  , fontWeight = FontWeight.Bold )
                            Text(text = contact.name , fontSize = 20.sp)
                        }
                    }
                    Card(elevation = CardDefaults.cardElevation(15.dp) , modifier = Modifier.padding(top = 5.dp,bottom = 5.dp , start = 10.dp , end = 10.dp)) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)) {
                            Text(text = "Phone :  " , fontSize = 20.sp  , fontWeight = FontWeight.Bold)
                            Text(text = contact.number , fontSize = 20.sp)
                        }
                    }
                    Card(elevation = CardDefaults.cardElevation(15.dp) , modifier = Modifier.padding(top = 5.dp,bottom = 5.dp , start = 10.dp , end = 10.dp)) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)) {
                            Text(text = "about :  ", fontSize = 20.sp  , fontWeight = FontWeight.Bold)
                            Text(text = contact.desc , fontSize = 20.sp)
                        }
                    }
                    Button(onClick = {
                        veiwModel.deleteContact(contact = contact)
                        navController.navigate("displayContacts")},
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(text = "Delete Contact")
                    }
                }
            }
        }
    }
}