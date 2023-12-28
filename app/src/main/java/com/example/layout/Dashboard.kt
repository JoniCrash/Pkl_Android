package com.example.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.layout.ui.theme.LayoutTheme

class Dashboard : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutTheme {
                val showMenu = remember { mutableStateOf(false) }

                TopAppBar(title = {
                    Text(text = "Dashboard")
                },
                    colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painterResource(id = R.drawable.baseline_menu_24),
                                contentDescription = null
                            )
                        }
//                        DropdownMenu(
//                            expanded = showMenu.value,
//                            onDismissRequest = { showMenu.value = false }) {
//                            DropdownMenuItem(
//                                text = {
//                                    Text(text = "Contoh 1")
//                                },
//                                onClick = { /*TODO*/ })
//                            DropdownMenuItem(
//                                text = {
//                                    Text(text = "Contoh 2")
//                                },
//                                onClick = { /*TODO*/ })
//                            DropdownMenuItem(
//                                text = {
//                                    Text(text = "Contoh 3")
//                                },
//                                onClick = { /*TODO*/ })
//                        }
                    },
                    actions = {
                        IconButton(onClick = { showMenu.value = true }) {
                            Icon(
                                painterResource(id = R.drawable.baseline_more_vert_24),
                                contentDescription = null
                            )
                        }
                        DropdownMenu(
                            expanded = showMenu.value,
                            onDismissRequest = { showMenu.value = false }) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = "Profil")
                                },
                                onClick = { /*TODO*/ })
                            DropdownMenuItem(
                                text = {
                                    Text(text = "Paket WiFi")
                                },
                                onClick = { /*TODO*/ })
                            DropdownMenuItem(
                                text = {
                                    Text(text = "Ajukan Pemasangan")
                                },
                                onClick = { /*TODO*/ })
                            DropdownMenuItem(
                                text = {
                                    Text(text = "Pembayaran")
                                },
                                onClick = { /*TODO*/ })
                        }
                    }

                )
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun dashboard() {
//    val showMenu = remember { mutableStateOf(false) }
//
//    TopAppBar(title = {
//        Text(text = "Dashboard")
//    },
//        colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
//        navigationIcon = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    painterResource(id = R.drawable.baseline_menu_24),
//                    contentDescription = null
//                )
//            }
//            DropdownMenu(
//                expanded = showMenu.value,
//                onDismissRequest = { showMenu.value = false }) {
//                DropdownMenuItem(
//                    text = {
//                           Text(text = "Contoh 1")
//                           },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Contoh 2")
//                    },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Contoh 3")
//                    },
//                    onClick = { /*TODO*/ })
//            }
//        },
//        actions = {
//            IconButton(onClick = { showMenu.value = true }) {
//                Icon(
//                    painterResource(id = R.drawable.baseline_more_vert_24),
//                    contentDescription = null
//                )
//            }
//            DropdownMenu(
//                expanded = showMenu.value,
//                onDismissRequest = { showMenu.value = false }) {
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Profil")
//                    },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Paket WiFi")
//                    },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Ajukan Pemasangan")
//                    },
//                    onClick = { /*TODO*/ })
//                DropdownMenuItem(
//                    text = {
//                        Text(text = "Pembayaran")
//                    },
//                    onClick = { /*TODO*/ })
//            }
//        }
//
//        )
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun dashboardPreview() {
//    LayoutTheme {
//        dashboard()
//    }
//}


//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    LayoutTheme {
//        Greeting("Android")
//    }
//}