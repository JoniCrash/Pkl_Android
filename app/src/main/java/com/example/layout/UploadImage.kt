package com.example.layout

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.DATA
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.layout.ui.theme.LayoutTheme
import net.gotev.uploadservice.MultipartUploadRequest
import net.gotev.uploadservice.UploadNotificationConfig
import java.io.IOException
import java.util.UUID

class UploadImage : ComponentActivity(), View.OnClickListener {
    //Declaring views
    private var buttonChoose: Button? = null
    private var buttonUpload: Button? = null
    private var imageView: ImageView? = null
//    private var editText: EditText? = null

    //Image request code
    private val PICK_IMAGE_REQUEST = 1

    private val STORAGE_PERMISSION_CODE = 123

    //Bitmap to get image from gallery
    private var bitmap: Bitmap? = null
    //Uri to store the image uri
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    requestStoragePermission()

                    var imageName by remember { mutableStateOf("") }


                    buttonChoose!!.setOnClickListener(this)
                    buttonUpload!!.setOnClickListener(this)
                    //
                    fun uploadMultipart() {
                        //getting name for the image
                        val name: String = imageName.trim { it <= ' ' }

                        //getting the actual path of the image
                        val path = getPath(filePath)

                        //Uploading code
                        try {
                            val uploadId = UUID.randomUUID().toString()

                            //Creating a multi part request
                            MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                                .addFileToUpload(path, "image") //Adding file
                                .addParameter("name", name) //Adding text parameter to the request
                                .setNotificationConfig(UploadNotificationConfig())
                                .setMaxRetries(2)
                                .startUpload() //Starting the upload
                        } catch (exc: Exception) {
                            Toast.makeText(this, exc.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    fun showFileChooser() {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
                    }


                    //handling the image chooser activity result
                     fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                        super.onActivityResult(requestCode, resultCode, data)
                        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                            filePath = data.getData()
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath)
                                imageView!!.setImageBitmap(bitmap)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    //method to get the file path from uri
                    fun getPath(uri: Uri?): String {
                        val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
                        cursor?.moveToFirst()
                        var document_id = cursor?.getString(0)
                        document_id = document_id!!.substring(document_id.lastIndexOf(":") + 1)
                        cursor?.close()

                        val cursorImage: Cursor? = contentResolver.query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
                        )
                        cursorImage?.moveToFirst()
                        val path = cursorImage?.getString(with(cursorImage) { getColumnIndex(DATA) })
                        cursorImage?.close()

                        return path ?: ""
                    }

                    //Requesting permission
                    fun requestStoragePermission() {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) return
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        ) {
                            // If the user has denied the permission previously your code will come to this block
                            // Here you can explain why you need this permission
                            // Explain here why you need this permission
                        }
                        // And finally ask for the permission
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            UploadImage.Companion.STORAGE_PERMISSIONS_CODE
                        )
                    }

                    fun onRequestPermissionsResult(
                        requestCode: Int,
                        permissions: Array<String?>,
                        grantResults: IntArray
                    ) {

                        // Checking the request code of our request
                        if (requestCode == STORAGE_PERMISSION_CODE) {

                            // If permission is granted
                            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                // Displaying a toast
                                Toast.makeText(
                                    this,
                                    "Permission granted now you can read the storage",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                // Displaying another toast if permission is not granted
                                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    //

                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonChoose -> showFileChooser()
            buttonUpload -> uploadMultipart()
        }
    }


}


@Composable
fun ImageUploadScreen() {
    var imageName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /* Select image */ }) {
                Text("Select")
            }

            TextField(
                value = imageName,
                onValueChange = {},
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(),
                placeholder = { Text("Name For Image") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            Button(onClick = { /* Upload image */ }) {
                Text("Upload")
            }
        }

//        Image(
//            painter = if (imageBitmap.value != null) painterResource(id = 0) else rememberImagePainter(
//                request = ImageRequest.Builder(context)
//                    .data(imageUri.value)
//                    .build()
//            ),
//            contentDescription = imageName,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .wrapContentSize(),
//            alignment = Alignment.Center
//        )
    }
}