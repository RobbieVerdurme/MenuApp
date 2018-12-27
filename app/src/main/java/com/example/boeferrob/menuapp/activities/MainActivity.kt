package com.example.boeferrob.menuapp.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.boeferrob.menuapp.R
import kotlinx.android.synthetic.main.main_layout.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        Logger.addLogAdapter(AndroidLogAdapter())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onStart() {
        super.onStart()
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    viewpager_main.currentItem = BaseFragment
                    true
                }
                R.id.navigation_dashboard -> {

                    true
                }
                R.id.navigation_notifications -> {

                    true
                }
                else ->{

                    true
                }
            }
        }
    }

    viewpager_main.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(p0: Int): Fragment {
            when(p0){
                /*
                BaseFragment.AIRPORTS -> return AirportsFragment.newInstance()
                BaseFragment.RAW -> return RawFragment.newInstance()
                BaseFragment.DETAILS -> return DetailsFragment.newInstance()
                BaseFragment.OLD -> return OldmetarsFragment.newInstance()
                */
            }
            return //AirportsFragment() return  standaard fragment ?
        }

        override fun getCount(): Int {
            return 4
        }
    }
}
