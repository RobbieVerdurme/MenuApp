package com.example.boeferrob.menuapp.activities

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.fragments.BaseFragment
import com.example.boeferrob.menuapp.fragments.DecideFragment
import com.example.boeferrob.menuapp.fragments.FoodListFragment
import com.example.boeferrob.menuapp.network.DataManager
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity(), DecideFragment.OnFragmentInteractionListener, FoodListFragment.OnFragmentInteractionListener{
    /************************************************variablen*********************************************************/


    /************************************************Override**********************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        DataManager.createFile(this)
    }

    override fun onStart() {
        super.onStart()
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_decider -> {
                    viewpager_main.currentItem = BaseFragment.DECIDE
                    true
                }
                R.id.navigation_recipeList -> {
                    viewpager_main.currentItem = BaseFragment.FOODLIST
                    true
                }
                else ->{
                    viewpager_main.currentItem = BaseFragment.DECIDE
                    true
                }
            }
        }

        viewpager_main.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment {
                when(p0){
                    BaseFragment.DECIDE -> return DecideFragment.newInstance()
                    BaseFragment.FOODLIST -> return FoodListFragment.newInstance()
                }
                return DecideFragment()
            }

            override fun getCount(): Int {
                return 2
            }
        }
    }

    override fun onFragmentInteraction(uri: Uri) {}

    override fun onStop() {
        super.onStop()
        navigation.setOnNavigationItemReselectedListener(null)
    }

    override fun onPause() {
        super.onPause()
        DataManager.save()
    }

    /************************************************Methods***********************************************************/
}
