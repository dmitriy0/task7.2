package com.example.task72

import com.google.gson.annotations.SerializedName

data class Hero(
    val name: String,
    val images: Image,
    val appearance: Appearance,
    )


class Image(
    val xs: String,
    val lg: String
    )


class Appearance(
    val gender: String,
    val height: List<String>,
    val weight: List<String>,
    val eyeColor: String,
    val hairColor: String
)