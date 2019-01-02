package com.example.boeferrob.menuapp.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.activities.FoodActivity
import com.example.boeferrob.menuapp.fragments.Adapter.FoodRecyclerAdapter
import com.example.boeferrob.menuapp.ui.FoodListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : BaseFragment() {
    /************************************************variablen*********************************************************/
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var foodListFragmentViewModel: FoodListFragmentViewModel

    /************************************************Override**********************************************************/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        foodListFragmentViewModel = ViewModelProviders.of(activity!!).get(FoodListFragmentViewModel::class.java)
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecylcerView()
    }

    override fun onStart() {
        super.onStart()

        fab.setOnClickListener{
            val activityIntent = Intent(activity, FoodActivity::class.java)
            startActivity(activityIntent)
        }

        txtFilter.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (listFood.adapter as Filterable).filter.filter(txtFilter.text.toString())
            }
        })
    }


    override fun onResume() {
        super.onResume()
        (listFood.adapter as Filterable).filter.filter(txtFilter.text.toString())
        listFood.adapter?.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TAG = "FoodListFragment"

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
    private fun configureRecylcerView(){
        val adapter = FoodRecyclerAdapter(activity!!, foodListFragmentViewModel.getFoodList().value as ArrayList<Food>)
        val layoutManager = LinearLayoutManager(activity)
        val swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
        val  deleteIcon: Drawable = ContextCompat.getDrawable(activity!!,R.drawable.ic_delete_black_24dp)!!

        foodListFragmentViewModel.getFoodList().observe(this, Observer<List<Food>> {adapter.setData(it!!)})

        listFood.layoutManager = layoutManager
        listFood.adapter = adapter
        listFood.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false // niet nodig in deze applicatie
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                (adapter as FoodRecyclerAdapter).removeItem(p0)
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
        itemTouchHelper.attachToRecyclerView(listFood)
    }

    /************************************************companion object**************************************************/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FoodListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FoodListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
