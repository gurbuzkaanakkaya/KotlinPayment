package com.gurbuzkaanakkaya.payment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class PaymentTransactions(context : Context) {
    var PaymentDatabase : SQLiteDatabase? = null
    var DatabaseOpenHelper : DatabaseOpenHelper
    init {
        DatabaseOpenHelper = DatabaseOpenHelper(context,"PaymentDatabase",null,1)
    }
    fun openDatabase(){
        PaymentDatabase = DatabaseOpenHelper.writableDatabase
    }
    fun closeDatabase() {
        if (PaymentDatabase != null && PaymentDatabase!!.isOpen) {
            PaymentDatabase!!.close()
        }
    }

    fun addPayment(payment: Payment) : Long{
        val cv = ContentValues()
        cv.put("paymentAmount","${payment.paymentAmount}" + " $")
        cv.put("paymentDate",payment.paymentDate)
        cv.put("paymentTypeId",payment.paymentTypeId)
        openDatabase()
        val addPayment = PaymentDatabase!!.insert("Payment",null,cv)
        closeDatabase()

        return  addPayment
    }
    fun setPayment(payment: Payment){
        val cv = ContentValues()
        cv.put("paymentAmount",payment.paymentAmount)
        cv.put("paymentDate",payment.paymentDate)
        openDatabase()
        PaymentDatabase!!.update("Payment",cv,"Id = ?", arrayOf(payment.id.toString()))
    }
    fun deletePayment(id : Int?){
        openDatabase()
        PaymentDatabase!!.delete("Payment","Id = ?", arrayOf(id.toString()))
        closeDatabase()
    }
    private fun getAllPaymentForPaymentType(): Cursor {
        var query = "SELECT * FROM Payment"
        return PaymentDatabase!!.rawQuery(query,null)
    }

    @SuppressLint("Range")
    fun getPayment(id : Int):ArrayList<Payment>{
        val payments : ArrayList<Payment> = ArrayList()
        var payment : Payment
        openDatabase()
        val cursor : Cursor = listPaymentForTypeId(id)
        if (cursor.moveToFirst()){
            do{
                payment = Payment()
                payment.id = cursor.getInt(0)
                payment.paymentAmount = cursor.getString(cursor.getColumnIndex("paymentAmount"))
                payment.paymentDate = cursor.getString(cursor.getColumnIndex("paymentDate"))
                payments.add(payment)

            }
                while (cursor.moveToNext())
        }
        closeDatabase()
        return payments

    }
    @SuppressLint("Range")
    fun getLastPayment() : Payment{
        val payments : ArrayList<Payment> = ArrayList()
        var payment : Payment
        openDatabase()
        val cursor : Cursor = getAllPaymentForPaymentType()
        if (cursor.moveToLast()){
            do{
                payment = Payment()
                payment.id = cursor.getInt(0)
                payment.paymentAmount = cursor.getString(cursor.getColumnIndex("paymentAmount"))
                payment.paymentDate = cursor.getString(cursor.getColumnIndex("paymentDate"))
                payments.add(payment)
            }
            while (cursor.moveToNext())
        }
        closeDatabase()
        return payments.get(0)
    }

    fun addPaymentType(payment : PaymentType) : Long{

        val cv = ContentValues()
        cv.put("Id",payment.id)
        cv.put("payTitle",payment.payTitle)
        cv.put("payPeriod",payment.payPeriod)
        cv.put("payDay","${payment.payDay}"+ ".Day")
        openDatabase()
        val addPaymentType = PaymentDatabase!!.insert("PaymentType",null,cv)
        closeDatabase()

        return  addPaymentType
    }
    private fun getAllPaymentType() : Cursor{
        val query = "SELECT * FROM PaymentType"
        return PaymentDatabase!!.rawQuery(query,null)
    }

    @SuppressLint("Range")
    fun getPaymentType() : ArrayList<PaymentType>{
        val paymentTypes : ArrayList<PaymentType> = ArrayList()
        var paymentType : PaymentType
        openDatabase()
        val cursor : Cursor = getAllPaymentType()
        if (cursor.moveToFirst()){
            do{
                paymentType = PaymentType()
                paymentType.id = cursor.getInt(0)
                paymentType.payTitle = cursor.getString(cursor.getColumnIndex("payTitle"))
                paymentType.payPeriod = cursor.getString(cursor.getColumnIndex("payPeriod"))
                paymentType.payDay = cursor.getString(cursor.getColumnIndex("payDay"))
                paymentTypes.add(paymentType)

            }
                while (cursor.moveToNext())
        }
        closeDatabase()
        return paymentTypes
    }

    @SuppressLint("Range")
    fun getLastPaymentType() : PaymentType{
        val paymentTypes : ArrayList<PaymentType> = ArrayList()
        var paymentType : PaymentType
        openDatabase()
        val cursor : Cursor = getAllPaymentType()
        if (cursor.moveToLast()){
            do{
                paymentType = PaymentType()
                paymentType.id = cursor.getInt(0)
                paymentType.payTitle = cursor.getString(cursor.getColumnIndex("payTitle"))
                paymentType.payPeriod = cursor.getString(cursor.getColumnIndex("payPeriod"))
                paymentType.payDay = cursor.getString(cursor.getColumnIndex("payDay"))
                paymentTypes.add(paymentType)
            }
            while (cursor.moveToNext())
        }
        closeDatabase()
        return paymentTypes.get(0)
    }
    fun setPaymentType(payment: PaymentType){
        val cv = ContentValues()
        cv.put("payPeriod",payment.payPeriod)
        cv.put("payTitle",payment.payTitle)
        cv.put("payDay",payment.payDay)
        openDatabase()
        PaymentDatabase!!.update("PaymentType",cv,"Id = ?", arrayOf(payment.id.toString()))
    }
    private fun listPaymentForTypeId(id:Int) : Cursor{
        val query = "SELECT * FROM Payment WHERE Payment.paymentTypeId = ?"
        return PaymentDatabase!!.rawQuery(query, arrayOf(id.toString()))
    }


}