package com.example.layout

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.layout.ui.theme.LayoutTheme
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
//import androidx.compose.ui.window.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.layout.ui.theme.MyTheme
import java.io.ByteArrayOutputStream
import java.util.Locale

class CobaPengajuan : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutTheme {

            }
        }
    }


    private fun inputData() {
        val urlKirim = "http://192.168.22.2/CRUDVoley/insert.php"
        val queue = Volley.newRequestQueue(this)
        val kirimData = StringRequest(
            Request.Method.GET, urlKirim,
            { response -> Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show() },
            { Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show() })
        queue.add(kirimData)
    }

    private fun getLocation(
        locationState: MutableState<Location?>,
        addressState: MutableState<String?>
    ) {
        try {
            val locationManager =
                applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    100
                )
                return
            }

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5f,
                object : LocationListener {
                    @Suppress("DEPRECATION")
                    override fun onLocationChanged(location: Location) {
                        updateLocationState(locationState, addressState, location)
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    }

                    override fun onProviderEnabled(provider: String) {}

                    override fun onProviderDisabled(provider: String) {}
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateLocationState(
        locationState: MutableState<Location?>,
        addressState: MutableState<String?>,
        location: Location
    ) {
        locationState.value = location
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: List<Address>? =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            addressState.value =
                addresses?.getOrNull(0)?.getAddressLine(0) ?: "Tidak dapat menemukan alamat"
        } catch (e: Exception) {
            e.printStackTrace()
            addressState.value = "Terjadi kesalahan saat mendapatkan alamat"
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun PreviewPengajuan() {
        PengajuanScreen()
    }

    @Composable
    fun PengajuanScreen() {
        val locationState = remember { mutableStateOf<Location?>(null) }
        val addressState = remember { mutableStateOf<String?>(null) }
        val scrollState = rememberScrollState()
        val opsiPaket = listOf("15 Mbps", "30 Mbps", "50 Mbps", "100 Mbps")
        var pilihanPaket by remember { mutableStateOf(opsiPaket[0]) }
        var namaLengkap by remember { mutableStateOf("") }
        var nik by remember { mutableStateOf("") }
        var noHp by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            PengajuanHeader()
            PengajuanForm(
                scrollState,
                namaLengkap,
                nik,
                noHp,
                email,
                pilihanPaket,
                opsiPaket,
                locationState,
                addressState,
                focusManager,
                { namaLengkap = it },
                { nik = it },
                { noHp = it },
                { email = it },
                { pilihanPaket = it },
                { getLocation(locationState, addressState) }
            )
            PengajuanButton { inputData() }
        }
    }

    @Composable
    fun PengajuanHeader() {
        Column {
            Image(
                painter = painterResource(R.drawable.comet_dark),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasicText(
                text = "FORM PENGAJUAN PEMASANGAN WIFI COMET"
            )
        }
    }

    @Composable
    fun PengajuanForm(
        scrollState: ScrollState,
        namaLengkap: String,
        nik: String,
        noHp: String,
        email: String,
        pilihanPaket: String,
        opsiPaket: List<String>,
        locationState: MutableState<Location?>,
        addressState: MutableState<String?>,
        focusManager: FocusManager,
        onNamaLengkapChange: (String) -> Unit,
        onNikChange: (String) -> Unit,
        onNoHpChange: (String) -> Unit,
        onEmailChange: (String) -> Unit,
        onPaketChange: (String) -> Unit,
        onGetLocationClick: () -> Unit
    ) {
        val location = locationState.value
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            OutlinedTextField(
                value = namaLengkap,
                onValueChange = onNamaLengkapChange,
                label = { Text("Nama Lengkap") },
                placeholder = { Text("Nama Lengkap") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nik,
                onValueChange = onNikChange,
                label = { Text("NIK") },
                placeholder = { Text("NIK") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = noHp,
                onValueChange = onNoHpChange,
                label = { Text("Nomor Hp") },
                placeholder = { Text("Nomor Hp") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                placeholder = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            PaketSelection(opsiPaket, pilihanPaket, onPaketChange)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = if (location != null) "${location.latitude}, ${location.longitude}" else "",
                onValueChange = {},
                label = { Text("Titik Kordinat") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = addressState.value ?: "",
                onValueChange = {},
                label = { Text("Alamat Pemasangan") },
                leadingIcon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onGetLocationClick) {
                Text(text = "Cari Lokasi")
            }
            Spacer(modifier = Modifier.height(8.dp))
            UploadScreen()
        }
    }

    @Composable
    fun PaketSelection(
        opsiPaket: List<String>,
        pilihanPaket: String,
        onPaketChange: (String) -> Unit
    ) {
        Column {
            Text(text = "Paket")
            opsiPaket.forEach { paket ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = pilihanPaket == paket,
                        onClick = { onPaketChange(paket) }
                    )
                    Text(text = paket)
                }
            }
        }
    }

    @Composable
    fun UploadImageSection() {
        // Placeholder for Upload Image UI section.
    }

    @Composable
    fun UploadScreen() {
        val context = LocalContext.current
        var imageUri1 by remember { mutableStateOf<Uri?>(null) }
        var imageUri2 by remember { mutableStateOf<Uri?>(null) }
        var showDialog1 by remember { mutableStateOf(false) }
        var showDialog2 by remember { mutableStateOf(false) }

        val galleryLauncher1 =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri1 = uri
            }
        val galleryLauncher2 =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri2 = uri
            }
        val cameraLauncher1 =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
                bitmap?.let {
                    imageUri1 = getImageUri(context, it)
                }
            }
        val cameraLauncher2 =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
                bitmap?.let {
                    imageUri2 = getImageUri(context, it)
                }
            }

        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { showDialog1 = true }) {
                Text(text = "Select Image 1")
            }

            Spacer(modifier = Modifier.height(8.dp))

            imageUri1?.let {
                ImagePreview(uri = it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { showDialog2 = true }) {
                Text(text = "Select Image 2")
            }

            Spacer(modifier = Modifier.height(8.dp))

            imageUri2?.let {
                ImagePreview(uri = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                imageUri1?.let { uri1 ->
                    imageUri2?.let { uri2 ->
                        uploadImages(context, uri1, uri2)
                    }
                }
            }) {
                Text(text = "Upload Images")
            }

            if (showDialog1) {

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
                                    cameraLauncher1.launch()
                                    showDialog1 = false
                                }
                        )
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
                                    galleryLauncher1.launch("image/*")
                                    showDialog1 = false
                                }
                        )
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
                                .clickable { showDialog1 = false })
                    }
                }

//                ImagePickerDialog(
//                    onDismissRequest = { showDialog1 = false },
//                    onCameraClick = {
//                        cameraLauncher1.launch()
//                        showDialog1 = false
//                    },
//                    onGalleryClick = {
//                        galleryLauncher1.launch("image/*")
//                        showDialog1 = false
//                    }
//                )
            }

            if (showDialog2) {
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
                                    cameraLauncher2.launch()
                                    showDialog2 = false
                                }
                        )
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
                                    galleryLauncher2.launch("image/*")
                                    showDialog2 = false
                                }
                        )
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
                                .clickable { showDialog2 = false })
                    }
                }

//                ImagePickerDialog(
//                    onDismissRequest = { showDialog2 = false },
//                    onCameraClick = {
//                        cameraLauncher2.launch()
//                        showDialog2 = false
//                    },
//                    onGalleryClick = {
//                        galleryLauncher2.launch("image/*")
//                        showDialog2 = false
//                    }
//                )


            }
        }
    }


//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun ImagePickerDialog(
//        onDismissRequest: () -> Unit,
//        onCameraClick: () -> Unit,
//        onGalleryClick: () -> Unit
//    ) {
//        BasicAlertDialog(
//            onDismissRequest = onDismissRequest, // Wrap the title in a Composable
//              text = {// Add a "text" section for the buttons "sdd"
//                Column { //Use a Column to arrange buttons vertically
//                    Button(
//                        onClick = onCameraClick,
//                        modifier = Modifier.fillMaxWidth() // Make buttons fill width
//                    ) {
//                        Text("Camera")
//                    }
//                    Spacer(modifier = Modifier.height(8.dp)) // Add space between buttons
//                    Button(
//                        onClick = onGalleryClick,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Gallery")
//                    }
//                }
//            },
//            confirmButton = {
//                Button(onClick = onDismissRequest) {
//                    Text("Cancel")
//                }
//            },
//            modifier = Modifier.padding(16.dp) // Adjust padding as needed
//        )
//    }

    @Composable
    fun ImagePreview(uri: Uri) {
        val context = LocalContext.current
        val bitmap = remember { mutableStateOf<Bitmap?>(null) }

        LaunchedEffect(uri) {
            bitmap.value = if (Build.VERSION.SDK_INT < 28) {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        }

        bitmap.value?.let { btm ->
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }

    fun uploadImages(context: Context, uri1: Uri, uri2: Uri) {
        Toast.makeText(context, "Images uploaded successfully", Toast.LENGTH_SHORT).show()
    }

    fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }


    @Composable
    fun PengajuanButton(onClick: () -> Unit) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "PENGAJUAN", color = colorResource(id = R.color.black))
        }
    }
}
