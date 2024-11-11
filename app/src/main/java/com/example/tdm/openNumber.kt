package com.example.tdm

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openNumber (ctx: Context, phone:String){
    val data =Uri.parse("tel:$phone")
    val intent = Intent(Intent.ACTION_VIEW, data)
    ctx.startActivity(intent)

}