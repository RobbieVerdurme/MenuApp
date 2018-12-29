package com.example.boeferrob.menuapp

import java.io.Serializable

data class Food(var name: String, var ingredients: MutableList<Ingredient>, var discritpion: String) : Serializable {
    constructor() : this("", ArrayList<Ingredient>(), "") {}
    override fun toString(): String {
        return name
    }
}
