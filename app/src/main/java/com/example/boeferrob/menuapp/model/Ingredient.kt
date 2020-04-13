package com.example.boeferrob.menuapp

import java.io.Serializable

data class Ingredient(var name: String, var quantity: String, var measurement: String) : Serializable{
    constructor() : this("", "","") {}
}