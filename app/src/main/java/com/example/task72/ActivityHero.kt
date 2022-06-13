package com.example.task72

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ActivityHero : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero)

        findViewById<TextView>(R.id.name).text = intent.getStringExtra("name")
        findViewById<TextView>(R.id.height).text = intent.getStringExtra("height")
        findViewById<TextView>(R.id.weight).text = intent.getStringExtra("weight")
        findViewById<TextView>(R.id.gender).text = "Gender: ${intent.getStringExtra("gender")}"
        findViewById<TextView>(R.id.eye_color).text = "Eye color: ${intent.getStringExtra("eyeColor")}"
        findViewById<TextView>(R.id.hair_color).text = "Hair color: ${intent.getStringExtra("hairColor")}"

        Picasso.get().load(intent.getStringExtra("imageUrl")).into(findViewById<ImageView>(R.id.image))
    }
}