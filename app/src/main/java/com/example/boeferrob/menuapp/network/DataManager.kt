package com.example.boeferrob.menuapp.network

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.Ingredient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.*
import java.util.ArrayList

object DataManager {
    var foodList :MutableList<Food> = ArrayList<Food>()
    //lateinit var file: File
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseRefrence = firebaseDatabase.getReference()

    init {
        databaseRefrence.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                println("Failed to read value ${p0.message}")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    foodList.clear()
                    for (h in dataSnapshot.child("Food").children){
                        val food = h.getValue(Food::class.java)
                        foodList.add(food!!)
                    }
                }
            }
        })
    }

    fun createFile(context: Context){
/*
        val directory = context.filesDir
        file = File(directory,"MenuApp")

        context.openFileInput(file.name).use {
            foodList.add(Food(it.read().toString(), ArrayList<Ingredient>(), ""))
        }

*/

    }

    fun save(){
        /*
        var fileoutputstream = FileOutputStream(file)
        var objectouputstream = ObjectOutputStream(fileoutputstream).use { it.writeObject(foodList) }
        fileoutputstream.flush()
        fileoutputstream.close()
        */
        for (food in foodList) {
            databaseRefrence.child("Food").push().setValue(food)
        }
    }
}