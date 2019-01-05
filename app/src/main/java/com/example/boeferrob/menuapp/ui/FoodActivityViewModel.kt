package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.network.Repository

class FoodActivityViewModel: ViewModel() {
    /************************************************variablen*********************************************************/
    private var foodList: MutableLiveData<List<Food>> = Repository.getFoodList()
    private var foodListArrayList: ArrayList<Food>? = null

    /************************************************Methods***********************************************************/
    fun getLastIndexFood(): Int{
        return  foodList.value!!.size
    }

    fun addFood(food: Food){
        checkfoodListArrayList()
        foodListArrayList!!.add(food)
        foodList.value = foodListArrayList
    }

    fun saveFood(food: Food){
        Repository.save(food)
    }

    private fun checkfoodListArrayList(){
        if(foodListArrayList.isNullOrEmpty()){
            foodListArrayList = foodList.value as ArrayList<Food>
        }
    }

    /***********************************************get & set**********************************************************/
    fun getFood(index: Int): Food{
        return foodList.value?.get(index)!!
    }
}