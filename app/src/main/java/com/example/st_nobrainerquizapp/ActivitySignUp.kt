package com.example.st_nobrainerquizapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class ActivitySignUp : AppCompatActivity() {
    private var binding: ActivitySignUpBinding? = null
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.tvGoToLogin?.setOnClickListener{
            startActivity(Intent(this, ActivityLogin::class.java))
            this.finish()
        }


        binding?.btnSignUp?.setOnClickListener{
            registerUser()
        }

    }

    private fun registerUser(){
        val name = binding?.etSinUpName?.text.toString()
        val email = binding?.etSinUpEmail?.text.toString()
        val password = binding?.etSinUpPassword?.text.toString()
        if (validateForm(name,email,password)){

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"User ID created!!", Toast.LENGTH_SHORT).show()



                        startActivity(Intent(this, ActivityLogin::class.java))
                        this.finish()
                    }

                 }
                //handle the failure scenario of tasks
                .addOnFailureListener(this)
                {
                        task->
                    //display an error message associated with the failed task
                    Toast.makeText(this,task.message.toString(), Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun validateForm(name: String, email:String, password:String?): Boolean{
        return when{
            TextUtils.isEmpty(name) ->{
                binding?.tilName?.error="Enter name"
                false
            }
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->{
                binding?.tilEmail?.error="Enter a valid email address"
                false
            }
            TextUtils.isEmpty(password) ->{
                binding?.tilPassword?.error="Enter a password"
                false
            }
            else ->{true}
        }
    }
}