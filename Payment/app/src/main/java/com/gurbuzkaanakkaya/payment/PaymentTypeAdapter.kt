package com.gurbuzkaanakkaya.payment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.gurbuzkaanakkaya.payment.databinding.MainRecyclerBinding

class PaymentTypeAdapter(val paymentTypeList : ArrayList<PaymentType>): RecyclerView.Adapter<PaymentTypeAdapter.PaymentTypeListHolder>() {
    class PaymentTypeListHolder(val binding: MainRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentTypeListHolder {
        val binding = MainRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PaymentTypeListHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentTypeListHolder, position: Int) {
        holder.binding.payTitle.text = paymentTypeList.get(position).payTitle.toString()
        holder.binding.payPeriod.text = paymentTypeList.get(position).payPeriod.toString()
        holder.binding.addPayment.setOnClickListener {
            val intent = Intent(holder.itemView.context,AddPaymentActivity::class.java)
            intent.putExtra("check","true")
            intent.putExtra("id",paymentTypeList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            intent.putExtra("id",paymentTypeList.get(position).id)
            intent.putExtra("paymenttype",paymentTypeList.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  paymentTypeList.size
    }

}