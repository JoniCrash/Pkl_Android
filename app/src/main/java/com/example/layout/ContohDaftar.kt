package com.example.layout

import android.content.Intent
import java.util.HashMap
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.layout.ui.theme.LayoutTheme
import com.android.volley.toolbox.StringRequest as StringRequest


class ContohDaftar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutTheme {

                val queue = Volley.newRequestQueue(this)
                val url = ("http://192.168.1.101/CRUDVolley/insert.php")
                var nim by remember { mutableStateOf("") }
                var nama by remember { mutableStateOf("") }
                var alamat by remember { mutableStateOf("") }

                Text(text = "Contoh Daftar")
                Column {

                    // input text nim
                    TextField(
                        value = (nim),
                        onValueChange = { newText -> nim = newText },
                        label = { Text("Nim") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    // input text nama
                    TextField(
                        value = (nama),
                        onValueChange = { newText -> nama = newText },
                        label = { Text("Nama") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    // input text alamat
                    TextField(
                        value = (alamat),
                        onValueChange = { newText -> alamat = newText },
                        label = { Text("alamat") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    Button(
                        onClick = { queue
                        },
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(text = "DAFTAR")
                    }
                    val textView = findViewById<TextView>(
                        androidx.core.R.id.text
                    )
                    // Request a string response from the provided URL.
                    val stringRequest = StringRequest(Request.Method.POST, url,
                        { response ->
                            // Display the first 500 characters of the response string.
                            "Response is: ${response.substring(0, 500)}".also { textView.text = it }
                        },
                        { "That didn't work!".also { textView.text = it } })

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest)
                }
                fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    // Add your code to populate the params map if needed
                    params["nim"] = nim
                    params["Nama"] = nama
                    params["Alamat"] = alamat
                    return params
                }
            }
        }
    }
}