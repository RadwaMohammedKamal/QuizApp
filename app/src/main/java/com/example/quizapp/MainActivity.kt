package com.example.quizapp

import android.media.MediaParser
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val countries= arrayOf("Egypt","USA","France","UK")
    val cities= arrayOf("cairo","ws","paris","london")
    lateinit var questionText:TextView
    lateinit var questionCount:TextView
    lateinit var scoreText:TextView
    lateinit var answerText:Spinner
    lateinit var startButton:Button
    lateinit var nextButton:Button
    lateinit var adapter: ArrayAdapter<String>
    var player : MediaPlayer?=null
    var score=0
    var index=0
   // val user= mutableSetOf<String>()
    val items= mutableListOf("please select","cairo","tokyo","ws","beijing","paris","tronto","london")
    val itemsCopy=listOf("please select", "cairo", "tokyo", "ws", "beijing", "paris", "toronto", "london")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionText=findViewById(R.id.questuinText)
        answerText=findViewById(R.id.answerText)
        questionCount=findViewById(R.id.questionCount)
        scoreText=findViewById(R.id.scoreText)
        startButton=findViewById(R.id.startButton)
        nextButton=findViewById(R.id.nextButton)

    }

    override fun onDestroy() {
        player?.stop()
        super.onDestroy()
    }


    fun Start(view: View) {
        player?.stop()
        index=0
        score=0
       // user.clear()
        items.clear()
        items.addAll(itemsCopy)
        // spinner =>adaptor => items
        val adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,items)
        answerText.adapter=adapter
        //adapter.notifyDataSetChanged()

        questionText.text="What is the Capital of ${countries[0]}"//print
        questionCount.text="Question ${index+1} of ${countries.size}"//print
        nextButton.isEnabled=true
        answerText.isEnabled=true
        startButton.isEnabled=false
       // scoreText.text="Score = $score"
    }

    fun Next(view: View) {
       // startButton.isEnabled=false
        val answer=answerText.selectedItem.toString().lowercase()
//        if(answer in user){
//            Toast.makeText(this, "selected before", Toast.LENGTH_SHORT).show()
//            return
//        }
        if (answerText.selectedItemPosition==0){
            Toast.makeText(this, "please answer", Toast.LENGTH_SHORT).show()
            return
        }
        if(answer==cities[index]){
            score++
          //  scoreText.text="Score = $score"
            items.remove(answer)
           // user.add(answer)
        }
        index++
        if (index<countries.size) {
            questionText.text = "What is the Capital of ${countries[index]}"//print
            questionCount.text="Question ${index+1} of ${countries.size}"//print
        }else {
            nextButton.isEnabled = false
            startButton.isEnabled=true
            scoreText.text="Your Final Score = $score"
            Toast.makeText(this, "Finish Quiz", Toast.LENGTH_SHORT).show()
            if(score>countries.size/2) {
                 player = MediaPlayer.create(this, R.raw.success)
               player?.start()
            } else{
                 player = MediaPlayer.create(this, R.raw.fail)
                player?.start()
            }
        }
        answerText.setSelection(0)
//        items.shuffle()
        items.subList(1,items.size).shuffle()
       // answerText.setText("")
    }
}