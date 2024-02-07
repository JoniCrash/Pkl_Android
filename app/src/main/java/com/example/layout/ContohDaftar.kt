package com.example.layout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContohDaftar : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            var nohp by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var keterangan by remember { mutableStateOf("") }

            val urlInsert = "http://192.168.22.2/CRUDVoley/insert.php" // Sesuaikan dengan alamat URL Anda
            val coroutineScope = rememberCoroutineScope()
            val queue = Volley.newRequestQueue(this@ContohDaftar)
            val showPassword = remember { mutableStateOf(false)}
            val focusManager = LocalFocusManager.current
            val matchError = remember { mutableStateOf(false) }
            val hasError  = false

//            Logika input data ke server
            fun  inputData() {
            val stringRequest = StringRequest(Request.Method.POST, urlInsert,
                { response -> keterangan = "Berhasil mendaftar "
                    coroutineScope.launch {
                        delay(5000)
                    }
                    val masuk =
                        Intent(this,ContohDashboard::class.java)
                    startActivity(masuk)
                    finish()},
                {
                    keterangan =
                        "Eror input data! periksa koneksi anda, lalu ulangi"
                    coroutineScope.launch {
                        delay(5000) // Waktu jeda dalam milidetik (misalnya, 2000 ms = 2 detik)
                        // Panggil fungsi callback untuk berpindah ke halaman atau tindakan lain
                        nohp = ""
                        username = ""
                        password = ""
                        keterangan=""
                    }
                }
            )
            queue.add(stringRequest)
            }
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
                            .padding(16.dp)
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
                            onValueChange = { password = it  },
                            label = { Text("Password") },
                            leadingIcon = { Icon(
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
                                      inputData()
                            }, modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(text = "Daftar")
                        }
                        Spacer(modifier = Modifier.height(20.dp))

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
    }
}
