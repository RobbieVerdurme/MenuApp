package com.example.boeferrob.menuapp

data class Food(var name: String, var ingredients: MutableList<Ingredient>, var discritpion: String) {
    override fun toString(): String {
        return name
    }
}
