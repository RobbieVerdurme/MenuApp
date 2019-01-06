package com.example.boeferrob.menuapp.fragments

import android.support.v4.app.Fragment

open class BaseFragmentFood : Fragment() {
    open var TAG : String = ""
    companion object {
        const val DETAIL = 0
        const val INGREDIENTS = 1
        const val PREPERATION = 2
    }
}