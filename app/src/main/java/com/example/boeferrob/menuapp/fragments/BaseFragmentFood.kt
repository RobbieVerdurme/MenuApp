package com.example.boeferrob.menuapp.fragments

import android.support.v4.app.Fragment

open class BaseFragmentFood : Fragment() {
    open var TAG : String = ""
    companion object {
        const val DETAIL = 0
        var detail: FoodDetailFragment? = null
        const val INGREDIENTS = 1
        var ingredients: FoodIngredientFragment? = null
        const val PREPERATION = 2
        var preperation: FoodPreperationFragment? = null
    }
}