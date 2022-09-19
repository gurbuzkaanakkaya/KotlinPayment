package com.gurbuzkaanakkaya.payment

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gurbuzkaanakkaya.payment.databinding.ActivityAddPaymentBinding

class AddPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.selectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dcpopup = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                view,year,monthofyear,dayOfMonth ->
                val months = monthofyear+1
                binding.selectDate.setText("$dayOfMonth/$months/$year")

            },year,month,day)
            dcpopup.datePicker.maxDate = calendar.timeInMillis
            dcpopup.show()
        }

        val intent = intent
        var check = intent.getStringExtra("check")
        if (check.equals("true")){
            binding.addPayment.setOnClickListener {
                var paymentTransactions= PaymentTransactions(this)
                var payment = createPayment()
                paymentTransactions.addPayment(payment)
                finish()
            }
        }else{
            binding.addPayment.setOnClickListener {
                var paymentTransactions= PaymentTransactions(this)
                var payment = createPayment()
                paymentTransactions.addPayment(payment)
                setResult(1)
                finish()
            }
        }


    }
    fun createPayment() : Payment{
        var getid = intent.getIntExtra("id",0)
        var amount = binding.enterAmount.text.toString()
        var date = binding.selectDate.getText().toString()
        var id = getid

        var payment = Payment()
        payment.paymentAmount = amount
        payment.paymentDate = date
        payment.paymentTypeId = id
        return payment
    }

}