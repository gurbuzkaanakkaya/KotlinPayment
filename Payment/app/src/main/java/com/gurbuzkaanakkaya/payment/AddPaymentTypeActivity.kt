package com.gurbuzkaanakkaya.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.R
import com.gurbuzkaanakkaya.payment.databinding.ActivityAddPaymentTypeBinding

class AddPaymentTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentTypeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var spinner : ArrayList<String> = arrayListOf("Yearly","Monthly", "Weekly")
        val adapter : ArrayAdapter<String> = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinner)
        binding.payPeriod.adapter = adapter

    }
    override fun onResume() {
        super.onResume()
        val intent = intent
        val info = intent.getStringExtra("info")
        if (info.equals("old")) {
            val getPaymentType = intent.getSerializableExtra("settype") as PaymentType
            showTypeForSet(getPaymentType)
            binding.addPaymentType.setOnClickListener {
                var set = setType(getPaymentType)
                var setintent = intent.putExtra("settedType",set)
                setResult(2,setintent)
                finish()
            }
        }else{
            binding.addPaymentType.setOnClickListener{
                var paymentTransactions= PaymentTransactions(this)
                var paymentType = createPaymentType()
                paymentTransactions.addPaymentType(paymentType)
                setResult(1)
                finish()
            }
        }
    }


    fun createPaymentType(): PaymentType {
        var paymentTitle = binding.payTitle.text.toString()
        var payPeriod = binding.payPeriod.selectedItem.toString()
        var payDay = binding.payDay.text.toString()

        var paymentType = PaymentType()
        paymentType.payTitle = paymentTitle
        paymentType.payPeriod = payPeriod
        paymentType.payDay = payDay
        return paymentType
    }
    fun showTypeForSet(paymentType: PaymentType){
        binding.payTitle.setText(paymentType.payTitle)
        binding.payDay.setText(paymentType.payDay)
    }
    fun setType(paymentType: PaymentType) : PaymentType{
        paymentType.payTitle = binding.payTitle.text.toString()
        paymentType.payDay = binding.payDay.text.toString()
        paymentType.payPeriod = binding.payPeriod.selectedItem.toString()
        return  paymentType
    }
}