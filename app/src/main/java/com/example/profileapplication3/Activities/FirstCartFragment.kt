package com.example.profileapplication3.Activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profileapplication3.Adapter.CartAdapter
import com.example.profileapplication3.DBService.Utils
import com.example.profileapplication3.Models.CartItem
import com.example.profileapplication3.R

class FirstCartFragment : Fragment(),CartAdapter.DeletingItems {
    private lateinit var txtSum:TextView
    private lateinit var recViewCartItems:RecyclerView
    private lateinit var btnNext:Button
    private lateinit var cartItemAdapter:CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.first_fragment,null,false)
        initViews(view)

         recViewCartItems.also{
             it.layoutManager=LinearLayoutManager(activity)
             it.adapter=cartItemAdapter
         }

        showingItemInAdapter()

        btnNext.setOnClickListener{
            val transaction=activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,SecondCartFragment())
            transaction.commit()
        }

        return view
    }

    private fun initViews(view: View?) {
        txtSum=view!!.findViewById(R.id.txtSum)
        recViewCartItems=view.findViewById(R.id.recViewCartItems)
        btnNext=view.findViewById(R.id.btnNext)
        cartItemAdapter= activity?.let { CartAdapter(it, arrayListOf(),this) }!!
    }

    private fun showingItemInAdapter(){
        Utils.getInstance()!!.getAllLiveDataCartItems().observe(this, Observer {
            Log.i("tag", "onCreateView: cartItems $it ")
            cartItemAdapter.updatingCartItems(it)
            calculatingSumOfCartItems(it)
        })
    }

    private fun calculatingSumOfCartItems(cartItems:ArrayList<CartItem>){
        var sum=0.00
        for(item in cartItems){
            sum+=item.price
        }

        txtSum.setText("Total Sum:"+String.format("%.2f",sum)+"$")
    }

    override fun onDeletingItems(cartItem: CartItem) {
          Utils.getInstance()?.deletingCartItems(cartItem.cart_fire_id)
          showingItemInAdapter()
    }


}