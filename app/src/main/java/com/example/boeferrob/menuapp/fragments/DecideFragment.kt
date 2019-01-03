package com.example.boeferrob.menuapp.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.activities.FoodActivity
import com.example.boeferrob.menuapp.ui.DecideViewModel
import com.example.boeferrob.menuapp.utils.FOOD_POSITION
import com.example.boeferrob.menuapp.utils.POSITION_NOT_SET
import kotlinx.android.synthetic.main.fragment_decide.*

class DecideFragment : BaseFragment() {
    /************************************************variablen*********************************************************/
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var decideViewModel: DecideViewModel


    /************************************************Override**********************************************************/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        decideViewModel = ViewModelProviders.of(activity!!).get(DecideViewModel::class.java)
        return inflater.inflate(R.layout.fragment_decide, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnDecide.setOnClickListener {
            lblMenu.setText(decideViewModel.RandomFood())
        }

        lblMenu.setOnClickListener {
            val index = decideViewModel.getFoodIndex()
            if (index == POSITION_NOT_SET){
                Toast.makeText(activity,"Click the decide button to choose food", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(activity, FoodActivity::class.java)
                intent.putExtra(FOOD_POSITION, index)
                startActivity(intent)
            }
        }
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
