package com.example.tdm

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openLink (ctx: Context, link:String){
    val data =Uri.parse(link)
    val intent = Intent(Intent.ACTION_VIEW, data)
    ctx.startActivity(intent)

}