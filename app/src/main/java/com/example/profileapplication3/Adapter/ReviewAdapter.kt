package com.example.profileapplication3.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profileapplication3.Models.Review
import com.example.profileapplication3.R

class ReviewAdapter(val reviews: ArrayList<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    fun updatatingReviews(newReview:ArrayList<Review>){
        reviews.clear()
        reviews.addAll(newReview)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtDate.setText(reviews[position].date)
        holder.txtReview.setText(reviews.get(position).review)
        holder.txtUserName.setText(reviews.get(position).name)
    }


    override fun getItemCount(): Int {
        return reviews.count()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUserName = view.findViewById<TextView>(R.id.txtUserName)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)
        val txtReview = view.findViewById<TextView>(R.id.txtReview)
    }
}