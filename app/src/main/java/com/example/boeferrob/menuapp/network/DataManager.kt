package com.example.boeferrob.menuapp.network

import com.example.boeferrob.menuapp.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

object DataManager {
    var foodList :MutableList<Food> = ArrayList<Food>()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRefrence = firebaseDatabase.getReference("FoodList")

    init {
        databaseRefrence.keepSynced(true)

        databaseRefrence.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                println("Failed to read value ${p0.message}")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                foodList.clear()
                for (h in dataSnapshot.child("Food").children){
                    val food = h.getValue(Food::class.java)
                    food?.key = h.key
                    foodList.add(food!!)
                    println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK list added ${food.name}")
                }
            }
        })
    }

    fun remove(food:Food){
        databaseRefrence.child("Food").child(food.key!!).removeValue()
    }

    fun save(food : Food){
        if(food.key.isNullOrBlank()){
            databaseRefrence.child("Food").push().setValue(food)
        }else{
            databaseRefrence.child("Food").child(food.key!!).setValue(food)
        }
    }
}