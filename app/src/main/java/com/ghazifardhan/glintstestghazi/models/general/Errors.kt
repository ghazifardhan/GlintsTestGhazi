package com.ghazifardhan.glintstestghazi.models.general

data class Errors(
    val bookDesc: List<String>,
    val bookTitle: List<String>,
    val supplier: List<String>,
    val totalItem: List<String>,
    val transactionDate: List<String>
)