package com.example.layout

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layout.ui.theme.LayoutTheme

class ContohDashboard : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            var showMenu = remember { mutableStateOf(false) }
            contohDashboard()

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun contohDashboard() {
    var showMenu = remember { mutableStateOf(false) }

    TopAppBar(title = {
        Text(text = "Contoh Dashboard")
    },
        colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
        navigationIcon = {
            IconButton(onClick = { showMenu.value= true }) {
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

    //menu profile

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                vertical = 100.dp, horizontal = 5.dp
            )
    ) {
//        Image(
//            imageVector = (Icons.Default.AccountBox), contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally),
//        )

        Button(
            onClick = {
            },
            colors = ButtonDefaults.elevatedButtonColors(
                Color.Transparent
            )) {
            Image(
                painter = painterResource(id = R.drawable.comet_dark),
                contentDescription = null,
            )
        }
        Column {
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
        }
    }
}
@Preview (showBackground = true, showSystemUi = true)
@Composable
fun PreviewcontohDashboard() {
    contohDashboard()
}