package com.example.boeferrob.menuapp.fragments.Adapter

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.boeferrob.menuapp.Ingredient
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.utils.MESUREMENTLIST
import kotlinx.android.synthetic.main.dialog_ingredient.view.*

class IngredientRecyclerAdapter(private val context : Context, private  val ingredient: MutableList<Ingredient>) : RecyclerView.Adapter<IngredientRecyclerAdapter.ViewHolder>() {
    /************************************************variablen*********************************************************/
    private val layoutInflater = LayoutInflater.from(context)

    /************************************************Override**********************************************************/
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView: View = layoutInflater.inflate(R.layout.item_ingredient_list, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = ingredient.size //+ 1

    override fun getItemViewType(position: Int): Int {
            return R.layout.item_ingredient_list
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            val ingredient = ingredient[p1]
            p0.textName?.text = ingredient.name
            p0.textMesurement?.text = ingredient.measurement
            p0.textQuantity?.text = ingredient.quantity.toString()

    }

    /************************************************Methods***********************************************************/
    fun removeItem(viewHolder: RecyclerView.ViewHolder){
        val removedPosition = viewHolder.adapterPosition
        val removedItem = ingredient[viewHolder.adapterPosition]
        ingredient.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "${removedItem.name} deleted.", Snackbar.LENGTH_LONG).setAction("UNDO") {
            ingredient.add(removedPosition, removedItem)
            notifyItemInserted(removedPosition)
        }.show()
    }

    /**************************************************inner class*****************************************************/
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //ingredient textfields for list
        val textQuantity = itemView.findViewById<TextView?>(R.id.txtQuantitiyIngredient)
        val textMesurement = itemView.findViewById<TextView?>(R.id.txtmesurement)
        val textName = itemView.findViewById<TextView?>(R.id.txtNameIngredient)

        init {
            itemView.setOnClickListener {
                //change ingredient dialog
                    val alertdialog = AlertDialog.Builder(context)
                    val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_ingredient, null)

                    mDialogView.spinnerMeasurement.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, MESUREMENTLIST)

                    alertdialog.setTitle("Ingredient")
                    alertdialog.setView(mDialogView)

                    val mAlertDialog = alertdialog.show()
                    val ingredient = ingredient[adapterPosition]
                    val mesurementpos = MESUREMENTLIST.indexOf(ingredient.measurement)

                    mDialogView.txtNameIngredient.setText(ingredient.name)
                    mDialogView.txtquantity.setText(ingredient.quantity.toString())
                    mDialogView.spinnerMeasurement.setSelection(mesurementpos)

                    mDialogView.btnSaveIngredient.setOnClickListener {
                        ingredient.name = mDialogView.txtNameIngredient.text.toString()
                        ingredient.quantity = mDialogView.txtquantity.text.toString().toInt()
                        ingredient.measurement = mDialogView.spinnerMeasurement.selectedItem.toString()
                        mAlertDialog.dismiss()
                        notifyDataSetChanged()
                    }

                    mDialogView.btnCancelIngredient.setOnClickListener {
                        mAlertDialog.dismiss()
                    }
            }
        }
    }
}