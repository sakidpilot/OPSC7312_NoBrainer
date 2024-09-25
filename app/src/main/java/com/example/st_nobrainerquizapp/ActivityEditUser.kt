package com.example.st_nobrainerquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.databinding.ActivityEditUserBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class ActivityEditUser : AppCompatActivity(){

    private lateinit var binding: ActivityEditUserBinding

//private lateinit var db : DBConnect

private lateinit var gender : String
var email = Firebase.auth.currentUser?.email.toString()

val databaseRef = FirebaseDatabase.getInstance().getReference("user")


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityEditUserBinding.inflate(layoutInflater)
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


    binding.btnCreateProfile.setOnClickListener {

        //db.insertUser(email, binding.edtUsername.text.toString(), binding.edtbio.text.toString(), gender, binding.edtAge.text.toString(), selectedImage)
        var userAuth = Firebase.auth.currentUser?.uid.toString()
        val user = UserSQLiteDB(email, binding.edtUsername.text.toString(), binding.edtBio.text.toString(), gender, binding.edtAge.text.toString())
        databaseRef.child(userAuth).setValue(user).addOnCompleteListener(){

            Toast.makeText(this,"User Profile created!!", Toast.LENGTH_SHORT).show()


            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }.addOnFailureListener(){
            Toast.makeText(this,"Error in creating profile and updating user account, try again!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, this::class.java))
        }




    }
}
}