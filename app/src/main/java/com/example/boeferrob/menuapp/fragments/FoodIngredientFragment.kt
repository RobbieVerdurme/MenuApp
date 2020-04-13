package com.example.boeferrob.menuapp.fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.Ingredient
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.fragments.Adapter.IngredientRecyclerAdapter
import com.example.boeferrob.menuapp.utils.MESUREMENTLIST
import com.example.boeferrob.menuapp.utils.SELECTEDFOOD
import kotlinx.android.synthetic.main.fragment_food_ingredient.*
import kotlinx.android.synthetic.main.item_add_ingredient_list.*


class FoodIngredientFragment : BaseFragment() {
    /************************************************variablen*********************************************************/
    private lateinit var chosenFood: Food
    private var listener: OnFragmentInteractionListener? = null

    /************************************************Override**********************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            chosenFood = it.getSerializable(SELECTEDFOOD) as Food
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_food_ingredient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayIngredients()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TAG = "FoodIngredientFragment"

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onResume() {
        super.onResume()
        listFoodIngredients.adapter?.notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
    /************************************************Methods***********************************************************/
    private fun displayIngredients() {
        val swipeBackground: ColorDrawable = ColorDrawable(resources.getColor(R.color.colorAccent))
        val  deleteIcon: Drawable = ContextCompat.getDrawable(activity!!,R.drawable.ic_delete_black_24dp)!!
        val adapter = IngredientRecyclerAdapter(activity!!,chosenFood.ingredients)
        val layouManager = LinearLayoutManager(activity)

        listFoodIngredients.adapter = adapter
        listFoodIngredients.layoutManager = layouManager
        listFoodIngredients.addItemDecoration(DividerItemDecoration(listFoodIngredients.context, layouManager.orientation))

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
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
/////////////////// add ingredient
        spinnerMesurement.adapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, MESUREMENTLIST)
        imgAddIngredient.setOnClickListener {
            //check if fields are filled in
            if(checkRequiredFieldsIngredient(txtName, txtQuantity)){
                //adding ingredient
                chosenFood.ingredients.add(
                    Ingredient(
                        txtName.text.toString(),
                        txtQuantity.text.toString(),
                        spinnerMesurement.selectedItem.toString())
                )
                adapter.notifyDataSetChanged()

                //clear textfields
                txtName.setText("")
                txtQuantity.setText("")
                spinnerMesurement.setSelection(0)
            }
        }
    }

    private fun checkRequiredFieldsIngredient(txtName: TextView, txtquantity: TextView): Boolean{
        if(txtName.text.toString().trim().isBlank() or txtquantity.toString().trim().isBlank()){
            Toast.makeText(activity, R.string.checkIngredientFields, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    /************************************************companion object**************************************************/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FoodIngredientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(food: Food) =
            FoodIngredientFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SELECTEDFOOD, food)
                }
            }
    }
}
