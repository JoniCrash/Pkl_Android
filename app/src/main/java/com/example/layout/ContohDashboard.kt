package com.example.layout

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.layout.ui.theme.Noted
import androidx.compose.runtime.setValue

class ContohDashboard : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            contohDashboard()

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun contohDashboard() {
    val showMenu = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val pengajuan = remember { mutableStateListOf<ListPengajuan>() }

    // Loop untuk membuat RadioButton dengan TextField untuk setiap pilihan
    val opsiPaket = listOf("15 Mbps", "30 Mbps", "50 Mbps", "100 Mbps")
    // MutableState untuk menyimpan opsi yang dipilih
    var pilihanPaket by remember { mutableStateOf(opsiPaket[0]) }
    // MutableState untuk menyimpan teks dalam TextField
    var textValue by remember { mutableStateOf("") }

    var noHp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var namaLengkap by remember { mutableStateOf("") }
    var paket by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                vertical = 0.1.dp, horizontal = 0.1.dp
            )
    ) {


        TopAppBar(title = {
            Text(text = "Contoh Dashboard")
        },
            colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
            navigationIcon = {
                IconButton(onClick = { showMenu.value = true }) {
                    Icon(
                        painterResource(id = R.drawable.baseline_menu_24),
                        contentDescription = null
                    )
                    // Menggunakan Garis 3
                }
                DropdownMenu(
                    expanded = showMenu.value,
                    onDismissRequest = { showMenu.value = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Emang boleh?")
                        },
                        onClick = { /*TODO*/ })

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )

                    DropdownMenuItem(
                        text = {
                            Text(text = "Emang boleh??")
                        },
                        onClick = { /*TODO*/ })

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )

                    DropdownMenuItem(
                        text = {
                            Text(text = "Emang boleh????")
                        },
                        onClick = { /*TODO*/ })

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )

                    DropdownMenuItem(
                        text = {
                            Text(text = "Emang boleh????")
                        },
                        onClick = { /*TODO*/ })
                }
                //akhir garis 3

            }
        )


        //akhir topappbar
//        Spacer(
//            modifier = Modifier
//                .height(20.dp)
//        )

        //menu profile
        Row {
            Button(
                onClick = {
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    Color.Transparent
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.comet_dark),
                    contentDescription = null,
                )

            }

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.elevatedButtonColors(
                    Color.Transparent
                )
            ) {
                Image(
                    imageVector = Icons.Default.AccountCircle, contentDescription = null,
                    Modifier.sizeIn(100.dp, 100.dp)
                )
            }
            IconButton(onClick = {}) {
                Image(
                    imageVector = Icons.Default.AccountBox, contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .fillMaxSize()

                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        Image(
            imageVector = Icons.Default.Build, contentDescription = null,

            modifier = Modifier
                .sizeIn(100.dp, 100.dp)
        )


        //Mulai dialog
        if (showDialog) {
            Dialog(onDismissRequest = {
                showDialog = false
            }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                )
                {
                    Column(Modifier.padding(16.dp)) {
                        TextField(
                            value = noHp,
                            label = {
                                Text(text = "Nomor Hp")
                            },
                            placeholder = {
                                Text(text = "Nomor Hp")
                            },
                            onValueChange = { noHp = it },
                            leadingIcon = {
                                Icon(
                                    imageVector = (Icons.Default.Phone),
                                    contentDescription = null
                                )
                            },
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = email,
                            label = {
                                Text(text = "Email")
                            },
                            placeholder = {
                                Text(text = "Email")
                            },
                            onValueChange = { email = it },
                            leadingIcon = {
                                Icon(
                                    imageVector = (Icons.Default.Email),
                                    contentDescription = null
                                )
                            },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = namaLengkap,
                            label = {
                                Text(text = "Nama Lengkap")
                            },
                            placeholder = {
                                Text(text = "Nama Lengkap")
                            },
                            onValueChange = { namaLengkap = it },
                            leadingIcon = {
                                Icon(
                                    imageVector = (Icons.Default.Person),
                                    contentDescription = null
                                )
                            },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
//                        TextField(
//                            value = paket,
//                            label = {
//                                Text(text = "Paket WiFi")
//                            },
//                            placeholder = {
//                                Text(text = "Paket WiFi")
//                            },
//                            onValueChange = { paket = it },
//                            leadingIcon = {
//                                Icon(
//                                    imageVector = (Icons.Default.ShoppingCart),
//                                    contentDescription = null
//                                )
//                            },
//                            singleLine = true
//                        )
                        //nyoba radio button

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            opsiPaket.forEach { pilihanya ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = pilihanPaket == pilihanya,
                                        onClick = { pilihanPaket = pilihanya },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = pilihanya,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    if (pilihanPaket == pilihanya) {
                                        TextField(
                                            value = textValue,
                                            onValueChange = { newValue -> textValue = newValue },
                                            leadingIcon = {
                                                    Icon(
                                                        imageVector = (Icons.Default.ShoppingCart),
                                                        contentDescription = null
                                                        )
                                                            },

                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = lokasi,
                            label = {
                                Text(text = "Lokasi")
                            },
                            placeholder = {
                                Text(text = "Lokasi")
                            },
                            onValueChange = { lokasi = it },
                            leadingIcon = {
                                Icon(
                                    imageVector = (Icons.Default.LocationOn),
                                    contentDescription = null
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Row(Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { showDialog = false })
                            {
                                Text(text = "Cancel")
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Button(onClick = {
                                showDialog = false
                                val note = ListPengajuan(
                                    noHp,
                                    email,
                                    namaLengkap,
                                    paket,
                                    lokasi,
                                )

                                pengajuan.add(note)
                                noHp = ""
                                email = ""
                                namaLengkap = ""
                                paket = ""
                                lokasi = ""
                            }) {
                                Text(text = "Save")
                            }
                        }
                    }
                }
            }
        }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    showDialog = true
                }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            },
        ) {
            Column(
                Modifier.padding(it).padding(16.dp),
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(pengajuan.size, key = { it }) { index ->
                        Card(
                            Modifier.fillMaxWidth()
                                .clickable {
                                    showDialog = true

                                    noHp = pengajuan[index].noHp
                                    email = pengajuan[index].email
                                    namaLengkap = pengajuan[index].namaLengkap
                                    paket = pengajuan[index].paket
                                    lokasi = pengajuan[index].lokasi

                                },
                        ) {
                            Row(Modifier.fillMaxWidth()) {
                                Column(
                                    Modifier.padding(8.dp)
                                        .weight(1f),
                                ) {
                                    Text(text = pengajuan[index].noHp)
                                    Text(text = pengajuan[index].email)
                                    Text(text = pengajuan[index].namaLengkap)
                                    Text(text = pengajuan[index].paket)
                                    Text(text = pengajuan[index].lokasi)

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview (showBackground = true, showSystemUi = true)
@Composable
fun PreviewcontohDashboard() {
    contohDashboard()
}