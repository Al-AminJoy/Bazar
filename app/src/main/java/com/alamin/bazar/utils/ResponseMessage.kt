package com.alamin.bazar.utils

import org.json.JSONObject

object ResponseMessage  {
    fun getErrorMessage(response: String, key: String): String{
        val jsonObject = JSONObject(response);
        return jsonObject.getString(key);
    }
}