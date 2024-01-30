package com.example.layout

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*
import java.util.logging.Handler


class ContohDaftar : ComponentActivity() {
    private val url = "http://192.168.22.2/CRUDVoley/insert.php" // Sesuaikan dengan alamat URL Anda


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            var nohp by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var keterangan by remember { mutableStateOf("") }

            //Logika untuk input data ke server
            fun inputData() {
                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(Request.Method.POST, url,
                    { response -> keterangan = response
                        },
                    { keterangan =  "ERROR TIDAK DAPAT INPUT DATA"}
                    )
                queue.add(stringRequest)
            }

            //----------------------------------//


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        vertical = 20.dp, horizontal = 5.dp
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.comet_dark),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "CONTOH DAFTAR")
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        TextField(
                            value = nohp,
                            onValueChange = { nohp = it },
                            label = { Text("Nomor Hp") },
                            leadingIcon = {
                                Icon(
                                    imageVector = (Icons.Default.Phone),
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
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
                            modifier = Modifier.fillMaxWidth()
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
                            modifier = Modifier.fillMaxWidth()
                        )
                        val coroutineScope = rememberCoroutineScope()
                        Button(
                            onClick = {
                                inputData()
                                coroutineScope.launch {
                                    delay(10000) // Waktu jeda dalam milidetik (misalnya, 2000 ms = 2 detik)
                                     // Panggil fungsi callback untuk berpindah ke halaman berikutnya

                                    val masuk =
                                        Intent(this@ContohDaftar,Masuk::class.java)
                                        startActivity(masuk)
                                        finish()
                                }
                            }, modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(text = "Input Data")
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                                text = keterangan,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                    }
                }
            }
        }
    }
}
