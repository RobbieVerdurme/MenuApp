package com.example.boeferrob.menuapp

import java.io.Serializable

data class Food(var key:String? = null, var name: String, var ingredients: MutableList<Ingredient>, var discritpion: String, var preperation: String) : Serializable {
    constructor() : this("","", ArrayList<Ingredient>(), "", "") {}
    override fun toString(): String {
        return name
    }
}
