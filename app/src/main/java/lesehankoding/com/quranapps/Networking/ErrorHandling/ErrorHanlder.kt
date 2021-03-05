package com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.utils

import android.util.Log
import android.widget.Toast
import com.androidnetworking.error.ANError
import kotlin.Exception

object ErrorHanlder {

    fun errorHandle(
            anError: ANError,
            onError: ((Int, String) -> Unit),
    ) {
        onError(anError.errorCode, anError.errorDetail)
    }
}