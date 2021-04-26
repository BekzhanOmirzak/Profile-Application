package com.example.profileapplication3.DBService

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.profileapplication3.Models.CartItem
import com.example.profileapplication3.Models.Order
import com.example.profileapplication3.Models.Product
import com.example.profileapplication3.Models.Review
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Utils private constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val product_collection_ref = firestore.collection("Products")
    private val cartItem_collection_ref = firestore.collection("CartItems")
    private val order_coollection_ref = firestore.collection("Orders")
    private val allLiveDataProducts = MutableLiveData<ArrayList<Product>>()
    private val allLiveDataPopularProducts = MutableLiveData<ArrayList<Product>>()
    private val allLiveDataSuggestedProducts = MutableLiveData<ArrayList<Product>>()
    private var productId = 0
    private var cartItem_id = 0
    private var order_id = 0
    private val LiveDataResultProducts = MutableLiveData<ArrayList<Product>>()
    private val singleProductLiveData = MutableLiveData<Product>()
    private val LiveDataReviews = MutableLiveData<ArrayList<Review>>()
    val review_collection_ref = firestore.collection("Reviews")
    private val allLiveDataCartItems = MutableLiveData<ArrayList<CartItem>>()


    private fun getId(): Int {
        productId++
        return productId
    }

    fun getCartItemId(): Int {
        cartItem_id++
        return cartItem_id
    }

    fun getOrderId(): Int {
        order_id++
        return order_id
    }


    companion object {
        private var INSTANCE: Utils? = null
        fun getInstance(): Utils? {
            if (INSTANCE == null) {
                INSTANCE = Utils()
            }
            return INSTANCE
        }
    }

    fun insertingInitialData() {
        val products = ArrayList<Product>()
        products.add(
            Product(
                getId(),
                "Milk",
                "the white liquid produced by cows, goats, and sheep and used by humans as a drink or for making butter, cheese, etc.:",
                "https://images-na.ssl-images-amazon.com/images/I/71TY0kSO2tL._SL1500_.jpg",
                "Drink",
                32.32,
                100
            )
        )

        products.add(
            Product(
                getId(),
                "Bread",
                "the white liquid produced by cows, goats, and sheep and used by humans as a drink or for making butter, cheese, etc.:",
                "https://images-na.ssl-images-amazon.com/images/I/61nhHDCo0dL._SL1000_.jpg",
                "Food",
                12.23,
                98
            )
        )
        products.add(
            Product(
                getId(),
                "Coca-Cola",
                "the white liquid produced by cows, goats, and sheep and used by humans as a drink or for making butter, cheese, etc.:",
                "https://images-na.ssl-images-amazon.com/images/I/91qJEmfS9qL._SL1500_.jpg",
                "Food",
                12.23,
                154
            )
        )

        for (item in products) {
            product_collection_ref.add(item)
        }

    }

    fun getAllLiveDataProducts(): MutableLiveData<ArrayList<Product>> {
        product_collection_ref.orderBy("productId", Query.Direction.DESCENDING).get()
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    val fireBaseProducts = ArrayList<Product>()
                    for (item in it.result!!) {
                        val product = item.toObject(Product::class.java)
                        product.product_firebase_id = item.id
                        fireBaseProducts.add(product)
                    }
                    allLiveDataProducts.postValue(fireBaseProducts)
                }
            })
        return allLiveDataProducts
    }

    fun getAllPopularityProducts(): MutableLiveData<ArrayList<Product>> {
        product_collection_ref.orderBy("popularityPoint", Query.Direction.DESCENDING).get()
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    val fireBaseProducts = ArrayList<Product>()
                    for (item in it.result!!) {
                        val product = item.toObject(Product::class.java)
                        product.product_firebase_id = item.id
                        fireBaseProducts.add(product)

                    }
                    allLiveDataPopularProducts.postValue(fireBaseProducts)
                }
            })
        return allLiveDataPopularProducts
    }

    fun getAllSuggestedProducts(): MutableLiveData<ArrayList<Product>> {
        product_collection_ref.orderBy("userPoint", Query.Direction.DESCENDING).get()
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    val fireBaseProducts = ArrayList<Product>()
                    for (item in it.result!!) {
                        val product = item.toObject(Product::class.java)
                        fireBaseProducts.add(product)
                    }
                    allLiveDataSuggestedProducts.postValue(fireBaseProducts)
                }
            })
        return allLiveDataSuggestedProducts
    }

    fun getCategories(): ArrayList<String> {
        val result = allLiveDataProducts.value
        val categories = ArrayList<String>()
        for (item in result!!) {
            if (!categories.contains(item.category)) {
                categories.add(item.category)
            }
        }
        return categories
    }

    fun getAllLiveDataResultProductsByCategory(search: String): MutableLiveData<ArrayList<Product>> {
        val result = ArrayList<Product>()
        product_collection_ref.whereEqualTo("category", search).get().addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful) {
                    for (item in it.result!!) {
                        val product = item.toObject(Product::class.java)
                        product.product_firebase_id = item.id
                        result.add(product)
                    }
                }
            })
        val allProduct = allLiveDataProducts.value
        if (allProduct != null) {
            for (item in allProduct) {
                if (item.name.equals(search))
                    result.add(item)
            }
        }
        LiveDataResultProducts.postValue(result)
        return LiveDataResultProducts
    }

    fun getSingleProductLiveData(product_firebase_id: String): MutableLiveData<Product> {
        product_collection_ref.document(product_firebase_id).get().addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful) {
                    singleProductLiveData.postValue(it.result?.toObject(Product::class.java))
                }
            })
        return singleProductLiveData
    }

    fun getAllLiveDataReviewsByProductFireId(product_firebase_id: String): MutableLiveData<ArrayList<Review>> {
        review_collection_ref.whereEqualTo("product_fire_id", product_firebase_id).get()
            .addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        val result = ArrayList<Review>()
                        for (item in it.result!!) {
                            val review = item.toObject(Review::class.java)
                            result.add(review)
                        }
                        LiveDataReviews.postValue(result)
                    }
                })
        return LiveDataReviews
    }

    fun insertingCartItems(cartItem: CartItem) {
        cartItem_collection_ref.add(cartItem)
    }

    fun getAllLiveDataCartItems(): MutableLiveData<ArrayList<CartItem>> {

        cartItem_collection_ref.orderBy("cart_id", Query.Direction.ASCENDING).get()
            .addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        val result = arrayListOf<CartItem>()
                        for (item in it.result!!) {
                            val cartItem = item.toObject(CartItem::class.java)
                            cartItem.cart_fire_id = item.id
                            result.add(cartItem)
                        }
                        allLiveDataCartItems.postValue(result)
                    }
                })

        return allLiveDataCartItems
    }

    fun deletingCartItems(cartItemFireId: String) {
        cartItem_collection_ref.document(cartItemFireId).delete()
    }

    fun getListOfCartItemsAndPrice(): Bundle {
        val cartItems = getAllLiveDataCartItems().value
        var result: String = ""
        for (item in cartItems!!) {
            result += " ${item.name}"
        }
        val bundle = Bundle()
        bundle.putString("name", result)
        var price = 0.0
        for (item in cartItems) {
            price += item.price
        }
        price = Math.round((price * 100.0) / 100.0).toDouble()
        bundle.putDouble("sum", price)
        return bundle
    }

    fun insertOrder(order: Order) {
        order_coollection_ref.add(order)
    }

    fun deleteItemsFromCart() {
        val list = allLiveDataCartItems.value
        for (item in list!!) {
            cartItem_collection_ref.document(item.cart_fire_id).delete()
        }
    }

}