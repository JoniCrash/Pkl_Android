package com.example.layout

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class NetworkManager(val context: Context) {

    private val userDaftarUrl = "http://192.168.22.2/BackEndPKL/user_app.php"
    private val userMasukUrl = "http://192.168.22.2/BackEndPKL/user_app.php"
    private val pengajuanUrl = "http://192.168.22.2/BackEndPKL/pengajuan_app.php"
    fun userDaftar(callback: NetworkCallback) {
        val queue = Volley.newRequestQueue(context)

        val kirimData = StringRequest(
            Request.Method.POST, userDaftarUrl,
            { response ->
                Toast.makeText(context,"Berhasil: ${response.substring(0, 500)}",Toast.LENGTH_SHORT
                ).show()
                callback.onSuccess(response)
                // Proses respons di sini
            },
            { error ->
                Toast.makeText(context, "Gagal Mendaftar: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
                callback.onFailure(error)})


        // Tambahkan permintaan ke RequestQueue
        queue.add(kirimData)
    }

    fun userMasuk(callback: NetworkCallback) {
        val queue = Volley.newRequestQueue(context)

        val kirimData = StringRequest(
            Request.Method.PUT, userMasukUrl,
            { response ->
                Toast.makeText(context,"Berhasil: ${response.substring(0, 500)}",Toast.LENGTH_SHORT
                ).show()
                callback.onSuccess(response)
                // Proses respons di sini
            },
            { error ->
                Toast.makeText(context, "Gagal Masuk: ${error.message}", Toast.LENGTH_SHORT).show()
                callback.onFailure(error)})
        // Tambahkan permintaan ke RequestQueue
        queue.add(kirimData)
    }

    fun userPengajuan(callback: NetworkCallback) {
        val queue = Volley.newRequestQueue(context)

        val kirimData = StringRequest(
            Request.Method.POST, pengajuanUrl,
            { response ->
                Toast.makeText(context,"Berhasil: ${response.substring(0, 500)}",Toast.LENGTH_SHORT
                ).show()
                callback.onSuccess(response)
                // Proses respons di sini
            },
            { error ->
                Toast.makeText(context, "Gagal Masuk: ${error.message}", Toast.LENGTH_SHORT).show()
                callback.onFailure(error)})
        // Tambahkan permintaan ke RequestQueue
        queue.add(kirimData)
    }
}
interface NetworkCallback {
    fun onSuccess(response: String)
    fun onFailure(error: Exception)
}