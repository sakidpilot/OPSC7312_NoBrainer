package com.example.st_nobrainerquizapp.otherGames

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.ActivitySettings
import com.example.st_nobrainerquizapp.ActivityViewProfile
import com.example.st_nobrainerquizapp.MainActivity
import com.example.st_nobrainerquizapp.R
import com.example.st_nobrainerquizapp.databinding.ActivityOtherGamesBinding

class OtherGamesActivity : AppCompatActivity() {
    private var binding: ActivityOtherGamesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherGamesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {
            btnPlaySnake.setOnClickListener {
                val intent = Intent(this@OtherGamesActivity, snake_game::class.java)
                startActivity(intent)
            }

            bottomNavigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        startActivity(Intent(this@OtherGamesActivity, MainActivity::class.java))
                        true
                    }

                    R.id.nav_board -> {
                        //startActivity(Intent(this, LeaderActivity::class.java))
                        Toast.makeText(this@OtherGamesActivity,"Leaderboard feature in development", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.nav_setting -> {
                        startActivity(Intent(this@OtherGamesActivity, ActivitySettings::class.java))
                        true
                    }

                    R.id.nav_profile -> {
                        startActivity(Intent(this@OtherGamesActivity, ActivityViewProfile::class.java))
                        true
                    }

                    else -> false
                }
            }
        }
    }
}