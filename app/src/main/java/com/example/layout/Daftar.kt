package com.example.layout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.layout.ui.theme.LayoutTheme

class Daftar : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompDaftar()
        }
    }

}


@Preview
@Composable
private fun PrevCompDaftar() {
    CompDaftar()
}
@Composable
fun CompDaftar() {
    val context = LocalContext.current
    val networkManager = NetworkManager(context)
    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    val userError = user.isEmpty()
    val emailError = email.isEmpty()
    val pwError = pw.isEmpty()
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(
            vertical = 5.dp, horizontal = 5.dp
        )) {
        Spacer(
            modifier = Modifier
                .height(120.dp)
        )
        Image(
            painter = painterResource(R.drawable.comet_dark),
            contentDescription = null,
            modifier = Modifier
                //.align(Alignment.TopCenter)// Jika menggunakan Box
                .align(Alignment.CenterHorizontally)
                .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text="DAFTAR")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    //input text username
                    value = user,
                    onValueChange = {  user = it }, //newText -> user = newText
                    label = { Text("Username") },
                    placeholder = {Text( "Username") },
                    singleLine = true,
                    isError = userError,
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Person),
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // Text Input for Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    placeholder = { Text( "Email") },
                    singleLine = true,
                    isError = emailError,
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Email),
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // Text Input for Password
                OutlinedTextField(
                    value = pw,
                    onValueChange = {  pw = it },
                    label = { Text("Password") },
                    placeholder = {Text("Email")},
                    singleLine = true,
                    isError = pwError,
                    leadingIcon = {
                        Icon(
                            imageVector = (Icons.Default.Lock),
                            contentDescription = null
                        )
                    },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {}),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                // Button to Show Entered Text
                Button(
                    onClick = {
                              networkManager.userDaftar(object : NetworkCallback {
                                  override fun onSuccess(response: String) {
                                      Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                                      startActivity(context, Intent(context, Masuk::class.java), null)
                                  }

                                  override fun onFailure(error: Exception) {
                                      Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                                  }
                              })
                    },
                    enabled = true,
                    colors = buttonColors(colorResource(id = R.color.orange)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "DAFTAR",
                        color = colorResource(id = R.color.black))
                }
            }
        }
    }
}