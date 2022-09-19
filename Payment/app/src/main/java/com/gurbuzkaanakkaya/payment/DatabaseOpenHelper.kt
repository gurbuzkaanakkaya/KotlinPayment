package com.gurbuzkaanakkaya.payment

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper (context : Context,name : String, factory : SQLiteDatabase.CursorFactory?,version : Int) : SQLiteOpenHelper(context,name,factory,version){
    override fun onCreate(SQLite: SQLiteDatabase?) {
        var query = "CREATE TABLE PaymentType(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,payTitle TEXT,payPeriod TEXT,payDay TEXT)"
        SQLite!!.execSQL(query)
        var query1 = "CREATE TABLE Payment(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,paymentTypeId INTEGER NOT NULL,paymentAmount TEXT,paymentDate TEXT)"
        SQLite!!.execSQL(query1)
    }

    override fun onUpgrade(SQLite: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion == 1 ){

        }

    }
}