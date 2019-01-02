package com.example.boeferrob.menuapp.fragments.Adapter


import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.boeferrob.menuapp.Food
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.activities.FoodActivity
import com.example.boeferrob.menuapp.activities.MainActivity
import com.example.boeferrob.menuapp.network.DataManager
import com.example.boeferrob.menuapp.ui.FoodListFragmentViewModel
import com.example.boeferrob.menuapp.utils.FOOD_POSITION

class FoodRecyclerAdapter(private val context : Context, private var food: ArrayList<Food>, private  val viewModel: FoodListFragmentViewModel) : Filterable, RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder>(){

    /************************************************variablen*********************************************************/
    private val layoutInflater = LayoutInflater.from(context)
    private var filterListResult: ArrayList<Food>
    private lateinit var charSearch: String

    /**************************************************init************************************************************/
    init {
        filterListResult = ArrayList(food)
    }

    /************************************************Override**********************************************************/
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_food_llist, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = filterListResult.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val food = filterListResult[p1]
        p0.textFood?.text = food.name
        p0.textDescriptionFood?.text = food.discritpion

    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                charSearch = constraint.toString()
                filter()
                val filterResults = Filter.FilterResults()
                filterResults.values = filterListResult
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterListResult = results?.values as ArrayList<Food>
                notifyDataSetChanged()
            }
        }
    }

    /************************************************Methods***********************************************************/
    fun removeItem(viewHolder: RecyclerView.ViewHolder){
        val removedPosition = viewHolder.adapterPosition
        val removedItem = filterListResult[removedPosition]
        val removedPositionFood = food.indexOf(removedItem)

        food.remove(removedItem)
        viewModel.remove(removedItem)
        filter()

        notifyItemRemoved(removedPosition)

        Snackbar.make(viewHolder.itemView, "${removedItem.name} deleted.", Snackbar.LENGTH_LONG).setAction("UNDO"){
            food.add(removedPositionFood,removedItem)
            viewModel.save(removedItem)
            filter()
            notifyItemInserted(removedPosition)
        }.show()
    }

    fun filter() {
        if(charSearch.isEmpty()){
            filterListResult = ArrayList(food)
        }else{
            val resultList = ArrayList<Food>()
            for (row in food){
                if(row.name.toLowerCase().contains(charSearch.toLowerCase()) or row.ingredients.any { x -> x.name.toLowerCase().contains(charSearch.toLowerCase())})
                    resultList.add(row)
            }
            filterListResult = resultList
        }
    }

    fun setData(foodList: List<Food>){
        food = ArrayList(foodList)
        filterListResult = ArrayList(food)
        notifyDataSetChanged()
    }


    /**************************************************inner class*****************************************************/
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textFood = itemView.findViewById<TextView?>(R.id.txtTitleFood)
        val textDescriptionFood = itemView.findViewById<TextView?>(R.id.txtDescriptionFood)

        init {
            itemView.setOnClickListener{
                val intent = Intent(context, FoodActivity::class.java)
                val index = food.indexOf(filterListResult[adapterPosition])
                intent.putExtra(FOOD_POSITION, index)
                context.startActivity(intent)
            }

        }
    }
}
