package com.example.boeferrob.menuapp.fragments

import android.support.v4.app.Fragment

open class BaseFragment: Fragment() {
    open var TAG : String = ""

    companion object {
        const val DECIDE = 0
        const val FOODLIST = 1
    }
}