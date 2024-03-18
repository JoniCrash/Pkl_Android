package com.example.layout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = getColor(R.color.black)
            AyoLah()
//            Column(modifier = Modifier
//                .fillMaxWidth(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally) {
//                Image(
//                    painter = painterResource(R.drawable.comet_dark),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
//                )
//                Text(text = "SELAMAT DATANG",
//                    fontWeight = FontWeight.Bold
//                )
//                Button(onClick = {
//                    val daftar = Intent(this@MainActivity, ContohDaftar::class.java)
//                    startActivity(daftar)
//                },modifier = Modifier
//                    .fillMaxWidth()
//                    ) {
//                    Text(text ="Daftar",
//                        color = Color.White)
//                }
//                Button(onClick = {
//                    val masuk = Intent(this@MainActivity,Masuk::class.java)
//                    startActivity(masuk)
//                },
//                    modifier = Modifier
//                    .fillMaxWidth(),) {
//                    Text(text = "MASUK",
//                        color = Color.White)
//                }
//            }
        }
    }
}




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