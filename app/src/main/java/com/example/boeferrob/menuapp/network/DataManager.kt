package com.example.boeferrob.menuapp.network

import android.content.Context
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.Ingredient
import java.io.*
import java.util.ArrayList

object DataManager {
    var foodList :MutableList<Food> = ArrayList<Food>() as MutableList<Food>
    lateinit var file: File

    init {
        //makeFakeFood()
    }

    private fun makeFakeFood(){
        var ingredients =ArrayList<Ingredient>()
        ingredients.add(Ingredient("kip", 1,"Unit"))
        ingredients.add(Ingredient("kersttomaten", 500,"Grams"))
        ingredients.add(Ingredient("ketchup", 20,"Grams"))
        ingredients.add(Ingredient("kaneel", 1,"Gram"))
        ingredients.add(Ingredient("paprika", 1,"Unit"))
        ingredients.add(Ingredient("zout", 2,"Grams"))
        ingredients.add(Ingredient("peper", 3,"Grams"))
        ingredients.add(Ingredient("hamburger", 1,"Unit"))
        ingredients.add(Ingredient("annannas", 1,"Unit"))
        ingredients.add(Ingredient("chirizo", 1,"Unit"))
        ingredients.add(Ingredient("kaas", 1,"Unit"))
        ingredients.add(Ingredient("oud brugge kaas", 500,"Grams"))

        var food = Food("Pizza", ingredients, "Pizza")
        foodList.add(food)

        ingredients = ArrayList<Ingredient>()
        ingredients.add(Ingredient("chirizo", 1,"Unit"))
        ingredients.add(Ingredient("kaas", 1,"Unit"))
        ingredients.add(Ingredient("oud brugge kaas", 500,"Grams"))

        food = Food("Lasigna", ingredients, "deeg in een pot")
        foodList.add(food)

        food = Food("Cake", ingredients, "taart met versiering")
        foodList.add(food)

        food = Food("Hamburger met frietjes", ingredients, "")
        foodList.add(food)

        food = Food("botherhammen", ingredients, "boterhammen met beleg")
        foodList.add(food)

        food = Food("Broodjes", ingredients, "Afgebakken broodjes met beleg")
        foodList.add(food)

        food = Food("stoverij met frietjes", ingredients, "")
        foodList.add(food)

        food = Food("boomstammetjes", ingredients, "boomstammetjes met appelmoes")
        foodList.add(food)

        food = Food("Test", ingredients, "dit is een testje")
        foodList.add(food)
    }

    fun createFile(context: Context){
        file = File(context.filesDir.toString() + "/" +  "MenuApp")
        save()
        getData()
    }

    fun save(){
        var fileoutputstream = FileOutputStream(file)
        ObjectOutputStream(fileoutputstream).writeObject(foodList)
    }

    fun getData(){
        var fileInputstream = FileInputStream(file)
        var objectoutputstream = ObjectInputStream(fileInputstream)
        foodList = objectoutputstream.readObject() as ArrayList<Food>
    }
}