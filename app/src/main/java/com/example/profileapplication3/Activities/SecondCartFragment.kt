package com.example.profileapplication3.Activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.profileapplication3.DBService.Utils
import com.example.profileapplication3.Models.Order
import com.example.profileapplication3.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SecondCartFragment : Fragment() {
    private  lateinit var edtAddress:EditText
    private lateinit var edtZipCode:EditText
    private lateinit var edtPhoneNumber:EditText
    private lateinit var edtEmail:EditText
    private lateinit var btnBack:Button
    private lateinit var btnNext:Button
    private lateinit var txtWarning:TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.second_fragment,null,false)
        initViews(view)

        val bundle=arguments
        if(bundle!=null){
            val jsonString=bundle.getString("order")
            val type= object : TypeToken<Order>(){}.type
            val order=Gson().fromJson<Order>(jsonString,type)
            edtAddress.setText(order.address)
            edtZipCode.setText(order.zipCode)
            edtPhoneNumber.setText(order.phoneNumber)
            edtEmail.setText(order.email)
        }

        backButton()
        nextButton()

        return view
    }

    private fun nextButton() {
        btnNext.setOnClickListener{
            if(validateData()){
                val bundle=Utils.getInstance()!!.getListOfCartItemsAndPrice()
                val item_names=bundle.getString("name")
                val total_price=bundle.getDouble("sum")
                Log.i("tag", "nextButton: ${item_names + total_price}")
                txtWarning.visibility=View.GONE
                val order= Order(Utils.getInstance()!!.getOrderId(),edtAddress.text.toString(),edtEmail.text.toString(),edtPhoneNumber.text.toString(),
                  edtZipCode.text.toString(),purchased_items =item_names!!,price = total_price
                )
                Log.i("tag", "nextButton: $order ")
                val json= Gson()
                val jsonOrder=json.toJson(order)
                val passing_bundle=Bundle()
                passing_bundle.putString("order",jsonOrder)
                val thirdFragment=ThirdFragment()
                thirdFragment.arguments=passing_bundle
                val transaction=activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container,thirdFragment)
                transaction.commit()

            }else {
                txtWarning.visibility=View.VISIBLE
            }
        }
    }

    private fun validateData(): Boolean {
        if(edtAddress.text.toString().trim().equals("") or edtEmail.text.toString().trim().equals("") or edtPhoneNumber.text.toString().equals("")
            or edtZipCode.text.toString().trim().equals("")){
            return false
        }

        return true
    }


    private fun backButton() {
        btnBack.setOnClickListener{
            val transaction=activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,FirstCartFragment())
            transaction.commit()
        }
    }


    private fun initViews(view: View?) {
        edtAddress=view!!.findViewById(R.id.edtAddress)
        edtZipCode=view.findViewById(R.id.edtZipCode)
        edtPhoneNumber=view.findViewById(R.id.edtPhoneNumber)
        edtEmail=view.findViewById(R.id.edtEmail)
        btnBack=view.findViewById(R.id.btnBack)
        btnNext=view.findViewById(R.id.btnNext)
        txtWarning=view.findViewById(R.id.viewWarning)
        txtWarning.visibility=View.GONE
    }




}