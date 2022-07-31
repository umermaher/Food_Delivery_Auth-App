package com.example.mealmonkey.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Pattern

class OtpReceiver(private val otpReceiverListener:OtpReceiverListener?=null):BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        if(intent!=null)
            if(SmsRetriever.SMS_RETRIEVED_ACTION == intent.action){
                val bundle=intent.extras

                if(bundle!=null){
                    val status=bundle.get(SmsRetriever.EXTRA_STATUS) as Status

                    if(status!=null){

                        when(status.statusCode){
                            CommonStatusCodes.SUCCESS->{
                                val msg=bundle.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                                val pattern= Pattern.compile("\\d{6}")
                                val matcher=pattern.matcher(msg)
                                if(matcher.find()){
                                    val myOpt=matcher.group(0)
                                    myOpt?.let { otpReceiverListener?.onOtpSuccess(it) }
                                }
                            }
                            CommonStatusCodes.TIMEOUT -> otpReceiverListener?.onOtpTimeOut()
                        }
                    }
                }
            }
    }

    interface OtpReceiverListener{
        fun onOtpSuccess(otp:String)
        fun onOtpTimeOut()
    }
}