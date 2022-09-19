package com.gurbuzkaanakkaya.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.gurbuzkaanakkaya.payment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val PaymentTransactions = PaymentTransactions(this)
    private lateinit var paymentTypeAdapter: PaymentTypeAdapter
    private lateinit var paymentTypeList : ArrayList<PaymentType>
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
    override fun onResume() {
        super.onResume()
        paymentTypeList = PaymentTransactions.getPaymentType()
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        paymentTypeAdapter = PaymentTypeAdapter(paymentTypeList)
        binding.recyclerView2.adapter = paymentTypeAdapter


        binding.add.setOnClickListener{
            var intent = Intent(this,AddPaymentTypeActivity::class.java)
            resultLauncher.launch(intent)
            paymentTypeAdapter.notifyDataSetChanged()
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == 1) {
            var dbLastPaymentType = PaymentTransactions.getLastPaymentType()
            paymentTypeList.add(dbLastPaymentType)
            paymentTypeAdapter.notifyDataSetChanged()}

        else{}
    }
}