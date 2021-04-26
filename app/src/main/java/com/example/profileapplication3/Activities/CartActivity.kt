package com.example.profileapplication3.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.profileapplication3.MainActivity
import com.example.profileapplication3.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity() {
    private lateinit var bottom_nav_view:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initViews()
        handleBottomNavView()


        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,FirstCartFragment())
        fragmentTransaction.commit()

    }

    private fun handleBottomNavView() {
        bottom_nav_view.selectedItemId=R.id.cart
        bottom_nav_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home->{
                    startActivity(Intent(this,MainActivity::class.java))
                }
                R.id.search->{
                    startActivity(Intent(this,SearchActivity::class.java))
                }
                else ->{

                }
            }

            return@OnNavigationItemSelectedListener true
        })
    }

    private fun initViews() {
        bottom_nav_view=findViewById(R.id.bottom_nav_view)
    }
}