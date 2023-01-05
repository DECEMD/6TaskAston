package com.example.sixthtaskaston.model

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sixthtaskaston.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

object DataBaseRequests {
    fun getNameListData(context: Context): List<String>{
        return context.resources.getStringArray(R.array.names).toList()
    }

    fun getSurnameListData(context: Context): List<String>{
        return context.resources.getStringArray(R.array.surnames).toList()
    }

    fun setImage(person: Person, imageView: ImageView, progressBar: ProgressBar, context: Context){
        Picasso.get()
            .load("https://picsum.photos/id/${person.contactId}/200/200")
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(context,
                        "Something went wrong while downloading images",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
    const val PERSON_DATA_BY_ID = "PERSON_DATA_BY_ID"
    const val DATA_FROM_SECOND_FRAGMENT = "DATA_FROM_SECOND_FRAGMENT"
}