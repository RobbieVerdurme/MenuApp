package com.example.boeferrob.menuapp.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.Ingredient
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.utils.EDIT
import com.example.boeferrob.menuapp.utils.SELECTEDFOOD
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FoodDetailFragment : BaseFragment() {
    /************************************************variablen*********************************************************/
    private var chosenFood: Food = Food("", "" ,ArrayList<Ingredient>(), "", "")
    private var listener: OnFragmentInteractionListener? = null
    private var edit = false

    /************************************************Override**********************************************************/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            chosenFood = it.getSerializable(SELECTEDFOOD) as Food
            edit = it.getBoolean(EDIT)
        }

        displayFood()

        if(edit){
            onClicklistenerTextView()
        }else{
            txtTitleFood.isEnabled = false
            txtDescriptionFood.isEnabled = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TAG = "FoodDetailFragment"

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

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(titleFood: String, description: String)
    }
    /************************************************Methods***********************************************************/
    private fun displayFood(){
        txtTitleFood.setText(chosenFood.name)
        txtDescriptionFood.setText(chosenFood.discritpion)
    }

    private fun onClicklistenerTextView(){
        txtTitleFood.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener!!.onFragmentInteraction(s.toString(),"")
            }
        })

        txtDescriptionFood.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener!!.onFragmentInteraction("", s.toString())
            }

        })
    }
    /************************************************companion object**************************************************/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FoodDetailFragment.
         */
        @JvmStatic
        fun newInstance(food: Food, edit: Boolean) =
            FoodDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SELECTEDFOOD, food)
                    putBoolean(EDIT, edit)
                }
            }
    }
}
