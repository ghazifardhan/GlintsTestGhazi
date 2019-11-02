package com.ghazifardhan.glintstestghazi.api

import com.ghazifardhan.glintstestghazi.models.books.Book
import com.ghazifardhan.glintstestghazi.models.books.BookList
import com.ghazifardhan.glintstestghazi.models.login.Login
import com.google.gson.JsonElement
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @GET("/auth/jwt/token")
    fun login(
        @Header("Authorization") authKey: String
    ): Observable<Response<Login>>

    @FormUrlEncoded
    @POST("/transactions")
    fun doCreateBook(
        @Header("Authorization") authKey: String,
        @Field("book_title") title: String,
        @Field("book_desc") desc: String,
        @Field("total_item") totalItem: String,
        @Field("supplier") supplier: String,
        @Field("transaction_date") date: String
    ): Observable<Response<Book>>

    @GET("/transactions")
    fun listBooks(
        @Header("Authorization") authKey: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Single<BookList>
}