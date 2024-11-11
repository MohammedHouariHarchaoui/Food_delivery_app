package com.example.tdm

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openLocation (ctx: Context, latitude:String,longitude:String){
    val data =Uri.parse("geo:$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, data)
    ctx.startActivity(intent)

}