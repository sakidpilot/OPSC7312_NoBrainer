package com.example.st_nobrainerquizapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.databinding.ActivityViewProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayInputStream


class ActivityViewProfile : AppCompatActivity() {


    private var binding: ActivityViewProfileBinding? = null
    //private lateinit var db : DBConnect
    //val user = db.retrieveUser(Firebase.auth.currentUser?.email.toString())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //db = DBConnect(this)

        val user = Firebase.auth.currentUser?.uid.toString()
        val databaseRef = FirebaseDatabase.getInstance().getReference("user")
        databaseRef.child(user).get().addOnSuccessListener {
            if(it.exists()){
                binding!!.edtAge.text  = "Age :" + (it.child("age").value.toString())
                binding!!.edtUsername.text = "Username: " + (it.child("username").value.toString())
                binding!!.edtbio.text = "Bio: " + (it.child("bio").value.toString())
                val gender = (it.child("gender").value.toString())
                binding!!.tvGender.text = gender

                if(gender == "Male"){
                    binding!!.imgPic.setImageResource(R.drawable.person1)
                }
                else{
                    binding!!.imgPic.setImageResource(R.drawable.person2)
                }
            }
            Toast.makeText(this@ActivityViewProfile,"Your profile info", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener(){

            Toast.makeText(this@ActivityViewProfile,"Unable to get your data, try again later!", Toast.LENGTH_SHORT).show()
        }


        binding = ActivityViewProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.apply {



            bottomNavigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        startActivity(Intent(this@ActivityViewProfile, MainActivity::class.java))
                        true
                    }

                    R.id.nav_board -> {
                        //startActivity(Intent(this@ActivityViewProfile, LeaderActivity::class.java))
                        Toast.makeText(this@ActivityViewProfile,"Leaderboard feature in development", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.nav_setting -> {
                        startActivity(Intent(this@ActivityViewProfile, ActivitySettings::class.java))
                        true
                    }

                    R.id.nav_profile -> {
                        startActivity(Intent(this@ActivityViewProfile, ActivityViewProfile::class.java))
                        true
                    }

                    else -> false
                }
            }



        }






        //redirect to user profile view to make changes
        /*btnEditUp.setOnClickListener(){
       startActivity(Intent(this@ActivityViewProfile, ActivityUserProfile::class.java))
        }*/



    }

    /*fun loadImage() {
        val imageByteArray = user.image
        if (imageByteArray != null)
        {
            val imageStream = ByteArrayInputStream(imageByteArray)
            val bitmap = BitmapFactory.decodeStream(imageStream)
            val drawable: Drawable = BitmapDrawable(bitmap)
            binding?.imgPic?.background = drawable

        }
        else
        {
            binding?.imgPic?.background = (R.drawable.person6).toDrawable()
        }
    }*/

    fun loadUser() {
        //read from db

    }



}