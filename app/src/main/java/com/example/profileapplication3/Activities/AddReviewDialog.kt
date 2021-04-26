package com.example.profileapplication3.Activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.profileapplication3.Const.product_firebase_key
import com.example.profileapplication3.Const.product_name
import com.example.profileapplication3.DBService.Utils
import com.example.profileapplication3.Models.Review
import com.example.profileapplication3.R
import java.lang.ClassCastException
import java.text.SimpleDateFormat
import java.util.*

class AddReviewDialog : DialogFragment() {


    interface UpdateReview{
        fun onUpDateReview()
    }

    private lateinit var txtReviewItem: TextView
    private lateinit var edtYourReview: EditText
    private lateinit var edtYourName: EditText
    private lateinit var btnAdd: Button
    private var reviewId = 0
    private var product_firebase_id:String?=null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.add_review_dialog, null, false)
        initViews(view)
        val builder = AlertDialog.Builder(activity).setView(view).create()

        val bundle=arguments
        if(bundle!=null){
            val name_product=bundle.getString("product_name")
            product_firebase_id=bundle.getString(product_firebase_key)
            txtReviewItem.setText("You are reviewing $name_product")
            Log.i(TAG, "onCreateDialog: Incoming values $product_firebase_id")
        }

        btnAdd.setOnClickListener {
            handleCreatingReview(product_firebase_id)
        }

        return builder
    }


    private fun handleCreatingReview(product_firebase_id:String?) {
        if (edtYourName.text.toString().trim().isEmpty()) {
            edtYourName.error = "Please,provide your name"
            edtYourName.requestFocus()
            return
        }
        if (edtYourReview.text.toString().trim().isEmpty()) {
            edtYourReview.error = "Please, provide review"
            edtYourReview.requestFocus()
            return
        }

        val review = Review(
            getReviewId(),
            product_firebase_id!!,
            edtYourName.text.toString(),
            getCurrentDate(),
            edtYourReview.text.toString(),
        )
        Log.i(TAG, "handleCreatingReview: ${review.toString()}")
        Utils.getInstance()!!.review_collection_ref.add(review)
        try{
            val updateReview=activity as UpdateReview
            updateReview.onUpDateReview()
        }catch (e:ClassCastException){
            e.printStackTrace()
        }

        dismiss()

    }


    private fun initViews(view: View?) {
        if (view != null) {
            txtReviewItem = view.findViewById(R.id.txtReviewItem)
            edtYourReview = view.findViewById(R.id.edtYourReview)
            btnAdd = view.findViewById(R.id.btnAdd)
            edtYourName = view.findViewById(R.id.edtName)
        }
    }

    private fun getReviewId(): Int {
        reviewId++
        return reviewId
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val simpelDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return simpelDateFormat.format(calendar.time)
    }


}