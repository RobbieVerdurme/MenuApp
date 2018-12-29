package com.example.boeferrob.menuapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.network.DataManager

class MainViewModel : ViewModel() {
    val currentRandomFoodName: LiveData<Food>
        get() = DataManager.currentRandomFood

    fun onChangeRandomFoodClick() = DataManager.changeCurrentRandomFood()


}