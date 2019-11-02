package com.ghazifardhan.glintstestghazi.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login {

    @SerializedName("data")
    @Expose
    var data: Data? = null
        get() = field
        set(value) {
            field = value
        }
}