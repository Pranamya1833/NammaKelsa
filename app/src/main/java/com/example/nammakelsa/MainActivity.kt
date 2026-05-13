package com.example.nammakelsa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         // Ensure the correct view type is used (CardView, not Button)
         val btnWorker = findViewById<CardView>(R.id.btnWorker)
         btnWorker.setOnClickListener {
             startActivity(Intent(this, WorkerActivity::class.java))
         }

         val btnCustomer = findViewById<CardView>(R.id.btnCustomer)
         btnCustomer.setOnClickListener {
             startActivity(Intent(this, CustomerActivity::class.java))
         }
    }
}