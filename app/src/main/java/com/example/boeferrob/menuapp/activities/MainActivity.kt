package com.example.boeferrob.menuapp.activities

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.fragments.BaseFragment
import com.example.boeferrob.menuapp.fragments.DecideFragment
import com.example.boeferrob.menuapp.fragments.FoodListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DecideFragment.OnFragmentInteractionListener, FoodListFragment.OnFragmentInteractionListener{
    /************************************************variablen*********************************************************/


    /************************************************Override**********************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        viewpager_main.addOnPageChangeListener(object :  ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigation.menu.getItem(position).isChecked = true
            }
        })
    }


    override fun onFragmentInteraction(uri: Uri) {}

    override fun onStop() {
        super.onStop()
        navigation.setOnNavigationItemReselectedListener(null)
    }

    /************************************************Methods***********************************************************/
}
