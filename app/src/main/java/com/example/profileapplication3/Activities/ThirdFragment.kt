package com.example.profileapplication3.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.profileapplication3.DBService.Utils
import com.example.profileapplication3.Models.Order
import com.example.profileapplication3.R
import com.example.profileapplication3.ServerService.OrderServerInterface
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdFragment : Fragment() {

    private lateinit var txtItemsName:TextView
    private lateinit var txtAddress:TextView
    private lateinit var txtTotalPrice:TextView
    private lateinit var txtPhoneNumber:TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnBack:Button
    private lateinit var btnCheckOut:Button
    private var type=object : TypeToken<Order>(){}.type
    private lateinit  var orderServerInterface:OrderServerInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.third_cart_fragment,null,false)
        initViews(view)

        val bundle=arguments
        val jsonOrder=bundle?.getString("order")
        if(bundle!=null) {
            val order = Gson().fromJson<Order>(jsonOrder, type)
            txtItemsName.setText(order.purchased_items)
            txtAddress.setText(order.address)
            txtPhoneNumber.setText(order.phoneNumber)
            txtTotalPrice.setText("${order.price}")



            btnBack.setOnClickListener {
                val backBundle = Bundle()
                backBundle.putString("order", jsonOrder)
                val secondFragment = SecondCartFragment()
                secondFragment.arguments = backBundle
                val transaction=activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container,secondFragment)
                transaction.commit()
            }

            btnCheckOut.setOnClickListener{
                 when(radioGroup.checkedRadioButtonId){
                     R.id.PayPal ->{
                         order.payment_method="PayPal"
                     }
                     R.id.creditCart ->{
                         order.payment_method="Credit Cart"
                     }
                     else ->{
                         order.payment_method="Unknown"
                     }

                 }
                 order.success=true

                Utils.getInstance()!!.insertOrder(order)
//                val service=orderServerInterface.sendToServer(order)
//                service.enqueue(object : Callback<Order>{
//                    override fun onResponse(call: Call<Order>, response: Response<Order>) {
//                        if(response.isSuccessful){
//                            Toast.makeText(activity,"Success",Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Order>, t: Throwable) {
//                        Toast.makeText(activity,"Failure",Toast.LENGTH_SHORT).show()
//                    }
//                })
                  Utils.getInstance()!!.deleteItemsFromCart()
                  val transaction=activity!!.supportFragmentManager.beginTransaction()
                  transaction.replace(R.id.container,FourthFragment())
                  transaction.commit()

            }
        }


        return view
    }

    private fun initViews(view: View?) {
       txtItemsName=view!!.findViewById(R.id.txtitemNames)
        txtAddress=view.findViewById(R.id.txtAddress)
        txtTotalPrice=view.findViewById(R.id.txtPrice)
        txtPhoneNumber=view.findViewById(R.id.txtPhoneNumber)
        radioGroup=view.findViewById(R.id.radioGroupPayment)
        btnBack=view.findViewById(R.id.btnBack)
        btnCheckOut=view.findViewById(R.id.btnCheckOut)
    }

}