package com.example.profileapplication3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.example.profileapplication3.Activities.CartActivity
import com.example.profileapplication3.Activities.SearchActivity
import com.example.profileapplication3.Adapter.ProductAdapter
import com.example.profileapplication3.DBService.Utils
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.todkars.shimmer.ShimmerRecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolBar: MaterialToolbar
    lateinit var nav_view: NavigationView
    lateinit var bottom_nav_view: BottomNavigationView
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var recViewNewItems:RecyclerView
    lateinit var recViewPopularItems:RecyclerView
    lateinit var recViewSuggestedItems:RecyclerView
    lateinit var newItemsAdapter:ProductAdapter
    lateinit var popularItemAdapter:ProductAdapter
    lateinit var suggestedItemAdapter:ProductAdapter
    lateinit var shimmerRecView:ShimmerRecyclerView
    lateinit var productLayout:RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViwes()
        setSupportActionBar(toolBar)
//        Utils.getInstance()?.insertingInitialData()
        actionBarDrawerToggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolBar,
                R.string.open_nav,
                R.string.close_nav
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        bottom_nav_view.selectedItemId=R.id.home
        productLayout.visibility=View.GONE
        shimmerRecView.showShimmer()
        handlingLeftNavView()
        handlingBottomNaView()
        recViewNewItems.also{
            it.adapter=newItemsAdapter
            it.layoutManager=GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false)
        }
        recViewPopularItems.also{
            it.adapter=popularItemAdapter
            it.layoutManager=GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false)
        }
        recViewSuggestedItems.also {
            it.adapter=suggestedItemAdapter
            it.layoutManager=GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false)
        }

        Utils.getInstance()!!.getAllLiveDataProducts().observe(this, Observer {
            if(it.size>0){
                 shimmerRecView.hideShimmer()
                 productLayout.visibility=View.VISIBLE
                 newItemsAdapter.updatingListItems(it)
            }else {
                shimmerRecView.showShimmer()
                productLayout.visibility=View.GONE
            }
        })
        Utils.getInstance()!!.getAllPopularityProducts().observe(this, Observer {
            if(it.size>0){
                popularItemAdapter.updatingListItems(it)
            }else {
                Toast.makeText(this,"Failed to retrieve data",Toast.LENGTH_SHORT).show()
            }
        })

        Utils.getInstance()!!.getAllSuggestedProducts().observe(this, Observer {
            if (it.size > 0) {
                suggestedItemAdapter.updatingListItems(it)
                Log.i("tag","onCreate: ${it.size} ")
            } else {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })


    }
    private fun handlingLeftNavView() {

        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.cart -> {

                }
                R.id.categories -> {

                }
                R.id.terms -> {

                }
                R.id.rateUs -> {

                }
                else -> {

                }
            }
            return@OnNavigationItemSelectedListener true
        })
    }
    private fun handlingBottomNaView() {
        bottom_nav_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId){
               R.id.search ->{
                   startActivity(Intent(this,SearchActivity::class.java))
               }
                R.id.cart->{
                    startActivity(Intent(this, CartActivity::class.java))
                }
                else ->{

                }
            }
            return@OnNavigationItemSelectedListener true
        })
    }
    private fun initViwes() {
        drawerLayout = findViewById(R.id.drawerLayout)
        toolBar = findViewById(R.id.toolBar)
        nav_view = findViewById(R.id.nav_view)
        bottom_nav_view = findViewById(R.id.bottom_nav_view)
        recViewNewItems=findViewById(R.id.recViewNewItems)
        recViewPopularItems=findViewById(R.id.recViewPopularItems)
        newItemsAdapter= ProductAdapter(this, arrayListOf())
        popularItemAdapter= ProductAdapter(this, arrayListOf())
        suggestedItemAdapter= ProductAdapter(this, arrayListOf())
        recViewSuggestedItems=findViewById(R.id.recViewSuggestedItems)
        shimmerRecView=findViewById(R.id.shimmerRecView)
        productLayout=findViewById(R.id.productItemsLayout)
    }


}