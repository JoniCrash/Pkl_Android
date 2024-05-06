package com.example.layout

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager.Request
import android.content.Context
import android.content.Intent
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
import android.os.Looper
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.android.volley.toolbox.StringRequest
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.layout.ui.theme.LayoutTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class Pengajuan : ComponentActivity() {
//    var locationManager: LocationManager? = null

    val urlKirim = "http://192.168.22.2/CRUDVoley/insert.php"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            pengajuan()


            //
        }
    }

    fun inputData(){
        val queue = Volley.newRequestQueue(this)
        val kirimData = StringRequest(com.android.volley.Request.Method.GET, urlKirim,
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
                    this@Pengajuan, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this@Pengajuan, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100
                )
                return
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000,
                5f,
                object : LocationListener {
                    @Suppress("DEPRECATION")
                    override fun onLocationChanged(location: Location) {
                        // Mendapatkan latitude dan longitude
                        locationState.value = location
                        // Mendapatkan alamat
                        try {
                            val geocoder = Geocoder(this@Pengajuan, Locale.getDefault())
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

        val locationState = remember { mutableStateOf<Location?>(null) }
        val adres = remember { mutableStateOf<String?>(null) }
        val location = locationState.value
        val text = remember { mutableStateOf(false) }
        //val showMenu = remember { mutableStateOf(false) }
        //var showDialog by remember { mutableStateOf(false) }
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
                @Suppress("DEPRECATION")
                bitmap.value = getBitmap(context.contentResolver,it)
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
                    text = "FORM PENGAJUAN PEMASANGAN WIFI COMET")

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
                                namaLengkap = it},
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
                            singleLine = true,

                            )
                        OutlinedTextField(
                            value = adres.value?:"",
                            onValueChange = { text },
                            label = { Text("Alamat Pemasangan") },
                            singleLine = false)

                        Button(
                            onClick = {
                                GetLocation(locationState,adres)
                            }) {
                            Text(text = "Cari Lokasi")
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        //Awal Upload image

                        Box(
                            modifier = Modifier
                                .padding(start = 1.dp)
                        )
                        {
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .clickable { showimgg = true }
                            ) {
                                Text(
                                    text = "Upload KTP",
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Image(
                                bitmap = bitmap.value.asImageBitmap(),
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
                                    ))

                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(
                            modifier = Modifier
                                .padding(start = 1.dp)
                        )
                        {
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .clickable { showimgg = true }
                            ) {
                                BasicText(
                                    text = "Poto Depan Rumah")

                            }
                            Image(
                                bitmap = bitmap.value.asImageBitmap(),
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
                                    ))

                        }

//                    Button(onClick = {
//                        isUploading.value = true
//                        bitmap.value.let { bitmap ->
//                            uploadImageToFirebase(bitmap,context as ComponentActivity) { succes->
//                                isUploading.value = false
//                                if (succes){
//                                    Toast.makeText(context,"Berhasil upload",Toast.LENGTH_SHORT).show()
//                                }
//                                else {
//                                    Toast.makeText(context,"Gagal upload",Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                        }
//                    },
//                        colors = ButtonDefaults.buttonColors(
//                            Color.Blue
//                        )
//                    ) {
//                        Text(
//                            text = "Uploag Image",
//                            fontWeight = FontWeight.Bold)
//                    }
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Ambil Gambar")
                        }
                        // Akhir upload Image
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                      inputData()
                            },
                            enabled = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            Text(text = "PENGAJUAN")
                        }

                    }
                }
            }
        }
    }//Akhir FormPengauan

}


