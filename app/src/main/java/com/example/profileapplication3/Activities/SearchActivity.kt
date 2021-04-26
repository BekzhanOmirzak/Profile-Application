package com.example.profileapplication3.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profileapplication3.Adapter.CategoryAdapter
import com.example.profileapplication3.Adapter.ProductAdapter
import com.example.profileapplication3.DBService.Utils
import com.example.profileapplication3.MainActivity
import com.example.profileapplication3.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchActivity : AppCompatActivity(), CategoryAdapter.PassingString{
    private lateinit var bottom_nav_view: BottomNavigationView
    lateinit var edtSearch:EditText
    lateinit var recViewCategories:RecyclerView
    lateinit var recViewResult:RecyclerView
    lateinit var categoryAdapter:CategoryAdapter
    lateinit var productAdapter:ProductAdapter
    lateinit var imgSearch:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        handlingBottomNavView()
        recViewCategories.layoutManager=GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false)
        recViewCategories.adapter=categoryAdapter

        recViewResult.layoutManager=GridLayoutManager(this,2)
        recViewResult.adapter=productAdapter


        categoryAdapter.updatingCategories(Utils.getInstance()!!.getCategories())

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                findProduct(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


    }
    private fun findProduct(s:String) {
        Log.i("tag", "findProduct: Result: $s ")
        Utils.getInstance()!!.getAllLiveDataResultProductsByCategory(s).observe(this, Observer {
            if (!it.isEmpty()) {
                Log.i("tag", "findProduct: AdapterResult ${it.size} ")
                productAdapter.updatingListItems(it)
            }
        })
    }

    private fun handlingBottomNavView() {
        bottom_nav_view.selectedItemId = R.id.search
        bottom_nav_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                }
                else -> {

                }
            }

            return@OnNavigationItemSelectedListener true
        })
    }

    private fun initViews() {
        bottom_nav_view = findViewById(R.id.bottom_nav_view)
        edtSearch=findViewById(R.id.edtSearch)
        recViewCategories=findViewById(R.id.recViewCategories)
        recViewResult=findViewById(R.id.recViewResults)
        categoryAdapter= CategoryAdapter(this, arrayListOf())
        productAdapter= ProductAdapter(this, arrayListOf())
        imgSearch=findViewById(R.id.imgSearch)
    }

    override fun onPassingString(category: String) {
        edtSearch.setText(category)
        findProduct(category)
    }
}