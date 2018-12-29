package com.example.boeferrob.menuapp.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.boeferrob.menuapp.Food
import java.util.*


object DataManager {

    private var foodList :MutableList<Food> = ArrayList<Food>()
    private val _currentRandomFood = MutableLiveData<Food>()

    val currentRandomFood: LiveData<Food>
        get() = _currentRandomFood

    init {
        _currentRandomFood.value = getRandomFood()
    }

    fun getRandomFood(): Food{
        val random = Random()
        return foodList[random.nextInt(foodList.size)]
    }

    fun changeCurrentRandomFood(){
        _currentRandomFood.value = getRandomFood()
    }
}