package com.example.layout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<User?>()
    val error = MutableLiveData<String>()

    val requestQueue: RequestQueue = Volley.newRequestQueue(application)
    fun fetchUserProfile(id_user: Int) {
        val url = "http://yourserver.com/getUserProfile.php?user_id=$id_user"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val id = response.getInt("id")
                    val username = response.getString("username")
                    val email = response.getString("email")
                    val pass = response.getString("pass")
                    // Ambil field lainnya sesuai kebutuhan

                    val user = User(id, username, email, pass)
                    this.user.postValue(user)
                } catch (e: Exception) {
                    error.postValue("Parsing error: ${e.message}")
                }
            },
            { volleyError ->
                error.postValue("Request error: ${volleyError.message}")
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}