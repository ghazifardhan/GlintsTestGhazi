package com.ghazifardhan.glintstestghazi.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("jwt")
    @Expose
    var jwt: String = ""
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("token_type")
    @Expose
    var tokenType: String = ""
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("expires_in")
    @Expose
    var expiresIn: String = ""
        get() = field
        set(value) {
            field = value
        }
}