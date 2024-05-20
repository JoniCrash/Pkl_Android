package com.example.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.layout.ui.theme.LayoutTheme
import com.example.layout.ui.theme.Noted
import com.example.layout.ui.theme.topAppBar

class Dashboard : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            
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
