package com.example.st_nobrainerquizapp.OwnQuiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.st_nobrainerquizapp.ActivitySettings
import com.example.st_nobrainerquizapp.ActivityViewProfile
import com.example.st_nobrainerquizapp.MainActivity
import com.example.st_nobrainerquizapp.R
import com.example.st_nobrainerquizapp.databinding.ActivityMyQuizBinding

class MyQuiz : AppCompatActivity() {
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var binding: ActivityMyQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = QuizListAdapter(
            onItemClicked = { quiz ->
                val intent = Intent(this, PlayQuizActivity::class.java)
                intent.putExtra("QUIZ_ID", quiz.id)
                startActivity(intent)
            },
            onDeleteClicked = { quiz ->
                showDeleteConfirmationDialog(quiz)
            }
        )

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        val quizDao = QuizDatabase.getInstance(application).quizDao()
        quizViewModel = ViewModelProvider(this, QuizViewModelFactory(quizDao))
            .get(QuizViewModel::class.java)

        quizViewModel.allQuizzes.observe(this) { quizzes ->
            quizzes?.let { adapter.submitList(it) }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this@MyQuiz, MainActivity::class.java))
                    true
                }

                R.id.nav_board -> {
                    //startActivity(Intent(this, LeaderActivity::class.java))
                    Toast.makeText(this@MyQuiz,"Leaderboard feature in development", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_setting -> {
                    startActivity(Intent(this@MyQuiz, ActivitySettings::class.java))
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this@MyQuiz, ActivityViewProfile::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun showDeleteConfirmationDialog(quiz: Quiz) {
        AlertDialog.Builder(this)
            .setTitle("Delete Quiz")
            .setMessage("Are you sure you want to delete this quiz?")
            .setPositiveButton("Yes") { _, _ ->
                quizViewModel.deleteQuiz(quiz)
            }
            .setNegativeButton("No", null)
            .show()
    }
}
