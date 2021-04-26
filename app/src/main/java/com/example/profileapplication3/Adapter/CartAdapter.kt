package com.example.profileapplication3.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.profileapplication3.Models.CartItem
import com.example.profileapplication3.R
import java.lang.ClassCastException

class CartAdapter(val context:Context,val cartItems:ArrayList<CartItem>,val fragment: Fragment)  : RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    interface DeletingItems{
        fun onDeletingItems(cartItem:CartItem)
    }


    fun updatingCartItems(newCartItems:ArrayList<CartItem>){
        cartItems.clear()
        cartItems.addAll(newCartItems)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.cari_item,null,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNameProduct.setText(cartItems[position].name)
        holder.txtPrice.setText(" ${cartItems[position].price}$")

        holder.txtDelete.setOnClickListener{

                val builder=AlertDialog.Builder(context).setTitle("Confirmation....")
                    .setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                        try{
                            val deletingItems=fragment as DeletingItems
                            deletingItems.onDeletingItems(cartItems.get(position))
                        }catch (ex:ClassCastException){
                            ex.printStackTrace()
                        }
                    }.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

                    }.setMessage("Are you sure you want to delete this item?")
                    .create()
                builder.show()
        }
    }

    override fun getItemCount(): Int {
       return cartItems.count()
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val txtPrice:TextView=view.findViewById(R.id.txtPrice)
        val txtDelete:TextView=view.findViewById(R.id.txtDelete)
        val txtNameProduct=view.findViewById<TextView>(R.id.txtProductName)
    }


}