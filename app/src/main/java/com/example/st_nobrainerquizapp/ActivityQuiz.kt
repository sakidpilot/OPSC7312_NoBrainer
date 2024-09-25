package com.example.st_nobrainerquizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.st_nobrainerquizapp.databinding.ActivityQuizBinding
import com.example.st_nobrainerquizapp.databinding.ScoreDialogBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.min


class ActivityQuiz : AppCompatActivity(),View.OnClickListener {

    companion object {
        var questionModelList : List<QuestionModel> = listOf()
        var time : String = ""
    }

    lateinit var binding: ActivityQuizBinding

    var currentQuestionIndex = 0;
    var selectedAnswer = ""
    var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            btn0.setOnClickListener(this@ActivityQuiz)
            btn1.setOnClickListener(this@ActivityQuiz)
            btn2.setOnClickListener(this@ActivityQuiz)
            btn3.setOnClickListener(this@ActivityQuiz)
            nextBtn.setOnClickListener(this@ActivityQuiz)
            hintBtn.setOnClickListener(){
                val user = Firebase.auth.currentUser?.uid.toString()
                val databaseRefScore = FirebaseDatabase.getInstance().getReference("score")
                databaseRefScore.child(user).get().addOnSuccessListener {
                    if(it.exists()){
                        val score = it.child("score").value.toString().toInt()
                        if(score >= 5){
                            hintText.visibility = View.VISIBLE
                            hintBtn.visibility = View.GONE

                            val newScore = UserScores( (score - 5))
                            databaseRefScore.child(user).setValue(newScore).addOnCompleteListener(){
                                Toast.makeText(this@ActivityQuiz,"You spent 5 points!!", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener(){
                                Toast.makeText(this@ActivityQuiz,"Error in creating profile and updating user account, try again!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@ActivityQuiz, this::class.java))
                            }
                        }
                        else{
                            Toast.makeText(this@ActivityQuiz,"You do not have enough points for a hint!", Toast.LENGTH_SHORT).show()
                        }

                    }
                    else{
                        Toast.makeText(this@ActivityQuiz,"Unable to access your score, try again later!", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener(){

                    Toast.makeText(this@ActivityQuiz,"Unable to access database, try again later!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loadQuestions()
        startTimer()

        //binding.hintBtn.setOnClickListener { binding.hintTextview.setText(currentQuestionIndex+1) }
    }

    private fun startTimer(){
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes,remainingSeconds)

            }

            override fun onFinish() {
                //Finish the quiz
            }

        }.start()
    }

    private fun loadQuestions(){
        selectedAnswer = ""
        if(currentQuestionIndex == questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex+1}/ ${questionModelList.size} "
            questionProgressIndicator.progress =
                ( currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100 ).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]

            hintText.text = questionModelList[currentQuestionIndex].hint
        }
    }

    override fun onClick(view: View?) {

        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button
        if(clickedBtn.id==R.id.next_btn){
            //next button is clicked
            if(selectedAnswer.isEmpty()){
                Toast.makeText(applicationContext,"Please select an answer to continue",Toast.LENGTH_SHORT).show()
                return;
            }
            if(selectedAnswer == questionModelList[currentQuestionIndex].correct){
                score++
                Log.i("Score of quiz",score.toString())
            }
            currentQuestionIndex++
            binding.hintBtn.visibility = View.VISIBLE
            binding.hintText.visibility = View.GONE
            loadQuestions()
        }else{
            //options button is clicked
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }

    private fun finishQuiz(){
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat() ) *100 ).toInt()

        val dialogBinding  = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if(percentage>60){
                scoreTitle.text = "Congrats! You passed the Quiz! And earned 5 points!"
                scoreTitle.setTextColor(Color.BLUE)

                val user = Firebase.auth.currentUser?.uid.toString()
                val databaseRefScore = FirebaseDatabase.getInstance().getReference("score")
                databaseRefScore.child(user).get().addOnSuccessListener {
                    if (it.exists()) {
                        val score = it.child("score").value.toString().toInt()
                        val newScore = UserScores((score + 5))
                        databaseRefScore.child(user).setValue(newScore).addOnCompleteListener() {
                            Toast.makeText(
                                this@ActivityQuiz,
                                "5 points added!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener() {
                            Toast.makeText(
                                this@ActivityQuiz,
                                "Error in adding 5 points, try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@ActivityQuiz, this::class.java))
                        }
                    }
                }
            }

            else{
                scoreTitle.text = "Oops! You failed the Quiz!"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions were correct"
            finishBtn.setOnClickListener {
                startActivity(Intent(this@ActivityQuiz, MainActivity::class.java))
                finish()
            }
        }

        AlertDialog.Builder(this@ActivityQuiz)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()


            }

        }

