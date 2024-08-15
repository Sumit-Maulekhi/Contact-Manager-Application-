package com.example.contactmanager.Veiws

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.contactmanager.MyVeiwModel.contactVeiwModel
import com.example.contactmanager.R
import com.example.contactmanager.room.contacts
import com.example.contactmanager.ui.theme.GreenJc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactPanel(contact: contacts, viewModel: contactVeiwModel, navController: NavController) {
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var imagePath by remember {
        mutableStateOf(contact.image)
    }
    var name by remember {
        mutableStateOf<String>(contact.name)
    }
    var phoneno by remember {
        mutableStateOf<String>(contact.number)
    }
    var desc by remember {
        mutableStateOf<String>(contact.desc)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        Text(text = "Edit Contact", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "You can edit your contact here",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_contact_icon),
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
        }
    )
    { innerPadding ->
        Card(elevation = CardDefaults.cardElevation(40.dp) , modifier = Modifier.padding(15.dp)) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                ,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .border(color = Color.Black, width = 1.dp, shape = CircleShape)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imagePath),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        launcher.launch("image/*")
                        imageUri?.let {
                            imagePath = copyUritoInternalStorage(context, it, "$name.jpg")
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                )
                {
                    Text(text = "Choose Another pic")
                }
                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(value = name, label = "name", onTextChange = { name = it })
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(value = phoneno, label = "number", onTextChange = { phoneno = it })
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(value = desc, label = "about", onTextChange = { desc = it })
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        contact.name = name
                        contact.image = imagePath
                        contact.desc = desc
                        contact.number = phoneno
                        viewModel.updateContact(contact)
                        navController.navigate("displayContacts")
                    },
                    colors = ButtonDefaults.textButtonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White)
                )
                {
                    Text(text = "Update Contact")
                }

            }
        }
    }
}

@Composable
fun CustomTextField(value: String, label: String, onTextChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { it -> onTextChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 25.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        textStyle = MaterialTheme.typography.bodyLarge
    )
}
