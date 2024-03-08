package com.example.layout

import android.Manifest
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import net.gotev.uploadservice.MultipartUploadRequest
import java.io.IOException
import java.util.UUID

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Declaring views
    private var buttonChoose: Button? = null
    private var buttonUpload: Button? = null
    private var imageView: ImageView? = null
    private var editText: EditText? = null

    Image request code
    private val PICK_IMAGE_REQUEST = 1

    //Bitmap to get image from gallery
    private var bitmap: Bitmap? = null

    //Uri to store the image uri
    private var filePath: Uri? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Requesting storage permission
        requestStoragePermission()

        //Initializing views
        buttonChoose = findViewById(R.id.buttonChoose) as Button?
        buttonUpload = findViewById(R.id.buttonUpload) as Button?
        imageView = findViewById(R.id.imageView) as ImageView?
        editText = findViewById(R.id.editTextName) as EditText?

        //Setting clicklistener
        buttonChoose!!.setOnClickListener(this)
        buttonUpload!!.setOnClickListener(this)
    }

    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    fun uploadMultipart() {
        //getting name for the image
        val name: String = editText.getText().toString().trim { it <= ' ' }

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

    //method to show file chooser
    private fun showFileChooser() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    //handling the image chooser activity result
    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
        var cursor: Cursor = getContentResolver().query(uri, null, null, null, null)
        cursor.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()
        cursor = getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, MediaStore.Images.Media._ID + " = ? ", arrayOf<String>(document_id), null
        )
        cursor.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return path
    }

    //Requesting permission
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) === PackageManager.PERMISSION_GRANTED
        ) return
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
            MainActivity.Companion.STORAGE_PERMISSION_CODE
        )
    }

    //This method will be called when the user will tap on allow or deny
    fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String?>?,
        @NonNull grantResults: IntArray
    ) {

        //Checking the request code of our request
        if (requestCode == MainActivity.Companion.STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(
                    this,
                    "Permission granted now you can read the storage",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onClick(v: View) {
        if (v === buttonChoose) {
            showFileChooser()
        }
        if (v === buttonUpload) {
            uploadMultipart()
        }
    }

    companion object {
        //storage permission code
        private const val STORAGE_PERMISSION_CODE = 123
    }
}