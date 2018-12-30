package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.network.DataManager

class FoodListFragmentViewModel:ViewModel(){
    /************************************************variablen*********************************************************/
    private var foodList: MutableLiveData<List<Food>> = DataManager.getFoodList()

    /***********************************************Methods***********************************************************/
    fun remove(removedItem : Food){
        DataManager.remove(removedItem)
    }

    fun save(food: Food){
        DataManager.save(food)
    }

    /***********************************************get & set**********************************************************/
    fun getFoodList() : LiveData<List<Food>>{
        return foodList as LiveData<List<Food>>
    }
}