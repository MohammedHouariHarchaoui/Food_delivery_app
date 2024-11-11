package com.example.tdm.utilities

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.tdm.R

fun showPopupMessage(context: Context, message: String) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.popup_layout)

    val messageTextView = dialog.findViewById<TextView>(R.id.messageTextView)
    messageTextView.text = message

    val dismissButton = dialog.findViewById<Button>(R.id.dismissButton)
    dismissButton.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}