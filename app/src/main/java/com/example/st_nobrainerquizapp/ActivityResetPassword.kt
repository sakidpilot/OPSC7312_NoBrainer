package com.example.st_nobrainerquizapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st_nobrainerquizapp.databinding.ActivityResetPasswordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import android.view.View
import android.widget.Toast


class ActivityResetPassword : AppCompatActivity() {
    private var binding: ActivityResetPasswordBinding? = null
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.btnForgotPasswordSubmit?.setOnClickListener{
            restPassword()
        }
    }

    private fun restPassword(){
        val email = binding?.etForgotPasswordEmail?.text.toString()
        if(validateForm(email)){
            //showProgressBar()
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    //hideProgressBar()
                    binding?.tilEmailForgetPassword?.visibility = View.GONE
                    binding?.tvSubmitMsg?.visibility = View.VISIBLE
                    binding?.btnForgotPasswordSubmit?.visibility = View.GONE
                    Toast.makeText(this, "Password reset link sent successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ActivityLogin::class.java))
                    this.finish()
                }
                else{
                    //hideProgressBar()
                    Toast.makeText(this, "Password can not be reset. Try again later", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun validateForm( email:String): Boolean{
        return when{
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->{
                binding?.tilEmailForgetPassword?.error="Enter a valid email address"
                false
            }
            else ->true
        }
    }
}