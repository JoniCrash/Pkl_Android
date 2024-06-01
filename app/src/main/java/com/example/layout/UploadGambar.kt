package com.example.layout

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.Volley
import com.example.layout.ui.theme.LayoutTheme
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.lang.System.*

class UploadGambar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutTheme {
                    UploadScreen()
            }
        }
    }
}

@Composable
fun UploadScreen() {
    val context = LocalContext.current
    var imageUri1 by remember { mutableStateOf<Uri?>(null) }
    var imageUri2 by remember { mutableStateOf<Uri?>(null) }

    val launcher1 = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri1 = uri
    }
    val launcher2 = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri2 = uri
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { launcher1.launch("image/*") }) {
            Text(text = "Select Image 1")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { launcher2.launch("image/*") }) {
            Text(text = "Select Image 2")
        }

        Spacer(modifier = Modifier.height(8.dp))

        imageUri1?.let {
            ImagePreview(uri = it)
        }

        imageUri2?.let {
            ImagePreview(uri = it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            imageUri1?.let { uri1 ->
                imageUri2?.let { uri2 ->
                    uploadImages(context, uri1, uri2)
                }
            }
        }) {
            Text(text = "Upload Images")
        }
    }
}

@Composable
fun ImagePreview(uri: Uri) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(uri) {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }

    bitmap.value?.let { btm ->
        Image(
            bitmap = btm.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}

fun uploadImages(context: Context, imageUri1: Uri, imageUri2: Uri) {
    val queue = Volley.newRequestQueue(context)
    val url = "http://your-server-url/upload.php" // ganti dengan URL server Anda

    val request = object :VolleyMultipartRequest(
        Method.POST,url,Response.Listener{
            Toast.makeText(context,"Berhasil:", Toast.LENGTH_SHORT
            ).show()
        },
        Response.ErrorListener{
            Toast.makeText(context,"Gagal:", Toast.LENGTH_SHORT
            ).show()
        }
    ){
        override fun getByteData(): Map<String, DataPart> {
            val params = HashMap<String, DataPart>()
            params["image1"] = DataPart(
                "image1.jpg",
                getFileDataFromDrawable(context, imageUri1)
            )
            params["image2"] = DataPart(
                "image2.jpg",
                getFileDataFromDrawable(context, imageUri2)
            )
            return params
        }

    }
    queue.add(request)
}

open class VolleyMultipartRequest(method: Int, url: String, listener: Response.Listener<NetworkResponse>, errorListener: Response.ErrorListener) : Request<NetworkResponse>(method, url, errorListener)  {
    private var responseHeaders: Map<String, String>? = null

    override fun getHeaders(): MutableMap<String, String> {
        return HashMap()
    }

    override fun getBodyContentType(): String {
        return "multipart/form-data;boundary=$boundary"
    }

    @Throws(AuthFailureError::class)
    override fun getBody(): ByteArray {
        val bos = ByteArrayOutputStream()
        val dos = DataOutputStream(bos)

        try {
            // Populate byte data
            for ((key, value) in getByteData()) {
                buildPart(dos, key, value)
            }

            // End of multipart/form-data.
            dos.writeBytes("--$boundary--")
            return bos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ByteArray(0)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
        responseHeaders = response.headers
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun deliverResponse(response: NetworkResponse) {

    }

    @Throws(AuthFailureError::class)
    protected open fun getByteData(): Map<String, DataPart> {
        return HashMap()
    }

    private fun buildPart(dos: DataOutputStream, parameterName: String, data: DataPart) {
        try {
            dos.writeBytes("--$boundary\r\n")
            dos.writeBytes("Content-Disposition: form-data; name=\"$parameterName\"; filename=\"${data.fileName}\"\r\n")
            dos.writeBytes("Content-Type: ${data.type}\r\n\r\n")
            dos.write(data.content)
            dos.writeBytes("\r\n")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    data class DataPart(val fileName: String, val content: ByteArray, val type: String = "image/jpeg")

    companion object {
        private val boundary = currentTimeMillis().toString() + nanoTime().toString()
    }
}

fun getFileDataFromDrawable(context: Context, uri: Uri): ByteArray {
    val bitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }

    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}