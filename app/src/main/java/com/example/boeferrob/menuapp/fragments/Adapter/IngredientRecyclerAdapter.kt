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
        val itemView: View
        if (p1 == R.layout.item_ingredient_list) {
            itemView = layoutInflater.inflate(R.layout.item_ingredient_list, p0, false)
        } else {
            itemView = layoutInflater.inflate(R.layout.item_add_ingredient_list, p0, false)
        }
        return ViewHolder(itemView)
    }

    override fun getItemCount() = ingredient.size + 1

    override fun getItemViewType(position: Int): Int {
        if(position == ingredient.size){
            return R.layout.item_add_ingredient_list
        }else{
            return R.layout.item_ingredient_list
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(p1 == ingredient.size){
            p0.spinnerMesurement.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, MESUREMENTLIST)
            p0.imgAddIngredient.setOnClickListener {
                //check if fields are filled in
                if(checkRequiredFieldsIngredient(p0.txtaddName, p0.txtaddQuantity)){
                    //adding ingredient
                    ingredient.add(Ingredient(
                        p0.txtaddName.text.toString(),
                        p0.txtaddQuantity.text.toString().toInt(),
                        p0.spinnerMesurement.selectedItem.toString()))
                    notifyDataSetChanged()

                    //clear textfields
                    p0.txtaddName.setText("")
                    p0.txtaddQuantity.setText("")
                    p0.spinnerMesurement.setSelection(0)
                }
            }
        }else {
            val ingredient = ingredient[p1]
            p0.textName?.text = ingredient.name
            p0.textMesurement?.text = ingredient.measurement
            p0.textQuantity?.text = ingredient.quantity.toString()
        }

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

    private fun checkRequiredFieldsIngredient(txtName: TextView, txtquantity: TextView): Boolean{
        if(txtName.text.toString().trim().isBlank() or txtquantity.toString().trim().isBlank()){
            Toast.makeText(context, "Fill in the textfields before adding an ingredient", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    /**************************************************inner class*****************************************************/
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //itemview
        val textQuantity = itemView.findViewById<TextView?>(R.id.txtQuantitiyIngredient)
        val textMesurement = itemView.findViewById<TextView?>(R.id.txtmesurement)
        val textName = itemView.findViewById<TextView?>(R.id.txtNameIngredient)

        //addview
        val txtaddName = itemView.findViewById<TextView>(R.id.txtName)
        val txtaddQuantity = itemView.findViewById<TextView>(R.id.txtQuantity)
        val spinnerMesurement = itemView.findViewById<Spinner>(R.id.spinnerMesurement)
        val imgAddIngredient = itemView.findViewById<ImageView>(R.id.imgAddIngredient)

        init {
            itemView.setOnClickListener {
                if (adapterPosition == ingredient.size) {
                    Toast.makeText(context, R.string.pleaseAddIngredient, Toast.LENGTH_LONG).show()
                } else {
                    //aanpassen view
                    //it = layoutInflater.inflate(R.layout.item_ingredient_list, p0, false)



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
}