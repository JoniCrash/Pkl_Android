package com.example.layout

import android.R.attr.label
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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


class Pengajuan : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            pppengajuan()
        }
    }

}
//fun GetLocation(locationState: MutableState<Location?>, addressState: MutableState<String?>) {
//    try {
//        val locationManager = applicationContext.getSystemService(Activity.LOCATION_SERVICE) as LocationManager
//        if (ContextCompat.checkSelfPermission(
//                this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100
//            )
//            return
//        }
//
//        locationManager.requestLocationUpdates(
//            LocationManager.GPS_PROVIDER,
//            5000,
//            5f,
//            object : LocationListener {
//                @Suppress("DEPRECATION")
//                override fun onLocationChanged(location: Location) {
//                    // Mendapatkan latitude dan longitude
//                    locationState.value = location
//                    // Mendapatkan alamat
//                    try {
//                        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
//                        val addresses: MutableList<Address>? = geocoder.getFromLocation(
//                            location.latitude,
//                            location.longitude,
//                            1
//                        )
//                        if (addresses != null && addresses.isNotEmpty()) {
//                            val address = addresses[0].getAddressLine(0)
//                            addressState.value = address
//                        } else {
//                            addressState.value = "Tidak dapat menemukan alamat"
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                        addressState.value = "Terjadi kesalahan saat mendapatkan alamat"
//                    }
//                }
//
//                @Deprecated("Deprecated in Java")
//                override fun onStatusChanged(provider: String?, status: Int, extra: Bundle?) {}
//                override fun onProviderEnabled(provider: String) {}
//                override fun onProviderDisabled(provider: String) {}
//            }
//        )
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}
val LocalLocationManagerHelper = staticCompositionLocalOf<LocationManagerHelper> {
    error("No LocationManagerHelper provided")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPengajuan() {
    pppengajuan()
}

@Composable
fun pppengajuan() {
    val context = LocalContext.current
    val locationManagerHelper = LocalLocationManagerHelper.current
    val activity = context as? MainActivity
    var location by remember { mutableStateOf<Location?>(null) }
    var address by remember { mutableStateOf<String?>(null) }
    var locationText by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("") }
    val locationState = remember { mutableStateOf<Location?>(null) }
    val adres = remember { mutableStateOf<String?>(null) }

    var showDialogKtp by remember { mutableStateOf(false) }
    var showDialogDepanRumah by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val opsiPaket = listOf("15 Mbps", "30 Mbps", "50 Mbps", "100 Mbps")
    var pilihanPaket by remember { mutableStateOf(opsiPaket[0]) }

    var namaLengkap by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val matchError = remember { mutableStateOf(false) }

    //Variabel validasi
    var namaLengkapError by remember { mutableStateOf(false) }
    namaLengkapError = namaLengkap.isEmpty()
    var nikError by remember { mutableStateOf(false) }
    nikError = nik.isEmpty()
    var noHpError by remember { mutableStateOf(false) }
    noHpError = noHp.isEmpty()
    var emailError by remember { mutableStateOf(false) }
    emailError = email.isEmpty()

    var showimgg by remember { mutableStateOf(false) }
    val isUploadingKtp = remember { mutableStateOf(false) }
    val isUploadingDepanRumah = remember { mutableStateOf(false) }

    val networkManager = NetworkManager(context)
    val imgKtp: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.baseline_person_24)
    val imgDepanRumah: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.baseline_menu_24)
    val bitmapKtp = remember { mutableStateOf(imgKtp) }
    val bitmapDepanRumah = remember { mutableStateOf(imgDepanRumah) }



    val launcherKtp = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        if (it != null) {
            bitmapKtp.value = it
        }
    }
    val launcherDepanRumah = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        if (it != null) {
            bitmapDepanRumah.value = it
        }
    }
    val launchImageKtp = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri ->
            val bitmapktp = if (Build.VERSION.SDK_INT < 28) {
                @Suppress("DEPRECATION")
                getBitmap(context.contentResolver, uri)
            } else {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            }
            bitmapKtp.value = bitmapktp
        }
    }
    val launchImageDepanRumah = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri ->
            val bitmapDepanRmh = if (Build.VERSION.SDK_INT < 28) {
                @Suppress("DEPRECATION")
                getBitmap(context.contentResolver, uri)
            } else {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            }
            bitmapDepanRumah.value = bitmapDepanRmh
        }
    }


    LaunchedEffect(Unit) {
        activity?.let {
            locationManagerHelper.checkLocationPermission(it)
            if (!locationManagerHelper.isLocationEnabled()) {
                locationManagerHelper.openLocationSettings()
            } else {
                locationManagerHelper.getLocation()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(R.drawable.comet_dark),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        BasicText(text = "FORM PENGAJUAN PEMASANGAN WIFI COMET")

        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                FormFields(
                    namaLengkap, { namaLengkap = it }, namaLengkapError,
                    nik, { nik = it }, nikError,
                    noHp, { noHp = it }, noHpError,
                    email, { email = it }, emailError,
                    locationText, { locationText = it },
                    addressText, { addressText = it },
                    lokasiEnabled = locationManagerHelper.isLocationEnabled(),
                    onGetLocation = {
                        locationManagerHelper.getLocation()
                        location?.let { locationText = " ${it.latitude}, ${it.longitude}" }
                        address?.let { addressText = it }
                    },
                    bitmapKtp, showDialogKtp, { showDialogKtp = it },
                    bitmapDepanRumah, showDialogDepanRumah, { showDialogDepanRumah = it },
                    launcherKtp, launchImageKtp, launcherDepanRumah, launchImageDepanRumah
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        networkManager.userPengajuan(object : NetworkCallback {
                            override fun onSuccess(response: String) {
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, Dashboard::class.java)
                                context.startActivity(intent)
                            }

                            override fun onFailure(error: Exception) {
                                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "PENGAJUAN", color = colorResource(id = R.color.black))
                }
            }
        }
    }
}

@Composable
fun FormFields(
    namaLengkap: String,
    onNamaLengkapChange: (String) -> Unit,
    namaLengkapError: Boolean,
    nik: String,
    onNikChange: (String) -> Unit,
    nikError: Boolean,
    noHp: String,
    onNoHpChange: (String) -> Unit,
    noHpError: Boolean,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: Boolean,
    locationText: String,
    onLocationTextChange: (String) -> Unit,
    addressText: String,
    onAddressTextChange: (String) -> Unit,
    lokasiEnabled: Boolean,
    onGetLocation: () -> Unit,
    bitmapKtp: MutableState<Bitmap>,
    showDialogKtp: Boolean,
    onShowDialogKtpChange: (Boolean) -> Unit,
    bitmapDepanRumah: MutableState<Bitmap>,
    showDialogDepanRumah: Boolean,
    onShowDialogDepanRumahChange: (Boolean) -> Unit,
    launcherKtp: ManagedActivityResultLauncher<Void?, Bitmap?>,
    launchImageKtp: ManagedActivityResultLauncher<String, Uri?>,
    launcherDepanRumah: ManagedActivityResultLauncher<Void?, Bitmap?>,
    launchImageDepanRumah: ManagedActivityResultLauncher<String, Uri?>
) {
    val opsiPaket = listOf("15 Mbps", "30 Mbps", "50 Mbps", "100 Mbps")
    var pilihanPaket by remember { mutableStateOf(opsiPaket[0]) }
    val context = LocalContext.current
    val locationManagerHelper = LocalLocationManagerHelper.current
    OutlinedTextField(
        value = namaLengkap,
        onValueChange = onNamaLengkapChange,
        isError = namaLengkapError,
        label = { Text(text = "Nama Lengkap") },
        placeholder = { Text(text = "Nama Lengkap") },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = nik,
        onValueChange = onNikChange,
        isError = nikError,
        label = { Text(text = "NIK") },
        placeholder = { Text(text = "NIK") },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = noHp,
        onValueChange = onNoHpChange,
        isError = noHpError,
        label = { Text(text = "Nomor Hp") },
        placeholder = { Text(text = "Nomor Hp") },
        leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        isError = emailError,
        label = { Text(text = "Email") },
        placeholder = { Text(text = "Email") },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))
    PaketOptions(pilihanPaket, { pilihanPaket = it })

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = locationText,
        onValueChange = onLocationTextChange,
        label = { Text("Lokasi") },
        leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = addressText,
        onValueChange = onAddressTextChange,
        label = { Text("Alamat Pemasangan") },
        leadingIcon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
        singleLine = false,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))
    Button(
        onClick = {
            if (!lokasiEnabled) {
                locationManagerHelper.openLocationSettings()
            } else {
                onGetLocation()
            }
        },
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
    ) {
        Text(text = "Cari Lokasi", color = colorResource(id = R.color.black))
    }

    Spacer(modifier = Modifier.height(16.dp))
    ImageUploadSection(
        "Upload KTP",
        bitmapKtp,
        showDialogKtp,
        onShowDialogKtpChange,
        launcherKtp,
        launchImageKtp
    )

    Spacer(modifier = Modifier.height(16.dp))
    ImageUploadSection(
        "Foto Depan Rumah",
        bitmapDepanRumah,
        showDialogDepanRumah,
        onShowDialogDepanRumahChange,
        launcherDepanRumah,
        launchImageDepanRumah
    )
}

@Composable
fun PaketOptions(pilihanPaket: String, onPilihanPaketChange: (String) -> Unit) {
    val opsiPaket = listOf("15 Mbps", "30 Mbps", "50 Mbps", "100 Mbps")
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Paket")
        opsiPaket.forEach { pilihanya ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = pilihanPaket == pilihanya,
                    onClick = { onPilihanPaketChange(pilihanya) },
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = pilihanya, modifier = Modifier.padding(end = 8.dp))
            }
        }
    }
}

@Composable
fun ImageUploadSection(
    label: String,
    bitmap: MutableState<Bitmap>,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    launcher: ManagedActivityResultLauncher<Void?, Bitmap?>,
    launchImage: ManagedActivityResultLauncher<String, Uri?>
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { onShowDialogChange(true) },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
        ) {
            Text(text = label, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.black))
        }
        Image(
            bitmap = bitmap.value.asImageBitmap(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 200.dp)
                .clip(RectangleShape)
                .size(width = 140.dp, height = 80.dp)
                .border(1.dp, Color.Black, RectangleShape)
        )
        if (showDialog) {
            UploadDialog(
                onCameraClick = {
                    launcher.launch()
                    onShowDialogChange(false)
                },
                onGalleryClick = {
                    launchImage.launch("image/*")
                    onShowDialogChange(false)
                },
                onDismiss = { onShowDialogChange(false) }
            )
        }
    }
}

@Composable
fun UploadDialog(onCameraClick: () -> Unit, onGalleryClick: () -> Unit, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .align(Alignment.Center)
                .clickable(enabled = false) { }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = onCameraClick) {
                        Icon(painter = painterResource(id = R.drawable.baseline_photo_camera_24), contentDescription = null, modifier = Modifier.size(50.dp))
                    }
                    Text(text = "Camera")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = onGalleryClick) {
                        Icon(painter = painterResource(id = R.drawable.baseline_image_24), contentDescription = null, modifier = Modifier.size(50.dp))
                    }
                    Text(text = "Gallery")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cancel",
                color = Color.Red,
                modifier = Modifier.clickable(onClick = onDismiss)
            )
        }
    }
}

