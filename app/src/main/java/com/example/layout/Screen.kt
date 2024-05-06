package com.example.layout

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@Composable
fun Screen() {
    // Write a message to the database
    val database = Firebase.database
    val myRef = database.getReference("CID")

    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = {newText -> text = newText},
            label = {Text(text = "Ketik nama")}
        )
        Button(onClick = { myRef.setValue(text) },
            modifier = Modifier
                .padding(16.dp)) {
            Text(text = "Submit")
        }
    }
}