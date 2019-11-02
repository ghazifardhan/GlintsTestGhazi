package com.ghazifardhan.glintstestghazi.models.realm

import io.realm.RealmObject

open class JwtTokenKotlin(
    var id: Int? = null,
    var jwt: String? = null
): RealmObject(){}