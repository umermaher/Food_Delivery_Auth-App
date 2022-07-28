package com.example.mealmonkey.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class OtpReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

    }

    interface OtpReceiverListener{
        fun onOtpSuccess(otp:String)

    }
}