package com.example.profileapplication3.Activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.profileapplication3.MainActivity
import com.example.profileapplication3.R

class FourthFragment : Fragment() {

    private lateinit var btnStartShopping:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=LayoutInflater.from(activity).inflate(R.layout.fourth_fragment,null,false)
        initViews(view)

         btnStartShopping.setOnClickListener{
             startActivity(Intent(activity,MainActivity::class.java))
         }

        return view
    }

    private fun initViews(view: View?) {
        btnStartShopping=view!!.findViewById(R.id.btnStartShopping)
    }


}