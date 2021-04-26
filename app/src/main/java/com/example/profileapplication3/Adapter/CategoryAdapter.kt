package com.example.profileapplication3.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profileapplication3.R
import java.lang.Exception

class CategoryAdapter(val context:Context,val categories: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    interface PassingString{
        fun onPassingString(category:String)
    }
    private lateinit  var passingString:PassingString

    fun updatingCategories(newCategories: ArrayList<String>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtCategory.setText(categories.get(position))
        holder.txtCategory.setOnClickListener {
             try{
                 passingString=context as PassingString
                 passingString.onPassingString(categories.get(position))
             }catch (ex:Exception){
                 ex.printStackTrace()
             }
        }
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCategory = view.findViewById<TextView>(R.id.txtCategory)
    }
}
