package com.gurbuzkaanakkaya.payment

import java.io.Serializable

class PaymentType : Serializable {
    var id : Int? = null
    var payDay : String? = null
    var payTitle : String? = null
    var payPeriod : String? = null
}