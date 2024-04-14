package com.example.layout

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.Debug
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.app.ActivityCompat.RequestPermissionsRequestCodeValidator
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.location.LocationManagerCompat
import androidx.core.view.DragAndDropPermissionsCompat.request
import androidx.core.view.NestedScrollingParent3
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.Locale


class MainActivity : ComponentActivity()//, LocationListener
{
//    private lateinit var latitude: TextView
//    private lateinit var longitude: TextView
//    private lateinit var altitude: TextView
//    private lateinit var akurasi: TextView
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    lateinit var locationManager: LocationManager
var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
           aktif()
        }
    }


    @Composable
    fun aktif() {
        val locationState = remember { mutableStateOf<Location?>(null) }
        val adres = remember { mutableStateOf<String?>(null) }
        val location = locationState.value
        var text = remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = if (location != null) "${location.latitude}, ${location.longitude}" else "",
                onValueChange = { text },
                label = { Text("Titik Kordinat") },
                singleLine = true
            )
            TextField(
                value = adres.value?:"",
                onValueChange = { text },
                label = { Text("Alamat") },
                singleLine = false)

            Button(
                onClick = {
                    GetLocation(locationState, adres)
                }) {
                Text(text = "Cari Lokasi")
            }
        }
    }
    fun GetLocation(locationState: MutableState<Location?>,adres:MutableState<String?>) {
        try {
            locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

            if (ContextCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ), 100
                )
            }
            locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        //mengambil latitude dan longitude
                        locationState.value = location
                        //mengambil lokasi
                        try {
                            val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                            val addresses: MutableList<Address>? = geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            )
                            val address = addresses?.get(0)?.getAddressLine(0)
                            adres.value = address

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    // Implementasi metode lain dari LocationListener interface di sini
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




}



//            var latitude by remember { mutableStateOf("") }
//            var longitude by remember { mutableStateOf("") }
//            var altitude by remember { mutableStateOf("") }
//            var akurasi by remember { mutableStateOf("") }
//            val request :LocationRequest
//
//
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//            fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//                if (requestCode == 10) {
//                    if (ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        ) != PackageManager.PERMISSION_GRANTED &&
//                        ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                        ) != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        Toast.makeText(applicationContext, "Izin Lokasi Tidak Diaktifkan", Toast.LENGTH_SHORT).show()
//                    } else {
//                        getLocation()
//                    }
//                }
//            }

//            fun getLocation() {
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    ActivityCompat.requestPermissions(
//                        this, arrayOf(
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                        ), 10
//                    )
//                } else {
//                    fusedLocationClient.getLastLocation().addOnSuccessListener { location ->
//                        location?.let {
//                            latitude = it.latitude.toString()
//                            longitude = it.longitude.toString()
//                            altitude = it.altitude.toString()
//                            akurasi = "${it.accuracy}%"
//                        } ?: run {
//                            Toast.makeText(
//                                applicationContext, "Lokasi Tidak Aktif",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }.addOnFailureListener { e ->
//                        Toast.makeText(
//                            applicationContext, e.localizedMessage,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }







//            Screen()
//            daftar()
//            Masuk()
//            Greeting("Android")
//@Composable
//fun UserChat(name:String,msg:String) {
//    Row(modifier = Modifier.padding(4.dp)) {
//        Image(
//            painter = painterResource(R.drawable.comet_dark),
//            contentDescription = null,
//            modifier = Modifier
//                .sizeIn(70.dp, 70.dp, 70.dp, 70.dp)
//                .padding(4.dp)
//                .border(1.dp, color = Color.Black, CircleShape)
//                .clip(CircleShape)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Column(modifier = Modifier) {
//            Text(
//                text = name,
//                fontWeight = FontWeight.Bold
//            )
//            Text(text = msg, modifier = Modifier.offset(x = 15.dp))
//
//            Icon(
//                imageVector = Icons.Filled.Check,
//                contentDescription = null,
//                modifier = Modifier
//                    .align(Alignment.End)
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Column {
//
//
//                Button(onClick = { /*TODO*/ }) {
//                    PaddingValues(
//                        start = 20.dp,
//                        top = 20.dp,
//                        end = 20.dp,
//                        bottom = 20.dp
//                    )
//                    Icon(imageVector = Icons.Filled.Favorite,
//                        contentDescription = null,
//                        modifier = Modifier.size(ButtonDefaults.IconSize))
//                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
//                    Text(text = "Daftar")
//                    Text(text = "Saja")
//                }
//            }
//        }
//
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun UserChatPreview() {
//    LayoutTheme {
//        UserChat(name = "Joni Crash", msg ="Android Dev" )
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun daftar() {
//    var text by remember { mutableStateOf("") }
//    val daftar = "DAFTAR"
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(
//                vertical = 5.dp, horizontal = 5.dp
//            )
//    )
//    {
//        Spacer(modifier = Modifier
//            .height(120.dp)
//        )
//        Image(
//            painter = painterResource(R.drawable.comet_dark),
//            contentDescription = null,
//            modifier = Modifier
//                //.align(Alignment.TopCenter)// Jika menggunakan Box
//                .align(Alignment.CenterHorizontally)
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            //Text(text="DAFTAR")
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                OutlinedTextField(
//                    value = (text),
//                    onValueChange = { newText -> text = newText },
//                    label = { Text("Username") },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = (Icons.Default.Person),
//                            contentDescription = null
//                        )
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                )
//
//                // Text Input for Email
//                OutlinedTextField(
//                    value = text,
//                    onValueChange = { newText -> text = newText },
//                    label = { Text("Email") },
//                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        keyboardType = KeyboardType.Email,
//                        imeAction = ImeAction.Next
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                )
//
//                // Text Input for Password
//                OutlinedTextField(
//                    value = text,
//                    onValueChange = { newText -> text = newText },
//                    label = { Text("Password") },
//                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//                    visualTransformation = VisualTransformation.None,
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Done
//                    ),
//                    keyboardActions = KeyboardActions(onDone = {
//                        // Handle the "Done" action here
//                    }),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                )
//                // Button to Show Entered Text
//                Button(
//                    onClick = { },
//                    enabled = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp)
//                ) {
//                    Text(text="DAFTAR")
//                }
//            }
//        }
//    }
//}
//
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun daftarPreview(){
//    LayoutTheme {
//        daftar()
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Masuk() {
//    var text by remember { mutableStateOf("") }
//    Spacer(modifier = Modifier
//        )
//    Card(
//        modifier = Modifier
//
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(
//                vertical = 20.dp, horizontal = 5.dp
//            )
//    ) {
//        Image(
//            painter = painterResource(R.drawable.comet_dark),
//            contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            //Text(text="MASUK")
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                OutlinedTextField(
//                    value = (text),
//                    onValueChange = { newText -> text = newText },
//                    label = { Text("Username") },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = (Icons.Default.Person),
//                            contentDescription = null
//                        )
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                )
//
//                // Text Input for Password
//                OutlinedTextField(
//                    value = text,
//                    onValueChange = { newText -> text = newText },
//                    label = { Text("Password") },
//                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//                    visualTransformation = VisualTransformation.None,
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Done
//                    ),
//                    keyboardActions = KeyboardActions(onDone = {
//                        // Handle the "Done" action here
//                    }),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                )
//                // Button to Show Entered Text
//                Button(
//                    onClick = {
//                        // Use the entered text
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp)
//                ) {
//                    Text(text="MASUK")
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MasukPreview() {
//    LayoutTheme {
//        Masuk()
//    }
//}