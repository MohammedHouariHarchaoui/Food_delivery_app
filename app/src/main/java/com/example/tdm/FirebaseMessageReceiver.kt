package com.example.tdm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirebaseMessageReceiver : FirebaseMessagingService() {
//    // var userRepo= UsersRespository()
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//
//        // Handle incoming FCM messages here
//
//
//        showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
//    }
//
//    private fun showNotification(title: String?, message: String?) {
//        val channelId = "default_channel_id"
//        val channelName = "Default Channel"
//        val notificationId = 1
//        println("data")
//        println(message)
//        val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.logoblack)
//
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.logowhite) // Set a valid small icon
//            .setContentTitle(title)
//            .setContentText(message)
//            .setLargeIcon(largeIconBitmap)
//            .setAutoCancel(true)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Create the notification channel (required for Android 8.0 and above)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//            channel.enableLights(true)
//            channel.lightColor = Color.BLUE
//            channel.enableVibration(true)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        notificationManager.notify(notificationId, notificationBuilder.build())
//    }
//
//
//    override fun onCreate() {
//        super.onCreate()
//
//    }
//
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        println("this should work")
//        if(checkUserAuth()) {
//            CoroutineScope(Dispatchers.IO).launch{
//                val data = tokenData(client = getUserToken() , token = token)
//                println("new token")
//                println(data)
//                val resutl = clientServiceAPI.createClientServiceAPI().addtokenNotif(data)
//                delay(500)
//            }
//        }
//
//
//    }
//
//
//    fun getUserToken():String {//recuperer le user_id sauvegardé
//        val pref = getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
//        val token  = pref.getString("token_food_delivry","")
//        return token.toString()
//    }
//
//    fun checkUserAuth():Boolean {//verifier si le user est authentifié
//        val pref = getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
//        return pref.contains("connected")
//    }
}