package com.example.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.layout.ui.theme.LayoutTheme
import com.google.android.material.progressindicator.CircularProgressIndicator
import androidx.compose.runtime.livedata.observeAsState

class Profil : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutTheme {
                val id_user = intent.getIntExtra("USER_ID", -1)
                if (id_user != -1) {
                    ValidateUser(id_user)
                } else {
                    // Handle error case where userId is not provided
                }

            }
        }
    }
}

@Composable
fun ValidateUser(userId: Int, userViewModel: UserViewModel = viewModel()) {
    val user by userViewModel.user.observeAsState()
    val error by userViewModel.error.observeAsState()
    LaunchedEffect(userId) {
        userViewModel.fetchUserProfile(userId)
    }

        if (user != null) {
            profil(user = user!!)
        } else if (error != null) {
            Text(text = "{$error!!}")
        } else {
            CircularProgressIndicator()
        }

}

@Composable
fun profil(user: User) {
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
        Spacer(modifier = Modifier.padding(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = "Id: ${user.id}")
            Text(text = "Name: ${user.username}")
            Text(text = "Email: ${user.email}")
            Text(text = "Password: ${user.pass}")
        }
    }

}