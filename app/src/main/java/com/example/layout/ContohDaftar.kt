package com.example.layout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class ContohDaftar : ComponentActivity() {
    private val url = "http://192.168.22.2/CRUDVoley/insert.php" // Sesuaikan dengan alamat URL Anda
    private lateinit var tket: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            var nim by remember { mutableStateOf("") }
            var nama by remember { mutableStateOf("") }
            var alamat by remember { mutableStateOf("") }
            var keterangan by remember { mutableStateOf("") }

            fun inputData() {
                val queue = Volley.newRequestQueue(this)
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        keterangan = response
                    },
                    Response.ErrorListener { error ->
                        keterangan = "ERROR TIDAK DAPAT INPUT DATA"
                    })
                {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["nim"] = nim
                        params["nama"] = nama
                        params["alamat"] = alamat
                        return params
                    }
                }
                queue.add(stringRequest)
            }


            //----------------------------------//


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = nim,
                    onValueChange = { nim = it },
                    label = { Text("Nim") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = alamat,
                    onValueChange = { alamat = it },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        val contohDaftar =
                            Intent(this@ContohDaftar, inputData()::class.java)
                    }, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Input Data")
                }


                Text(
                    text = keterangan,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
