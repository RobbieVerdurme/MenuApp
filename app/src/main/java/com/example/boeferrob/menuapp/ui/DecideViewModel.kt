package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.network.DataManager
import kotlin.random.Random

class DecideViewModel: ViewModel() {
    /************************************************variablen*********************************************************/
    private var foodList:MutableLiveData<List<Food>> = DataManager.getFoodList()

    /************************************************Methods***********************************************************/
    fun RandomFood(): String {
        if(foodList?.value.isNullOrEmpty()){
            return "No food in list. Please add food"
        }else{
            val random = Random
            val randomId = random.nextInt(foodList!!.value!!.size)
            return foodList!!.value!!.get(randomId).name
        }
    }


}