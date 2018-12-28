package com.example.boeferrob.menuapp.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.Ingredient

import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.network.DataManager
import kotlinx.android.synthetic.main.fragment_decide.*
import kotlin.random.Random

class DecideFragment : BaseFragment() {
    /************************************************variablen*********************************************************/
    private var listener: OnFragmentInteractionListener? = null
    private var foodList: MutableList<Food> = ArrayList<Food>()

    /************************************************Override**********************************************************/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_decide, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnDecide.setOnClickListener {RandomFood()}
        foodList = createFood()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        TAG = "DecideFragment"

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
        fun onFragmentInteraction(uri: Uri)
    }

    /************************************************Methods***********************************************************/
    private fun RandomFood() {
        if(foodList.isEmpty()){
            lblMenu.text = "No food in list. Please aan food"
        }else{
            val random = Random
            val randomId = random.nextInt(foodList.size)
            lblMenu.text = foodList[randomId].toString()
        }
    }

    private fun createFood(): MutableList<Food> {
        return DataManager.foodList
    }

    /************************************************companion object**************************************************/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment DecideFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DecideFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
