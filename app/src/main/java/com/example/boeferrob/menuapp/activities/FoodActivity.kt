package com.example.boeferrob.menuapp.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.Ingredient
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.fragments.*
import com.example.boeferrob.menuapp.ui.FoodActivityViewModel
import com.example.boeferrob.menuapp.utils.*
import kotlinx.android.synthetic.main.activity_food.*

class FoodActivity : AppCompatActivity(), FoodDetailFragment.OnFragmentInteractionListener, FoodIngredientFragment.OnFragmentInteractionListener, FoodPreperationFragment.OnFragmentInteractionListener {
    /************************************************variablen*********************************************************/
    private var foodPosition = POSITION_NOT_SET
    private var logedin = POSITION_NOT_SET
    private lateinit var food: Food
    private var edit = false
    lateinit var foodActivityViewModel: FoodActivityViewModel

    /************************************************Override**********************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        toolbar.setLogo(R.drawable.menu_logo_navbar)
        toolbar.titleMarginStart = 50
        setSupportActionBar(toolbar)

        //create viewmodel
        foodActivityViewModel = ViewModelProviders.of(this).get(FoodActivityViewModel::class.java)

        //food position in the list
        foodPosition = savedInstanceState?.getInt(FOOD_POSITION, POSITION_NOT_SET)?:intent.getIntExtra(FOOD_POSITION, POSITION_NOT_SET)

        //see if you're logged on
        logedin = intent.getIntExtra(LOGIN, POSITION_NOT_SET)

        //see if you edit food
        edit = intent.getBooleanExtra(EDIT,false)

        getFood()
        configureViewpager()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FOOD_POSITION, foodPosition)
    }

    override fun onPause() {
        super.onPause()
        edit = false
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.foodactionsmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when(id){
            R.id.action_save -> {
                saveFood()
                edit = false
            }
            R.id.action_edit -> {
                edit = true
                val adapter: FragmentPagerAdapter = viewpager_food.adapter as FragmentPagerAdapter
                val fragments: List<Fragment?> = listOf(BaseFragmentFood.detail, BaseFragmentFood.ingredients, BaseFragmentFood.preperation)//adapter.getItem(0), adapter.getItem(1), adapter.getItem(2))
                val bundle = Bundle()

                bundle.putBoolean(EDIT, edit)
                bundle.putSerializable(SELECTEDFOOD, food)

                for (fragment in fragments) {
                    fragment?.arguments = bundle
                }
                adapter.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    //detail fragment
    override fun onFragmentInteraction(titleFood: String, description:String) {
        if(titleFood.isNotBlank()){
            food.name = titleFood
        }

        if(description.isNotBlank()){
            food.discritpion = description
        }
    }

    //ingredient fragment
    override fun onFragmentInteraction(uri: Uri) {}

    //preperation fragment
    override fun onFragmentInteraction(preperation: String) {
        food.preperation = preperation
    }
    /************************************************Methods***********************************************************/
    private fun saveFood() {
        if (logedin == POSITION_NOT_SET){
            foodPosition = foodActivityViewModel.getLastIndexFood() + 1
            foodActivityViewModel.addFood(food)
        }

        if(food.name.isNotBlank()){
            foodActivityViewModel.saveFood(food)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(LOGIN, logedin)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Fill in a name for the food",Toast.LENGTH_LONG).show()
        }
    }
    private fun getFood(){
        //get food from list
        if(foodPosition != POSITION_NOT_SET){
            food = foodActivityViewModel.getFood(foodPosition)
        }else {
            food = Food("","", ArrayList<Ingredient>(), "","")
        }
    }

    private fun configureViewpager(){
        viewpager_food.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(p0: Int): Fragment {
                when(p0){
                    BaseFragmentFood.DETAIL ->{
                        BaseFragmentFood.detail = FoodDetailFragment.newInstance(food,edit)
                        return BaseFragmentFood.detail!!
                    }
                    BaseFragmentFood.INGREDIENTS -> {
                        BaseFragmentFood.ingredients = FoodIngredientFragment.newInstance(food, edit)
                        return BaseFragmentFood.ingredients!!
                    }
                    BaseFragmentFood.PREPERATION -> {
                        BaseFragmentFood.preperation = FoodPreperationFragment.newInstance(food, edit)
                        return BaseFragmentFood.preperation!!
                    }
                }
                return FoodDetailFragment()
            }

            override fun getCount(): Int {
                return 3
            }

            override fun getPageTitle(position: Int): CharSequence? {
                when(position){
                    0 -> return "Detail"
                    1 -> return "Ingredients"
                    2 -> return "Preperation"
                }
                return "Page not Found"
            }

        }

        tabLayoutFood.setupWithViewPager(viewpager_food)
    }
}
