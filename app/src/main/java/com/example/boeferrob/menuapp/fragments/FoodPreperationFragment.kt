package com.example.boeferrob.menuapp.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boeferrob.menuapp.Food

import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.utils.SELECTEDFOOD
import kotlinx.android.synthetic.main.fragment_food_preperation.*

class FoodPreperationFragment : BaseFragment() {
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
        return inflater.inflate(R.layout.fragment_food_preperation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayPreperation()

        txtPreperation.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener!!.onFragmentInteraction(s.toString())
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TAG = "FoodPreperationFragment"

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
        fun onFragmentInteraction(preperation: String)
    }
    /************************************************Methods***********************************************************/
    private fun displayPreperation() {
        txtPreperation.setText(chosenFood.preperation)
    }

    /************************************************companion object**************************************************/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FoodPreperationFragment.
         */
        @JvmStatic
        fun newInstance(food: Food) =
            FoodPreperationFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SELECTEDFOOD, food)
                }
            }
    }
}
