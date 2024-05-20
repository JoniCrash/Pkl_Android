package com.example.layout

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.layout.ui.theme.MyTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    val urlKirim = "http://192.168.22.2/CRUDVoley/insert.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            pengajuan()
        }
    }

    fun inputData(){
        val queue = Volley.newRequestQueue(this)
        val kirimData = StringRequest(Request.Method.GET, urlKirim,
            { response -> Toast.makeText(this,"Berhasil",Toast.LENGTH_SHORT).show()
                // Display the first 500 characters of the response string.
            },
            { Toast.makeText(this,"gagal",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(kirimData)
        //
    }

    fun GetLocation(locationState: MutableState<Location?>, addressState: MutableState<String?>) {
        try {
            val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100
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
                        // Mendapatkan latitude dan longitude
                        locationState.value = location
                        // Mendapatkan alamat
                        try {
                            val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                            val addresses: MutableList<Address>? = geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            )
                            if (addresses != null && addresses.isNotEmpty()) {
                                val address = addresses[0].getAddressLine(0)
                                addressState.value = address
                            } else {
                                addressState.value = "Tidak dapat menemukan alamat"
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            addressState.value = "Terjadi kesalahan saat mendapatkan alamat"
                        }
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onStatusChanged(provider: String?, status: Int, extra: Bundle?) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun PreviewPengajuan() {
        pengajuan()
    }
    @Composable
    fun pengajuan() {
        MyTheme {


            val locationState = remember { mutableStateOf<Location?>(null) }
            val adres = remember { mutableStateOf<String?>(null) }
            val location = locationState.value
            val text = remember { mutableStateOf(false) }
            //val showMenu = remember { mutableStateOf(false) }
            var showDialogKtp by remember { mutableStateOf(false) }
            var showDialogDepanRumah by remember { mutableStateOf(false) }
            //val pengajuan = remember { mutableStateListOf<ListPengajuan>() }
            val scrollState = rememberScrollState()
            // Loop untuk membuat RadioButton dengan TextField untuk setiap pilihan
            val opsiPaket = listOf("15 Mbps", "30 Mbps", "50 Mbps", "100 Mbps")
            // untuk menyimpan opsi yang dipilih
            var pilihanPaket by remember { mutableStateOf(opsiPaket[0]) }
            // MutableState untuk menyimpan teks dalam TextField
            var textValue by remember { mutableStateOf("") }

            var namaLengkap by remember { mutableStateOf("") }
            var nik by remember { mutableStateOf("") }
            var noHp by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }

            var password by remember { mutableStateOf("") }
            val showPassword = remember { mutableStateOf(false) }
            val focusManager = LocalFocusManager.current
            val matchError = remember { mutableStateOf(false) }
            val hasError = false

            //Variabel validasi
            var namaLengkapError by remember { mutableStateOf(false) }
            namaLengkapError = namaLengkap.isEmpty()
            var nikError by remember { mutableStateOf(false) }
            nikError = nik.isEmpty()
            var noHpError by remember { mutableStateOf(false) }
            noHpError = noHp.isEmpty()
            var emailError by remember { mutableStateOf(false) }
            emailError = email.isEmpty()
            var locationStateEror by remember { mutableStateOf(false) }
            var adresEror by remember { mutableStateOf(false) }


            var showimgg by remember { mutableStateOf(false) }
            val isUploadingKtp = remember { mutableStateOf(false) }
            val isUploadingDepanRumah = remember { mutableStateOf(false) }
            val context = LocalContext.current
            val imgKtp: Bitmap = BitmapFactory.decodeResource(
                Resources.getSystem(),
                android.R.drawable.ic_menu_report_image
            )
            val imgDepanRumah: Bitmap = BitmapFactory.decodeResource(
                Resources.getSystem(),
                android.R.drawable.ic_menu_report_image
            )
            val bitmapKtp = remember { mutableStateOf(imgKtp) }
            val bitmapDepanRumah = remember { mutableStateOf(imgDepanRumah) }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicturePreview()
            ) {
                if (it != null) {
                    bitmapKtp.value = it
                    bitmapDepanRumah.value = it
                }
            }
            val launchImage =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
                    if (Build.VERSION.SDK_INT < 28) {
                        @Suppress("DEPRECATION")
                        bitmapKtp.value =
                            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                        bitmapDepanRumah.value =
                            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = it?.let { it1 ->
                            ImageDecoder.createSource(context.contentResolver, it1)
                        }
                        bitmapKtp.value = source?.let { it1 ->
                            ImageDecoder.decodeBitmap(it1)
                        }!!
                        bitmapDepanRumah.value = source?.let { it1 ->
                            ImageDecoder.decodeBitmap(it1)
                        }!!
                    }
                }

            //Validasi jika texfield kosong

//    fun validasiJikaKosong(): Boolean {
////                true -> Text("NIK tidak boleh kosong", color = MaterialTheme.colorScheme.error)
////                false -> Text("")
//
//        when (namaLengkapError) {
//            true -> namaLengkap = ""
//            false -> namaLengkapError
//        }
//        when (nikError) {
//            true -> nik = ""
//            false -> nikError
//        }
//        when (noHpError) {
//            true -> noHp = ""
//            false -> noHpError
//        }
//        when (emailError) {
//            true -> email = ""
//            false -> emailError
//        }
//        return validasiJikaKosong()
//    }

            //Akhir Validasi jika textfield kosong

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
                    BasicText(
                        text = "FORM PENGAJUAN PEMASANGAN WIFI COMET"
                    )

                    Card {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .verticalScroll(scrollState)
                                .fillMaxSize()
                        )
                        {
                            OutlinedTextField(
                                value = namaLengkap,
                                onValueChange = {
                                    namaLengkap = it
                                },
                                isError = namaLengkapError,
                                label = {
                                    Text(text = "Nama Lengkap")
                                },
                                placeholder = {
                                    Text(text = "Nama Lengkap")
                                },
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
                            OutlinedTextField(
                                value = nik,
                                onValueChange = { nik = it },
                                isError = nikError,
                                label = {
                                    Text(text = "NIK")
                                },
                                placeholder = {
                                    Text(text = "NIK")
                                },

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
                            OutlinedTextField(
                                value = noHp,
                                onValueChange = { noHp = it },
                                isError = noHpError,
                                label = {
                                    Text(text = "Nomor Hp")
                                },
                                placeholder = {
                                    Text(text = "Nomor Hp")
                                },

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
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                isError = emailError,
                                label = {
                                    Text(text = "Email")
                                },
                                placeholder = {
                                    Text(text = "Email")
                                },

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
                            OutlinedTextField(
                                value = if (location != null) "${location.latitude}, ${location.longitude}" else "",
                                onValueChange = { text },
                                label = { Text("Titik Kordinat") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = (Icons.Default.LocationOn),
                                        contentDescription = null
                                    )
                                },
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = adres.value ?: "",
                                onValueChange = { text },
                                label = { Text("Alamat Pemasangan") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = (Icons.Default.Home),
                                        contentDescription = null
                                    )
                                },
                                singleLine = false
                            )
                            Button(
                                onClick = { GetLocation(locationState, adres) },
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange)),
                            ) {
                                Text(
                                    text = "Cari Lokasi",
                                    color = colorResource(id = R.color.black)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            //Awal Upload image

                            Box(
                                modifier = Modifier
                                    .padding(start = 1.dp)
                            )
                            {
                                Button(
                                    onClick = { showDialogKtp = true },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
                                ) {
                                    Text(
                                        text = "Upload KTP",
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.black)
                                    )

                                }
                                Image(
                                    bitmap = bitmapKtp.value.asImageBitmap(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 200.dp)
                                        .clip(RectangleShape)
                                        .size(width = 140.dp, height = 80.dp)
                                        //.background(Color.Blue)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RectangleShape
                                        )
                                )
                                if (showDialogKtp) {
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
                                                        showDialogKtp = false
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
                                                        showDialogKtp = false
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
                                                    .clickable { showDialogKtp = false })
                                        }
                                    }
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    if (isUploadingKtp.value) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .padding(16.dp),
                                            color = Color.White
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .padding(start = 1.dp)
                            )
                            {
                                Button(
                                    onClick = { showDialogDepanRumah = true },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange)),
                                ) {
                                    Text(
                                        text = "Poto Depan Rumah",
                                        color = colorResource(id = R.color.black)
                                    )
                                }
                                Image(
                                    bitmap = bitmapDepanRumah.value.asImageBitmap(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 200.dp)
                                        .clip(RectangleShape)
                                        .size(width = 140.dp, height = 80.dp)
                                        //.background(Color.Blue)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RectangleShape
                                        )
                                )

                                if (showDialogDepanRumah) {
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
                                                        showDialogDepanRumah = false
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
                                                        showDialogDepanRumah = false
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
                                                    .clickable { showDialogDepanRumah = false })
                                        }
                                    }
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    if (isUploadingDepanRumah.value) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .padding(16.dp),
                                            color = Color.White
                                        )
                                    }
                                }

                            }
                            // Akhir upload Image
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    inputData()
                                },
                                enabled = true,
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                Text(
                                    text = "PENGAJUAN",
                                    color = colorResource(id = R.color.black)
                                )
                            }

                        }
                    }
                }
            }
        }
    }//Akhir FormPengauan
}
