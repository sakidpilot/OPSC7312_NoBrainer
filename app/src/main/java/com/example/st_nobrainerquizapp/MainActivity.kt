package com.example.st_nobrainerquizapp


import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.st_nobrainerquizapp.OwnQuiz.MyQuiz
import com.example.st_nobrainerquizapp.OwnQuiz.OwnQuizActivity
import com.example.st_nobrainerquizapp.databinding.ActivityMainBinding
import com.example.st_nobrainerquizapp.otherGames.OtherGamesActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayInputStream

class MainActivity : AppCompatActivity() {

    //private lateinit var db : DBConnect

    private var binding: ActivityMainBinding? = null

    lateinit var quizModelList : MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    var auth = Firebase.auth.currentUser?.email.toString()
    var user = Firebase.auth.currentUser?.uid.toString()

    //private val user = db.retrieveUser(auth)
    private val score = 0//db.retrieveScore(auth)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //db = DBConnect(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        quizModelList = mutableListOf()
        getQuizFromFirebase()


        binding?.apply {

            //loadImage()
            val databaseRef = FirebaseDatabase.getInstance().getReference("user")
            databaseRef.child(user).get().addOnSuccessListener {
                if(it.exists()){
                    val gender = it.child("gender").value.toString()

                    if(gender == "Male"){
                        dp.setImageResource(R.drawable.person1)
                    }
                    else{
                        dp.setImageResource(R.drawable.person2)
                    }
                }
            }.addOnFailureListener(){

                Toast.makeText(this@MainActivity,"Unable to access database, try again later!", Toast.LENGTH_SHORT).show()
            }


            val databaseRefScore = FirebaseDatabase.getInstance().getReference("score")
            databaseRefScore.child(user).get().addOnSuccessListener {
                if(it.exists()){
                    val score = it.child("score").value.toString()
                    binding?.tvScore?.setText(score)
                }
                else{
                    Toast.makeText(this@MainActivity,"Unable to access your score, try again later!", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener(){

                Toast.makeText(this@MainActivity,"Unable to access database, try again later!", Toast.LENGTH_SHORT).show()
            }


            tvUserWelcome.setText("Hi, " + auth)

            chatbtn.setOnClickListener(){
                Toast.makeText(this@MainActivity,"Feature in development POE3", Toast.LENGTH_SHORT).show()
            }

            btnOtherGames.setOnClickListener{
                val intent = Intent(this@MainActivity, OtherGamesActivity::class.java)
                startActivity(intent)
            }

            createbtn.setOnClickListener {
                val intent = Intent(this@MainActivity, OwnQuizActivity::class.java)
                startActivity(intent)
            }

            myQuiz.setOnClickListener{
                val intent = Intent(this@MainActivity, MyQuiz::class.java)
                startActivity(intent)
            }

            bottomNavigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                        true
                    }

                    R.id.nav_board -> {
                        //startActivity(Intent(this, LeaderActivity::class.java))
                        Toast.makeText(this@MainActivity,"Leaderboard feature in development POE3", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.nav_setting -> {
                        startActivity(Intent(this@MainActivity, ActivitySettings::class.java))
                        true
                    }

                    R.id.nav_profile -> {
                        startActivity(Intent(this@MainActivity, ActivityViewProfile::class.java))
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setupRecyclerView(){
        binding?.progressBar?.visibility ?:  View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding?.recyclerViewQuiz?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerViewQuiz?.adapter = adapter
    }

    private fun getQuizFromFirebase(){
        binding?.progressBar?.visibility ?: View.VISIBLE
        FirebaseDatabase.getInstance().getReference("quiz")
            .get()
            .addOnSuccessListener { dataSnapshot->
                if(dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }


    }
}

    /*fun loadImage() {
        val imageByteArray = null//user.image
        if (imageByteArray != null)
        {
            val imageStream = ByteArrayInputStream(imageByteArray)
            val bitmap = BitmapFactory.decodeStream(imageStream)
            val drawable: Drawable = BitmapDrawable(bitmap)
            binding?.dp?.background = drawable

        }
        else
        {
            binding?.dp?.background = (R.drawable.person6).toDrawable()
        }
    }*/
