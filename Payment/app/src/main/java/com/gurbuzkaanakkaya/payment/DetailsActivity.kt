package com.gurbuzkaanakkaya.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurbuzkaanakkaya.payment.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {
    private val PaymentTransactions = PaymentTransactions(this)
    private lateinit var paymentAdapter: PaymentAdapter
    private lateinit var paymentList : ArrayList<Payment>
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        val id = intent.getIntExtra("id",0)
        val detailPaymentType = intent.getSerializableExtra("paymenttype") as PaymentType
        showDetailType(detailPaymentType)

        paymentList = PaymentTransactions.getPayment(id)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        paymentAdapter = PaymentAdapter(paymentList)
        binding.recyclerView.adapter = paymentAdapter
        
        binding.updatePaymentType.setOnClickListener {
            var intent = Intent(this,AddPaymentTypeActivity::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",id)
            intent.putExtra("settype",detailPaymentType)
            resultLauncher.launch(intent)
        }
        binding.addPayment.setOnClickListener {
            var intent = Intent(this,AddPaymentActivity::class.java)
            intent.putExtra("id",id)
            resultLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        paymentAdapter.notifyDataSetChanged()
    }

    fun showDetailType(paymentType : PaymentType){
        binding.payTitle.text = (paymentType.payTitle)
        binding.payPeriod.text = (paymentType.payPeriod)
        binding.payDay.text = (paymentType.payDay)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == 1) {
            var dbLastPaymentType = PaymentTransactions.getLastPayment()
            paymentList.add(dbLastPaymentType)
            paymentAdapter.notifyDataSetChanged()}

        else if(it.resultCode == 2){
            var setType = it.data!!.getSerializableExtra("settedType") as PaymentType
            PaymentTransactions.setPaymentType(setType)
            showDetailType(setType)
            finish()
            paymentAdapter.notifyDataSetChanged()
        }
    }
}