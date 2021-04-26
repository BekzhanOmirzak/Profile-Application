package com.example.profileapplication3.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.profileapplication3.Activities.ProductActivity
import com.example.profileapplication3.Const.product_firebase_key
import com.example.profileapplication3.Models.Product
import com.example.profileapplication3.R
import com.google.android.material.card.MaterialCardView

class ProductAdapter(val context:Context,val products:ArrayList<Product>)  : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    fun updatingListItems(newProducts:ArrayList<Product>){
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.product_item,null,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.also {
           it.txtPrice.setText("${products[position].price} $")
           it.txtProductTitle.setText(products.get(position).name)
       }
       Glide.with(context).asBitmap().load(products.get(position).imageURL).into(holder.imgProduct)

        holder.parent.setOnClickListener{
            val intent= Intent(context,ProductActivity::class.java)
            intent.putExtra(product_firebase_key,products.get(position).product_firebase_id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val txtProductTitle=view.findViewById<TextView>(R.id.txtProductTitle)
        val txtPrice=view.findViewById<TextView>(R.id.txtPrice)
        val imgProduct=view.findViewById<ImageView>(R.id.imgProduct)
        val parent=view.findViewById<MaterialCardView>(R.id.parent)
    }


}