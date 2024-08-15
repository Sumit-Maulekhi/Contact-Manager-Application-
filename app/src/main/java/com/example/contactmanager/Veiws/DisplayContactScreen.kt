package com.example.contactmanager.Veiws


import android.content.Intent
import android.media.Image
import android.net.Uri
import android.provider.ContactsContract.Contacts
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.contactmanager.MyVeiwModel.contactVeiwModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.contactmanager.R
import com.example.contactmanager.room.contacts
import com.example.contactmanager.ui.theme.GreenJc


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayContactPanel(veiwModel: contactVeiwModel, navController: NavController) {
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
                        Text(text = "Contacts", fontSize = 25.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "your contact list",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.contact_icon),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
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
            Button(onClick = { navController.navigate("addContact") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
    { contentPadding ->
        val contact_list = veiwModel.allContacts.observeAsState(emptyList())
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .padding(10.dp)
                .background(color = Color.White)
        ) {
            items(contact_list.value) { contact ->
                DisplayEachContact(contact = contact, navController = navController)
            }
        }
    }
}

@Composable
fun DisplayEachContact(contact: contacts, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        val context = LocalContext.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("deleteContact/${contact.id}") },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter(model = contact.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .padding(5.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = contact.name,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 10.dp , start = 5.dp)
                )
            }

            IconButton(
                onClick = {
                    // Trigger the phone call intent
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.number}"))
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = GreenJc // Change the tint to match your theme color
                )
            }

        }
    }
}
