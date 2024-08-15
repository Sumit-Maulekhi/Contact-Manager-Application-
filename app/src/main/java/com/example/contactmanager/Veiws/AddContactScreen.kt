package com.example.contactmanager.Veiws

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import com.example.contactmanager.MyVeiwModel.contactVeiwModel


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.contactmanager.R
import com.example.contactmanager.ui.theme.GreenJc
import java.io.File
import java.io.FileOutputStream
import java.net.URI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactPanel(viewModel: contactVeiwModel, navController: NavController) {

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var name by remember {
        mutableStateOf<String>("")
    }
    var phoneno by remember {
        mutableStateOf<String>("")
    }
    var desc by remember {
        mutableStateOf<String>("")
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

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
                        Text(text = "Add Contacts", fontSize = 18.sp)
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
                            painter = painterResource(id = R.drawable.add_contact_icon),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.add_image_logo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.textButtonColors(
                    MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            )
            {
                Text(text = "Add Image")
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextFields(value = name, label = "name", onTextChange = { name = it })
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextFields(value = phoneno, label = "number", onTextChange = { phoneno = it })
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextFields(value = desc, label = "about", onTextChange = { desc = it })
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    imageUri?.let { it ->
                        val imagepath = copyUritoInternalStorage(context, it, "$name.jpg")
                        viewModel.insertContact(
                            image = imagepath,
                            name = name,
                            phone_no = phoneno,
                            desc = desc
                        )
                        navController.navigate("displayContacts") {
                            popUpTo(0)
                        }
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            )
            {
                Text(text = "Save Contact")
            }

        }
    }
}

@Composable
fun CustomTextFields(value: String, label: String, onTextChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { it -> onTextChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 20.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        label = { Text(text = label) },
    )
}

fun copyUritoInternalStorage(context: Context, uri: Uri, filename: String): String? {
    val file = File(context.filesDir, filename)
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { OutputStream ->
                inputStream.copyTo(OutputStream)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        null
    }
}