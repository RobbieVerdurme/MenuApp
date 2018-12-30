package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.network.DataManager

class FoodActivityViewModel: ViewModel() {
    /************************************************variablen*********************************************************/
    private var foodList: MutableLiveData<List<Food>> = DataManager.getFoodList()

    /************************************************Methods***********************************************************/
    fun addFood(food: Food): Int{
        val foodListArrayList = foodList.value as ArrayList<Food>
        foodListArrayList.add(food)

        foodList.value = foodListArrayList
        return  foodListArrayList.lastIndex
    }

    fun saveFood(food: Food){
        DataManager.save(food)
    }

    /***********************************************get & set**********************************************************/
    fun getFood(index: Int): Food{
        return foodList?.value?.get(index)!!
    }
}