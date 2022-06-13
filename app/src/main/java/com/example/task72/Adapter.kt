package com.example.task72

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Adapter(private val data: ArrayList<Hero>, private val activity: Activity): RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)!!
        val icon = view.findViewById<ImageView>(R.id.icon)!!
        val card = view.findViewById<CardView>(R.id.cardHero)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = data[position]
        holder.name.text = hero.name
        Picasso.get().load(hero.images.xs).into(holder.icon)
        holder.card.setOnClickListener{
            val intent = Intent(activity, ActivityHero::class.java)
            intent.putExtra("name", hero.name)
            intent.putExtra("imageUrl", hero.images.lg)
            intent.putExtra("gender",
                if (hero.appearance.gender == "-" || hero.appearance.gender == "null") "no information available"
                else hero.appearance.gender
            )
            intent.putExtra("eyeColor",
                if (hero.appearance.eyeColor == "-" || hero.appearance.eyeColor == "null") "no information available"
                else hero.appearance.eyeColor
            )
            intent.putExtra("hairColor",
                if (hero.appearance.hairColor == "-" || hero.appearance.hairColor == "null") "no information available"
                else hero.appearance.hairColor
            )
            intent.putExtra("height", hero.appearance.height[1])
            intent.putExtra("weight", hero.appearance.weight[1])
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = data.size
}