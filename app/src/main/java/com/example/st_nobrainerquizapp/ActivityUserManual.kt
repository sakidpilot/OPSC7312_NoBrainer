package com.example.st_nobrainerquizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.databinding.ActivitySettingsBinding
import com.example.st_nobrainerquizapp.databinding.ActivityUserManualBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class ActivityUserManual : AppCompatActivity() {
    private val BASE_URL = "https://st10198310-testapi.azurewebsites.net"
    private val TAG : String = "CHECK_RESPONSE"
    private var binding: ActivityUserManualBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserManualBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.apply {

            btnManualOK.setOnClickListener() {
                startActivity(Intent(this@ActivityUserManual, ActivitySettings::class.java))
                finish()
            }

        }

        getUserGuide()


    }

    private fun getUserGuide() {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(REST_API::class.java)


        api.getGuide().enqueue(object : Callback<UserGuide> {
            override fun onResponse(
                call: Call<UserGuide>,
                response: Response<UserGuide>
            ) {
                if (response.isSuccessful) {

                    val userGuide = StringBuilder()

                    response.body()?.let {
                        //for (guideData in it) {
                            userGuide.append(response.body()!!.guide.toString())
                            userGuide.append("\n")
                            Log.i(TAG, "onResponse : ${response.body()!!.guide}")
                        //}
                    }
                    Toast.makeText(this@ActivityUserManual,"REST API Call Success", Toast.LENGTH_SHORT).show()
                    binding?.tvUserManual?.text = userGuide
                }
            }

            override fun onFailure(call: Call<UserGuide>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }

}