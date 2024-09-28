package com.example.st_nobrainerquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.databinding.ActivitySettingsBinding

class ActivitySettings : AppCompatActivity() {
    private var binding: ActivitySettingsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.apply {

            buttonEditUser.setOnClickListener(){
                startActivity(Intent(this@ActivitySettings, ActivityEditUser::class.java))
            }
            buttonDarkMode.setOnClickListener(){
                Toast.makeText(this@ActivitySettings,"Feature in development POE 3", Toast.LENGTH_SHORT).show()
            }
            buttonLightMode.setOnClickListener(){
                Toast.makeText(this@ActivitySettings,"Feature in development POE 3", Toast.LENGTH_SHORT).show()
            }
            buttonLanguages.setOnClickListener(){
                Toast.makeText(this@ActivitySettings,"Feature in development POE 3", Toast.LENGTH_SHORT).show()
            }

            buttonUserManual.setOnClickListener(){
                startActivity(Intent(this@ActivitySettings, ActivityUserManual::class.java))
            }

            buttonLogout.setOnClickListener() {
                startActivity(Intent(this@ActivitySettings, ActivityLogin::class.java))
                Toast.makeText(this@ActivitySettings, "Thank you for using the No Brainer App, you are not successfully logged out! Goodbye!", Toast.LENGTH_LONG).show()
                finish()
            }


            bottomNavigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        startActivity(Intent(this@ActivitySettings, MainActivity::class.java))
                        true
                    }

                    R.id.nav_board -> {
                       // startActivity(Intent(this@ActivitySettings, LeaderActivity::class.java))
                        Toast.makeText(this@ActivitySettings,"Leaderboard feature in development", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.nav_setting -> {
                        startActivity(Intent(this@ActivitySettings, ActivitySettings::class.java))
                        true
                    }

                    R.id.nav_profile -> {
                        startActivity(Intent(this@ActivitySettings, ActivityViewProfile::class.java))
                        true
                    }

                    else -> false
                }
            }
        }
    }

}