package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.network.DataManager

class FoodActivityViewModel: ViewModel() {
    /************************************************variablen*********************************************************/
    private var foodList: MutableLiveData<List<Food>> = DataManager.getFoodList()
    private var foodListArrayList: ArrayList<Food>? = null

    /************************************************Methods***********************************************************/
    fun addFood(food: Food): Int{
        checkfoodListArrayList()
        foodListArrayList!!.add(food)

        foodList.value = foodListArrayList
        return  foodListArrayList!!.lastIndex
    }

    fun saveFood(food: Food){
        DataManager.save(food)
    }

    fun deleteFood(food: Food){
        checkfoodListArrayList()
        foodListArrayList!!.remove(food)

        foodList.value = foodListArrayList
    }

    private fun checkfoodListArrayList(){
        if(foodListArrayList.isNullOrEmpty()){
            foodListArrayList = foodList.value as ArrayList<Food>
        }
    }

    /***********************************************get & set**********************************************************/
    fun getFood(index: Int): Food{
        return foodList?.value?.get(index)!!
    }
}