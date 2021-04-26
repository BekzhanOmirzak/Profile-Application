package com.example.profileapplication3.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.profileapplication3.Adapter.ReviewAdapter
import com.example.profileapplication3.Const.product_firebase_key
import com.example.profileapplication3.DBService.Utils
import com.example.profileapplication3.Models.CartItem
import com.example.profileapplication3.Models.Product
import com.example.profileapplication3.R
import com.google.firebase.firestore.FirebaseFirestore

class ProductActivity : AppCompatActivity(), AddReviewDialog.UpdateReview {
    private lateinit var txtTitleProduct: TextView
    private lateinit var txtPrice: TextView
    private lateinit var imgProduct: ImageView
    private lateinit var btnAddToCart:Button
    private lateinit var txtDescription: TextView
    private lateinit var txtAddReview: TextView
    private lateinit var recViewReviews:RecyclerView
    private lateinit var reviewAdapter:ReviewAdapter
    private  var product_fire_id:String?="New ID which should be changed"
    private var incomingProduct:Product?=null
    private lateinit var firsStarLayout:RelativeLayout
    private lateinit var secondStarLayout:RelativeLayout
    private lateinit var thirdStarLayout:RelativeLayout
    private lateinit var fourthStarLayout:RelativeLayout
    private lateinit  var fifthStarLayout:RelativeLayout
    private lateinit var firstEmptyStart:ImageView
    private lateinit var secondEmptyStart:ImageView
    private lateinit var thirdEmptyStart:ImageView
    private lateinit var fourthEmptyStart:ImageView
    private lateinit var fifthEmptyStart:ImageView
    private lateinit var firstFilledStar:ImageView
    private lateinit var secondFilledStart:ImageView
    private lateinit var thirdFilledStart:ImageView
    private lateinit var fourthFilledStart:ImageView
    private lateinit var fifthFilledStart:ImageView
    private var current_start_rate=0

    private val firestore = FirebaseFirestore.getInstance()
    private val product_collection_ref = firestore.collection("Products")

    private  var product_name:String?=null
    private var product_fire_key:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        initViews()

        product_fire_id = intent.getStringExtra(product_firebase_key)
        if (product_fire_id != null) {
            settingAllTheValuesToProductActivity(product_fire_id)
        }

        txtAddReview.setOnClickListener {
            passingBundleThroughArguments(product_fire_id,product_name)
        }

        recViewReviews.also{
            it.adapter=reviewAdapter
            it.layoutManager=LinearLayoutManager(this)
        }
        btnAddToCart.setOnClickListener{
            handleAddingToCartItems(incomingProduct)
        }

        hadleShowingReviews()

        firsStarLayout.setOnClickListener{
            updateStars(1)
            current_start_rate=1
        }
        secondFilledStart.setOnClickListener{
            updateStars(2)
            current_start_rate=2
        }
        thirdStarLayout.setOnClickListener{
            updateStars(3)
            current_start_rate=3
        }
        fourthFilledStart.setOnClickListener{
            updateStars(4)
            current_start_rate=4
        }
        fifthStarLayout.setOnClickListener{
            updateStars(5)
            current_start_rate=5
        }

    }

    private  fun settingAllTheValuesToProductActivity(product_fire_id:String?){
        Utils.getInstance()?.getSingleProductLiveData(product_fire_id!!)?.observe(
            this,
            Observer {
                if (it != null) {
                    txtTitleProduct.setText(it.name)
                    txtPrice.setText("${it.price} $")
                    txtDescription.setText(it.description)
                    Glide.with(this).asBitmap().load(it.imageURL).into(imgProduct)
                    updateStars(4)
                    product_name=it.name
                    product_fire_key=it.product_firebase_id
                    incomingProduct=it
                    current_start_rate=it.rate
                }
            })
    }


    private fun updateStars(number:Int){
        when(number){
            0->{
                firstEmptyStart.visibility=View.VISIBLE
                firstFilledStar.visibility=View.GONE
                secondEmptyStart.visibility=View.VISIBLE
                secondFilledStart.visibility=View.GONE
                thirdEmptyStart.visibility=View.VISIBLE
                thirdFilledStart.visibility=View.GONE
                fourthEmptyStart.visibility=View.VISIBLE
                fourthFilledStart.visibility=View.GONE
                fifthEmptyStart.visibility=View.VISIBLE
                firstFilledStar.visibility=View.GONE
            }
            1->{
                firstEmptyStart.visibility=View.GONE
                firstFilledStar.visibility=View.VISIBLE
                secondEmptyStart.visibility=View.VISIBLE
                secondFilledStart.visibility=View.GONE
                thirdEmptyStart.visibility=View.VISIBLE
                thirdFilledStart.visibility=View.GONE
                fourthEmptyStart.visibility=View.VISIBLE
                fourthFilledStart.visibility=View.GONE
                fifthEmptyStart.visibility=View.VISIBLE
                firstFilledStar.visibility=View.GONE
            }
            2 ->{
                firstEmptyStart.visibility=View.GONE
                firstFilledStar.visibility=View.VISIBLE
                secondEmptyStart.visibility=View.GONE
                secondFilledStart.visibility=View.VISIBLE
                thirdEmptyStart.visibility=View.VISIBLE
                thirdFilledStart.visibility=View.GONE
                fourthEmptyStart.visibility=View.VISIBLE
                fourthFilledStart.visibility=View.GONE
                fifthEmptyStart.visibility=View.VISIBLE
                firstFilledStar.visibility=View.GONE
            }
            3->{
                firstEmptyStart.visibility=View.GONE
                firstFilledStar.visibility=View.VISIBLE
                secondEmptyStart.visibility=View.GONE
                secondFilledStart.visibility=View.VISIBLE
                thirdEmptyStart.visibility=View.GONE
                thirdFilledStart.visibility=View.VISIBLE
                fourthEmptyStart.visibility=View.VISIBLE
                fourthFilledStart.visibility=View.GONE
                fifthEmptyStart.visibility=View.VISIBLE
                firstFilledStar.visibility=View.GONE
            }
            4->{
                firstEmptyStart.visibility=View.GONE
                firstFilledStar.visibility=View.VISIBLE
                secondEmptyStart.visibility=View.GONE
                secondFilledStart.visibility=View.VISIBLE
                thirdEmptyStart.visibility=View.GONE
                thirdFilledStart.visibility=View.VISIBLE
                fourthEmptyStart.visibility=View.GONE
                fourthFilledStart.visibility=View.VISIBLE
                fifthEmptyStart.visibility=View.VISIBLE
                firstFilledStar.visibility=View.GONE
            }
            else ->{
                firstEmptyStart.visibility=View.GONE
                firstFilledStar.visibility=View.VISIBLE
                secondEmptyStart.visibility=View.GONE
                secondFilledStart.visibility=View.VISIBLE
                thirdEmptyStart.visibility=View.GONE
                thirdFilledStart.visibility=View.VISIBLE
                fourthEmptyStart.visibility=View.GONE
                fourthFilledStart.visibility=View.VISIBLE
                fifthEmptyStart.visibility=View.GONE
                firstFilledStar.visibility=View.VISIBLE
            }
        }
    }

    private fun passingBundleThroughArguments(productFireId: String?, productName: String?) {
        val bundle = Bundle();
        bundle.putString(product_firebase_key,productFireId)
        bundle.putString("product_name",productName)
        val addReviewDialog=AddReviewDialog()
        addReviewDialog.arguments=bundle
        Log.i(
            "tag",
            "passingBundleThroughArguments: Inside bundle ${productFireId} and ${productName}"
        )
        addReviewDialog.show(supportFragmentManager,"Show a dialog")
    }

    private fun hadleShowingReviews() {
        Utils.getInstance()!!.getAllLiveDataReviewsByProductFireId(product_fire_id!!)!!.observe(this, Observer {
            if(!it.isEmpty()){
                reviewAdapter.updatatingReviews(it)
            }
        })
    }
    private fun handleAddingToCartItems(incomingProduct: Product?) {
         if(incomingProduct!=null){
              val cartItem= CartItem(Utils.getInstance()!!.getCartItemId(),incomingProduct.name,incomingProduct.price)
             Utils.getInstance()?.insertingCartItems(cartItem)
             startActivity(Intent(this,CartActivity::class.java))
         }
    }

    private fun initViews() {
        txtTitleProduct = findViewById(R.id.txtProductTitle)
        txtPrice = findViewById(R.id.txtPrice)
        imgProduct = findViewById(R.id.imgProduct)
        txtDescription = findViewById(R.id.txtDescription)
        txtAddReview = findViewById(R.id.txtAddReview)
        recViewReviews=findViewById(R.id.recViewReviews)
        reviewAdapter= ReviewAdapter(arrayListOf())
        btnAddToCart=findViewById(R.id.btnAddToCart)
        firsStarLayout=findViewById(R.id.firstStarLayout)
        secondStarLayout=findViewById(R.id.secondStarLayout)
        thirdStarLayout=findViewById(R.id.thirdStarLayout)
        thirdFilledStart=findViewById(R.id.thirdtFilledStar)
        fourthFilledStart=findViewById(R.id.fourthFilledStar)
        fourthStarLayout=findViewById(R.id.fourthStarLayout)
        fifthStarLayout=findViewById(R.id.fifthStarLayout)
        firstEmptyStart=findViewById(R.id.firstEmptyStar)
        firstFilledStar=findViewById(R.id.firstFilledStar)
        secondFilledStart=findViewById(R.id.secondFilledStar)
        secondEmptyStart=findViewById(R.id.secondEmptyStar)
        thirdStarLayout=findViewById(R.id.thirdStarLayout)
        thirdEmptyStart=findViewById(R.id.thirdEmptyStar)
        fourthStarLayout=findViewById(R.id.fourthStarLayout)
        fourthEmptyStart=findViewById(R.id.fourthEmptyStar)
        fifthEmptyStart=findViewById(R.id.fifthEmptyStar)
        fifthFilledStart=findViewById(R.id.fifthFilledStar)
    }

    override fun onUpDateReview() {
        hadleShowingReviews()
    }


    override fun onPause() {
        super.onPause()

    }


}