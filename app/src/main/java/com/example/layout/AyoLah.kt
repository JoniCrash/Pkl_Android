package com.example.layout

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.EditText
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.android.volley.toolbox.ImageRequest


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

//@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun FormPengajuanPreview() {
//    FormPengajuan()
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun previeHomeScreen() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
        Box {
            Image(
                painter = painterResource(id = R.drawable.comet_dark),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize())

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
                        .background(Color.Blue)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = CircleShape
                        ))
            }

            var showDialog by remember { mutableStateOf(false) }

            Box(modifier = Modifier
                .padding(top = 280.dp, start = 260.dp))
            {
                Image(painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black)
                        .padding(10.dp)
                        .size(50.dp)
                        .clickable { showDialog = true }
                )
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp)
            ) {
                if (showDialog){
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
                                color = Color.White)
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
                                color = Color.White)
                        }
                        Column(modifier = Modifier
                            .padding(start = 50.dp, bottom = 80.dp))
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

    }

}

//Awal FormPengajaun
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun FormPengajuan() {

    val showMenu = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val pengajuan = remember { mutableStateListOf<ListPengajuan>() }
    val scrollState = rememberScrollState()

    // Loop untuk membuat RadioButton dengan TextField untuk setiap pilihan
    val opsiPaket = listOf("15 Mbps", "30 Mbps", "50 Mbps", "100 Mbps")
    // MutableState untuk menyimpan opsi yang dipilih
    var pilihanPaket by remember { mutableStateOf(opsiPaket[0]) }
    // MutableState untuk menyimpan teks dalam TextField
    var textValue by remember { mutableStateOf("") }

    var noHp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var namaLengkap by remember { mutableStateOf("") }
    var paket by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val matchError = remember { mutableStateOf(false) }
    val hasError = false

    var nameError by remember { mutableStateOf(false) }

//    fun validateName() {
//        nameError = name.isEmpty()
//    }
//
//        TextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Enter your name") },
//            isError = nameError,
//            singleLine = true
//        )
//
//        if (nameError) {
//            Text("Name cannot be empty", color = MaterialTheme.colors.error)
//        }
//
//        Button(onClick = { validateName() }) {
//            Text("Submit")
//        }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 0.1.dp, horizontal = 0.1.dp)
    )
    {
        Image(
            painter = painterResource(R.drawable.comet_dark),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(100.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Form Pengajuan Pemasangan WiFi")
            Card {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                )

                {

                    TextField(
                        value = namaLengkap,
                        label = {
                            Text(text = "Nama Lengkap")
                        },
                        placeholder = {
                            Text(text = "Nama Lengkap")
                        },
                        onValueChange = { namaLengkap = it },
                        leadingIcon = {
                            Icon(
                                imageVector = (Icons.Default.Person),
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = nik,
                        label = {
                            Text(text = "NIK")
                        },
                        placeholder = {
                            Text(text = "NIK")
                        },
                        onValueChange = { nik = it },
                        leadingIcon = {
                            Icon(
                                imageVector = (Icons.Default.Person),
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = noHp,
                        label = {
                            Text(text = "Nomor Hp")
                        },
                        placeholder = {
                            Text(text = "Nomor Hp")
                        },
                        onValueChange = { noHp = it },
                        leadingIcon = {
                            Icon(
                                imageVector = (Icons.Default.Phone),
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = email,
                        label = {
                            Text(text = "Email")
                        },
                        placeholder = {
                            Text(text = "Email")
                        },
                        onValueChange = { email = it },
                        leadingIcon = {
                            Icon(
                                imageVector = (Icons.Default.Email),
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(text = "Paket")
                            opsiPaket.forEach { pilihanya ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = pilihanPaket == pilihanya,
                                        onClick = { pilihanPaket = pilihanya },
                                        modifier = Modifier.padding(end = 1.dp)
                                    )
                                    Text(
                                        text = pilihanya,
                                        modifier = Modifier.padding(end = 1.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    TextField(
                        value = lokasi,
                        label = {
                            Text(text = "Lokasi Pemasangan")
                        },
                        placeholder = {
                            Text(text = "Lokasi Pemasangan")
                        },
                        onValueChange = { lokasi = it },
                        leadingIcon = {
                            Icon(
                                imageVector = (Icons.Default.LocationOn),
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = lokasi,
                        label = {
                            Text(text = "Lokasi Pemasangan")
                        },
                        placeholder = {
                            Text(text = "Lokasi Pemasangan")
                        },
                        onValueChange = { lokasi = it },
                        leadingIcon = {
                            Icon(
                                imageVector = (Icons.Default.LocationOn),
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Ambil Gambar")
                    }

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = (Icons.Default.Lock),
                                contentDescription = null
                            )
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            autoCorrect = true,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        isError = hasError || matchError.value,
                        visualTransformation =
                        if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            if (showPassword.value) {
                                Pair(
                                    R.drawable.baseline_remove_red_eye_24,
                                    colorResource(id = R.color.black)
                                )
                            } else Pair(Icons.Filled.Done, colorResource(id = R.color.white))
                            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                                    contentDescription = "Visibility"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                        },
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(text = "DAFTAR")
                    }

                }
            }
        }
    }
}//Akhir FormPengauan

@Composable
fun AyoLah() {
    val showDaftar = remember { mutableStateOf(false) }
    val showMasuk = remember { mutableStateOf(false) }



    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(5.dp),
                    shape = CircleShape,
                    border = BorderStroke(0.5.dp, Color.LightGray),
                    shadowElevation = 4.dp,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.comet_dark),
                        contentDescription = null,
                        contentScale = ContentScale.Crop)
                }
                Divider()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(5.dp),
                ) {
                    Text(
                        text = "JoniCrash",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row {
                        Button(onClick = { showDaftar.value = !showDaftar.value }) {
                            Text(text = "AYO DAFTAR")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(onClick = { showMasuk.value = !showMasuk.value }) {
                            Text(text = "AYO MASUK")
                        }
                    }
                    Column {
                        if (showDaftar.value){
                            AyoDaftar()
                        }else if (showMasuk.value){
                            AyoMasuk()
                        }else if (showDaftar.value && showMasuk.value){
                            AyoMasuk()
                        }else if (showMasuk.value && showDaftar.value){
                            AyoDaftar()
                        }
//                        if (showMasuk.value) {
//                            AyoMasuk()
//                        }
//                        if (showDaftar.value) {
//                            AyoMasuk()
//                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AyoDaftar() {
    var noHp by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }

    val showPassword = remember { mutableStateOf(false)}
    val focusManager = LocalFocusManager.current
    val matchError = remember { mutableStateOf(false) }
    val hasError  = false

    Surface(
        modifier = Modifier
//            .fillMaxSize()
            .fillMaxWidth()
            .height(320.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp, 10.dp, 10.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            )

            {
                Text(text = "Daftar")


                TextField(
                    value = noHp,
                    onValueChange = { noHp = it },
                    label = { Text("Nomor Hp") },
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Phone),
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("username") },
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Person),
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Lock),
                            contentDescription = null
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        autoCorrect = true,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    isError = hasError || matchError.value,
                    visualTransformation =
                    if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val (icon, iconColor) = (if (showPassword.value) {
                            Pair(
                                R.drawable.baseline_remove_red_eye_24,
                                colorResource(id = R.color.black)
                            )
                        } else Pair(Icons.Filled.Done, colorResource(id = R.color.white)))
                        IconButton(onClick = { showPassword.value = !showPassword.value }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                                contentDescription = "Visibility",
                                tint = iconColor
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
                Button(
                    onClick = {
                    }, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "AYO DAFTAR")
                }
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = keterangan,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Composable
fun AyoMasuk() {
    var noHp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }

    val showPassword = remember { mutableStateOf(false)}
    val focusManager = LocalFocusManager.current
    val matchError = remember { mutableStateOf(false) }
    val hasError  = false

    Surface(
        modifier = Modifier
//            .fillMaxSize()
            .fillMaxWidth()
            .height(255.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp, 10.dp, 10.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                TextField(
                    value = noHp,
                    onValueChange = { noHp = it },
                    label = { Text("Nomor Hp") },
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Phone),
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Lock),
                            contentDescription = null
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        autoCorrect = true,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    isError = hasError || matchError.value,
                    visualTransformation =
                    if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val (icon, iconColor) = (if (showPassword.value) {
                            Pair(
                                R.drawable.baseline_remove_red_eye_24,
                                colorResource(id = R.color.black)
                            )
                        } else Pair(Icons.Filled.Done, colorResource(id = R.color.white)))
                        IconButton(onClick = { showPassword.value = !showPassword.value }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                                contentDescription = "Visibility",
                                tint = iconColor
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
                Button(
                    onClick = {
                    }, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "AYO MASUK")
                }
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = keterangan,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

