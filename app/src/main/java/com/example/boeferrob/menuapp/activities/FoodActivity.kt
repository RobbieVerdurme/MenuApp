package com.example.boeferrob.menuapp.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.Ingredient
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.fragments.Adapter.IngredientRecyclerAdapter
import com.example.boeferrob.menuapp.ui.FoodActivityViewModel
import com.example.boeferrob.menuapp.utils.FOOD_POSITION
import com.example.boeferrob.menuapp.utils.MESUREMENTLIST
import com.example.boeferrob.menuapp.utils.POSITION_NOT_SET
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.android.synthetic.main.content_food.*
import kotlinx.android.synthetic.main.dialog_ingredient.*
import kotlinx.android.synthetic.main.dialog_ingredient.view.*

class FoodActivity : AppCompatActivity() {
    /************************************************variablen*********************************************************/
    private var foodPosition = POSITION_NOT_SET
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
    private lateinit var  deleteIcon: Drawable
    private lateinit var food: Food
    lateinit var foodActivityViewModel: FoodActivityViewModel

    /************************************************Override**********************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        //create viewmodel
        foodActivityViewModel = ViewModelProviders.of(this).get(FoodActivityViewModel::class.java)

        //food position in the list
        foodPosition = savedInstanceState?.getInt(FOOD_POSITION, POSITION_NOT_SET)?:intent.getIntExtra(FOOD_POSITION,
            POSITION_NOT_SET)

        //get food from list
        if(foodPosition != POSITION_NOT_SET){
            food = foodActivityViewModel.getFood(foodPosition)
        }else {
            food = foodActivityViewModel.getFood(foodActivityViewModel.addFood(Food("","", ArrayList<Ingredient>(), "")))
        }

        //display the food
        displayFood()

        //button to add an ingredient
        fabAddIngredient.setOnClickListener {showAlertIngredient()}
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FOOD_POSITION, foodPosition)
    }

    override fun onResume() {
        super.onResume()
        listFoodIngredients.adapter?.notifyDataSetChanged()
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
            }
        }
        return super.onOptionsItemSelected(item)

    }

    /************************************************Methods***********************************************************/
    private fun displayFood() {
        txtTitleFood.setText(food.name)
        txtDescriptionFood.setText(food.discritpion)
        configAdapterListFoodIngredients()
    }

    private fun configAdapterListFoodIngredients(){
        val adapter = IngredientRecyclerAdapter(this,food.ingredients)
        val layouManager = LinearLayoutManager(this)

        listFoodIngredients.adapter = adapter
        listFoodIngredients.layoutManager = layouManager
        listFoodIngredients.addItemDecoration(DividerItemDecoration(listFoodIngredients.context, layouManager.orientation))

        deleteIcon = ContextCompat.getDrawable(this,R.drawable.ic_delete_black_24dp)!!

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false //niet nodig voor dit programma
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                (adapter as IngredientRecyclerAdapter).removeItem(p0)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemvView = viewHolder.itemView
                val iconMargin = (itemvView.height - deleteIcon.intrinsicHeight) / 2

                if(dX >0){
                    swipeBackground.setBounds(itemvView.left, itemvView.top, dX.toInt(), itemvView.bottom)
                    deleteIcon.setBounds(itemvView.left + iconMargin, itemvView.top + iconMargin, itemvView.left + iconMargin + deleteIcon.intrinsicWidth, itemvView.bottom - iconMargin)
                }else{
                    swipeBackground.setBounds(itemvView.left + dX.toInt(), itemvView.top, itemvView.right, itemvView.bottom)
                    deleteIcon.setBounds(itemvView.right - iconMargin - deleteIcon.intrinsicWidth, itemvView.top + iconMargin, itemvView.right - iconMargin, itemvView.bottom - iconMargin)
                }
                swipeBackground.draw(c)

                c.save()
                if(dX > 0 ) {
                    c.clipRect(itemvView.left, itemvView.top, dX.toInt(), itemvView.bottom)
                } else {
                    c.clipRect(itemvView.left + dX.toInt(), itemvView.top, itemvView.right, itemvView.bottom)
                }

                deleteIcon.draw(c)

                c.restore()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(listFoodIngredients)
    }

    private fun checkRequiredFieldsFood(): Boolean{
        var check = true

        if(textFieldEmpty(txtTitleFood)){
            txtTitleFood.error = getString(R.string.required)
            check = false
        }

        if(textFieldEmpty(txtDescriptionFood)){
            txtDescriptionFood.error = getString(R.string.required)
            check = false
        }

        return check
    }

    private fun checkRequiredFieldsIngredient(txtName: EditText, txtquantity: EditText): Boolean{
        var check = true

        if (txtName.text.toString().trim().isNullOrBlank()){
            txtName.error = getString(R.string.required)
            check = false
        }

        if(txtquantity.text.toString().trim().isNullOrBlank()){
            txtquantity.error = getString(R.string.required)
            check = false
        }

        return check
    }

    private fun textFieldEmpty(textField: EditText): Boolean {
        val text = textField.text.toString()
        return text.trim() == ""
    }

    private fun showAlertIngredient(){
        val alertdialog = AlertDialog.Builder(this)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_ingredient,null)

        mDialogView.spinnerMeasurement.adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, MESUREMENTLIST)

        alertdialog.setTitle("Ingredient")
        alertdialog.setView(mDialogView)

        val mAlertDialog = alertdialog.show()

        mDialogView.btnSaveIngredient.setOnClickListener {
            if (checkRequiredFieldsIngredient(mDialogView.txtName, mDialogView.txtquantity)){
                mAlertDialog.dismiss()
                val name = mDialogView.txtName.text.toString()
                val quantity = mDialogView.txtquantity.text.toString().toInt()
                val mesurement = mDialogView.spinnerMeasurement.selectedItem.toString()

                food.ingredients.add(Ingredient(name,quantity,mesurement))
                listFoodIngredients.adapter?.notifyItemInserted(food.ingredients.lastIndex)
            }
        }

        mDialogView.btnCancelIngredient.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun saveFood() {
        if(checkRequiredFieldsFood()){
            food.name = txtTitleFood.text.toString()
            food.discritpion = txtDescriptionFood.text.toString()
            foodActivityViewModel.saveFood(food)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else if (food.name.isNullOrBlank() or food.discritpion.isNullOrBlank()){
            foodActivityViewModel.deleteFood(food)
        }
    }

}
