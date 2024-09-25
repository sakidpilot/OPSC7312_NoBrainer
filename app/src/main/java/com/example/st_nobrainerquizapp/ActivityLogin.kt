package com.example.st_nobrainerquizapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.st_nobrainerquizapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class ActivityLogin : AppCompatActivity() {
    private var binding:ActivityLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding?.tvGoToRegister?.setOnClickListener{
            startActivity(Intent(this, ActivitySignUp::class.java))
            this.finish()
        }
        binding?.tvForgotPassword?.setOnClickListener{
            startActivity(Intent(this, ActivityResetPassword::class.java))
        }
        binding?.btnSignIn?.setOnClickListener{
            sinInUser()
        }

        binding?.btnSignInWithGoogle?.setOnClickListener{
            signInWithGoogle()
        }
    }


    private fun sinInUser(){
        val email = binding?.etSinInEmail?.text.toString()
        val password = binding?.etSinInPassword?.text.toString()
        if(validateForm(email,password)){
            //showProgressBar()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){

                        startActivity(Intent(this, ActivityUserProfile::class.java))
                        Toast.makeText(this,"Login was successful. Welcome!", Toast.LENGTH_SHORT).show()
                        this.finish()

                    }else{
                        Toast.makeText(this,"Login was unsuccessful. Try again!", Toast.LENGTH_SHORT).show()
                        //hideProgressBar()
                    }
                }
        }
    }


    private fun validateForm( email:String, password:String?): Boolean{
        return when{
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

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if(account!=null){
                updateUI(account)
            }
        }
        else{
            Toast.makeText(this,"Sign in was unsuccessful. Try again!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        //showProgressBar()
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){

                startActivity(Intent(this, ActivityUserProfile::class.java))
                Toast.makeText(this,"Sign in was successful. Welcome!", Toast.LENGTH_SHORT).show()
                this.finish()

            }else{
                Toast.makeText(this,"Login was unsuccessful. Try again!", Toast.LENGTH_SHORT).show()
                //hideProgressBar()
            }
        }

    }


}


