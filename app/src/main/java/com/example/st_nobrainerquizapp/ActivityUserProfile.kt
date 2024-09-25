package com.example.st_nobrainerquizapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.databinding.ActivityUserProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.auth.UserProfileChangeRequest


class ActivityUserProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding

    //private lateinit var db : DBConnect
   ///private lateinit var selectedImage : ByteArray

    private lateinit var gender : String
    var email = Firebase.auth.currentUser?.email.toString()

    val databaseRef = FirebaseDatabase.getInstance().getReference("user")
    val databaseRefScore = FirebaseDatabase.getInstance().getReference("score")

    //variable to store image
    /*private val getImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let{
            val inputStream = contentResolver.openInputStream(uri)
            val imageByteArray = inputStream?.readBytes()
            if (imageByteArray != null)
            {
                selectedImage = imageByteArray

            }
            else
            {
                Toast.makeText(this, "No Image Attached", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, "Image Captured Successfully", Toast.LENGTH_SHORT).show()
        }
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db = DBConnect(this)
        //binding.imgPic.setBackground(selectedImage)
        gender = "Male"

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
               R.id.rdMale -> gender = "Male"
               R.id.rdFemale -> gender = "Female"
            }
        }

        binding.btnSkipProfile.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnCreateProfile.setOnClickListener {

            //db.insertUser(email, binding.edtUsername.text.toString(), binding.edtbio.text.toString(), gender, binding.edtAge.text.toString(), selectedImage)
            var userAuth = Firebase.auth.currentUser?.uid.toString()
            val user = UserSQLiteDB(email, binding.edtUsername.text.toString(), binding.edtBio.text.toString(), gender, binding.edtAge.text.toString())
            databaseRef.child(userAuth).setValue(user).addOnCompleteListener(){

                Toast.makeText(this,"User Profile created!!", Toast.LENGTH_SHORT).show()

/*
// Get the current user
                val user = FirebaseAuth.getInstance().currentUser

                if (user != null) {
                    // Create a request to update the user's profile
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName("New Display Name") // Set the new display name
                        .build()

                    // Update the user's profile
                    user.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Display name updated successfully
                                println("User profile updated.")
                            } else {
                                // Handle failure
                                println("Failed to update profile: ${task.exception}")
                            }
                        }
                }*/

                val score = UserScores(50)
                databaseRefScore.child(userAuth).setValue(score).addOnCompleteListener(){
                    Toast.makeText(this,"New user 50 points added!!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener(){
                    Toast.makeText(this,"Error in creating profile and updating user account, try again!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, this::class.java))
                }

                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }.addOnFailureListener(){
                Toast.makeText(this,"Error in creating profile and updating user account, try again!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, this::class.java))
            }





        }

       // binding.imgTakeImage.setOnClickListener() {
          //  getImage.launch("image/*")

        //}


        }
    }


