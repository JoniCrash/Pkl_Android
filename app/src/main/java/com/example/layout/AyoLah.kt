package com.example.layout

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.*
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.location.Location
import android.location.LocationManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonColors
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.ImageRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream

//                    TextField(
//                        value = password,
//                        onValueChange = { password = it },
//                        label = { Text("Password") },
//                        leadingIcon = {
//                            Icon(
//                                imageVector = (Icons.Default.Lock),
//                                contentDescription = null
//                            )
//                        },
//                        keyboardActions = KeyboardActions(
//                            onDone = {
//                                focusManager.clearFocus()
//                            }
//                        ),
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType = KeyboardType.Text,
//                            autoCorrect = true,
//                            imeAction = ImeAction.Done
//                        ),
//                        singleLine = true,
//                        isError = hasError || matchError.value,
//                        visualTransformation =
//                        if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//                        trailingIcon = {
//                            if (showPassword.value) {
//                                Pair(
//                                    R.drawable.baseline_remove_red_eye_24,
//                                    colorResource(id = R.color.black)
//                                )
//                            } else Pair(Icons.Filled.Done, colorResource(id = R.color.white))
//                            IconButton(onClick = { showPassword.value = !showPassword.value }) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
//                                    contentDescription = "Visibility"
//                                )
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )



//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AyoLahPreview() {
//    AyoLah()
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AyoDaftarPreview() {
//    AyoDaftar()
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AyoMasukPreview() {
//    AyoMasuk()
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun previeHomeScreen() {
    HomeScreen()
}


//Awal Contoh Upload Image
@Composable
fun HomeScreen() {
        Box {
            Image(
                painter = painterResource(id = R.drawable.comet_dark),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize())

            val isUploading = remember { mutableStateOf(false) }
            val context = LocalContext.current
            val img : Bitmap = BitmapFactory.decodeResource(Resources.getSystem(),android.R.drawable.ic_menu_report_image)
            val bitmap = remember { mutableStateOf(img) }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicturePreview()) {
                if(it!= null){
                    bitmap.value = it
                }
            }
            val launchImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
                if (Build.VERSION.SDK_INT < 28){
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver,it)
                }
                else{
                    val source = it?.let { it1 ->
                        ImageDecoder.createSource(context.contentResolver,it1)
                    }
                    bitmap.value = source?.let { it1 ->
                        ImageDecoder.decodeBitmap(it1)
                    }!!
                }
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp)
            ) {
                Image(
                    bitmap = bitmap.value.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(250.dp)
                        //.background(Color.Blue)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = CircleShape
                        ))
            }

            var showDialog by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                .padding(top = 280.dp, start = 260.dp)
            )
            {
                Image(painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        //.background(Color.Black)
                        .padding(10.dp)
                        .size(50.dp)
                        .clickable { showDialog = true }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp)
            ) {
                Button(onClick = {
                    isUploading.value = true
                    bitmap.value.let { bitmap ->
                        uploadImageToFirebase(bitmap,context as ComponentActivity) { succes->
                            isUploading.value = false
                            if (succes){
                                Toast.makeText(context,"Berhasil upload",Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(context,"Gagal upload",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        Color.Blue
                    )
                    ) {
                    Text(
                        text = "Uploag Image",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp)
            )
            {
                if (showDialog) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Blue)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(60.dp)
                        ) {
                            Image(painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        launcher.launch()
                                        showDialog = false
                                    })
                            Text(
                                text = "Camera",
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.padding(30.dp))
                        Column {
                            Image(painter = painterResource(id = R.drawable.baseline_image_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        launchImage.launch("image/*")
                                        showDialog = false
                                    })
                            Text(
                                text = "Galeri",
                                color = Color.White
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 50.dp, bottom = 80.dp)
                        )
                        {
                            Text(
                                text = "X",
                                color = Color.White,
                                modifier = Modifier
                                    .clickable { showDialog = false })
                        }
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .height(450.dp)
            ) {
                if(isUploading.value){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp),
                        color = Color.White
                    )
                }
            }

    }

}

fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity,callback:(Boolean)->Unit) {
    val storageRef = Firebase.storage.reference
    val imageRef = storageRef.child("images/${bitmap}.jpg")
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
    val imageData = baos.toByteArray()

    imageRef.putBytes(imageData).addOnSuccessListener {
        callback(true)
    }.addOnFailureListener{
        callback(false)
    }
}
//Akhir Contoh Upload Image


//@Composable
//fun AyoLah() {
//    val showDaftar = remember { mutableStateOf(false) }
//    val showMasuk = remember { mutableStateOf(false) }
//
//
//
//    Surface(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(12.dp),
//            shape = RoundedCornerShape(15.dp),
//            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Surface(
//                    modifier = Modifier
//                        .size(150.dp)
//                        .padding(5.dp),
//                    shape = CircleShape,
//                    border = BorderStroke(0.5.dp, Color.LightGray),
//                    shadowElevation = 4.dp,
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.comet_dark),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop)
//                }
//                Divider()
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.padding(5.dp),
//                ) {
//                    Text(
//                        text = "JoniCrash",
//                        fontSize = 30.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                    Row {
//                        Button(onClick = { showDaftar.value = !showDaftar.value }) {
//                            Text(text = "AYO DAFTAR")
//                        }
//
//                        Spacer(modifier = Modifier.width(20.dp))
//
//                        Button(onClick = { showMasuk.value = !showMasuk.value }) {
//                            Text(text = "AYO MASUK")
//                        }
//                    }
//                    Column {
//                        if (showDaftar.value){
//                            AyoDaftar()
//                        }else if (showMasuk.value){
//                            AyoMasuk()
//                        }else if (showDaftar.value && showMasuk.value){
//                            AyoMasuk()
//                        }else if (showMasuk.value && showDaftar.value){
//                            AyoDaftar()
//                        }
////                        if (showMasuk.value) {
////                            AyoMasuk()
////                        }
////                        if (showDaftar.value) {
////                            AyoMasuk()
////                        }
//                    }
//                }
//            }
//        }
//    }
//}

//@Composable
//fun AyoDaftar() {
//    var noHp by remember { mutableStateOf("") }
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var keterangan by remember { mutableStateOf("") }
//
//    val showPassword = remember { mutableStateOf(false)}
//    val focusManager = LocalFocusManager.current
//    val matchError = remember { mutableStateOf(false) }
//    val hasError  = false
//
//    Surface(
//        modifier = Modifier
////            .fillMaxSize()
//            .fillMaxWidth()
//            .height(320.dp)
//            .padding(5.dp),
//        shape = RoundedCornerShape(15.dp),
//        border = BorderStroke(2.dp, Color.LightGray)
//    ) {
//        Column {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp, 5.dp, 10.dp, 10.dp),
//                shape = RoundedCornerShape(10.dp),
//                elevation = CardDefaults.cardElevation(4.dp)
//            )
//
//            {
//                Text(text = "Daftar")
//
//
//                TextField(
//                    value = noHp,
//                    onValueChange = { noHp = it },
//                    label = { Text("Nomor Hp") },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = (Icons.Default.Phone),
//                            contentDescription = null
//                        )
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Phone
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                )
//                TextField(
//                    value = username,
//                    onValueChange = { username = it },
//                    label = { Text("username") },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = (Icons.Default.Person),
//                            contentDescription = null
//                        )
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                )
//                TextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    label = { Text("Password") },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = (Icons.Default.Lock),
//                            contentDescription = null
//                        )
//                    },
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            focusManager.clearFocus()
//                        }
//                    ),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        autoCorrect = true,
//                        imeAction = ImeAction.Done
//                    ),
//                    singleLine = true,
//                    isError = hasError || matchError.value,
//                    visualTransformation =
//                    if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//                    trailingIcon = {
//                        val (icon, iconColor) = (if (showPassword.value) {
//                            Pair(
//                                R.drawable.baseline_remove_red_eye_24,
//                                colorResource(id = R.color.black)
//                            )
//                        } else Pair(Icons.Filled.Done, colorResource(id = R.color.white)))
//                        IconButton(onClick = { showPassword.value = !showPassword.value }) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
//                                contentDescription = "Visibility",
//                                tint = iconColor
//                            )
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                )
//                Button(
//                    onClick = {
//                    }, modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    Text(text = "AYO DAFTAR")
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Text(
//                    text = keterangan,
//                    textAlign = TextAlign.Center,
//                    color = Color.Red,
//                    modifier = Modifier
//                        .padding(top = 16.dp)
//                        .align(Alignment.CenterHorizontally),
//                )
//            }
//        }
//    }
//}

//@Composable
//fun AyoMasuk() {
//    var noHp by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var keterangan by remember { mutableStateOf("") }
//
//    val showPassword = remember { mutableStateOf(false)}
//    val focusManager = LocalFocusManager.current
//    val matchError = remember { mutableStateOf(false) }
//    val hasError  = false
//
//    Surface(
//        modifier = Modifier
////            .fillMaxSize()
//            .fillMaxWidth()
//            .height(255.dp)
//            .padding(5.dp),
//        shape = RoundedCornerShape(15.dp),
//        border = BorderStroke(2.dp, Color.LightGray)
//    ) {
//        Column {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp, 5.dp, 10.dp, 10.dp),
//                shape = RoundedCornerShape(10.dp),
//                elevation = CardDefaults.cardElevation(4.dp)
//            ) {
//                TextField(
//                    value = noHp,
//                    onValueChange = { noHp = it },
//                    label = { Text("Nomor Hp") },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = (Icons.Default.Phone),
//                            contentDescription = null
//                        )
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Phone
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                )
//
//                TextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    label = { Text("Password") },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = (Icons.Default.Lock),
//                            contentDescription = null
//                        )
//                    },
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            focusManager.clearFocus()
//                        }
//                    ),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        autoCorrect = true,
//                        imeAction = ImeAction.Done
//                    ),
//                    singleLine = true,
//                    isError = hasError || matchError.value,
//                    visualTransformation =
//                    if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//                    trailingIcon = {
//                        val (icon, iconColor) = (if (showPassword.value) {
//                            Pair(
//                                R.drawable.baseline_remove_red_eye_24,
//                                colorResource(id = R.color.black)
//                            )
//                        } else Pair(Icons.Filled.Done, colorResource(id = R.color.white)))
//                        IconButton(onClick = { showPassword.value = !showPassword.value }) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
//                                contentDescription = "Visibility",
//                                tint = iconColor
//                            )
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                )
//                Button(
//                    onClick = {
//                    }, modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    Text(text = "AYO MASUK")
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Text(
//                    text = keterangan,
//                    textAlign = TextAlign.Center,
//                    color = Color.Red,
//                    modifier = Modifier
//                        .padding(top = 16.dp)
//                        .align(Alignment.CenterHorizontally),
//                )
//            }
//        }
//    }
//}

