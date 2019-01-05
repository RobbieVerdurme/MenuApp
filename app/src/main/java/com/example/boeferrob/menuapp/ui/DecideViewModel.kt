package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.network.Repository
import com.example.boeferrob.menuapp.utils.POSITION_NOT_SET
import kotlin.random.Random

class DecideViewModel: ViewModel() {
    /************************************************variablen*********************************************************/
    private var foodList:MutableLiveData<List<Food>> = Repository.getFoodList()
    private var chosenRandomfood: Food? = null

    /************************************************Methods***********************************************************/
    fun RandomFood(): String {
        if(foodList.value.isNullOrEmpty()){
            return "No food in list. Please add food"
        }else{
            val random = Random
            val randomId = random.nextInt(foodList.value!!.size)
            chosenRandomfood = foodList.value!!.get(randomId)
            return chosenRandomfood!!.name
        }
    }

    fun getFoodIndex(): Int{
        if(chosenRandomfood == null){
            return POSITION_NOT_SET
        }else {
            return foodList.value!!.indexOf(chosenRandomfood!!)
        }
    }
}