package com.example.mealmonkey.utils

fun trimName(name: String): String {
    var value = ""
    for(i in name.indices){
        if(name[i]==' ') break
        value+=name[i]
    }
    return value
}