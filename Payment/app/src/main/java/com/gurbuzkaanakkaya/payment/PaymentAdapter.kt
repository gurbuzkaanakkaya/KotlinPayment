package com.gurbuzkaanakkaya.payment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.gurbuzkaanakkaya.payment.databinding.DetailsRecyclerBinding

class PaymentAdapter(val paymentList : ArrayList<Payment>) :RecyclerView.Adapter<PaymentAdapter.PaymentListHolder>(){
    class PaymentListHolder(val binding: DetailsRecyclerBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentListHolder {
        val binding = DetailsRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PaymentListHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentListHolder, position: Int) {
        holder.binding.payAmount.text = paymentList.get(position).paymentAmount.toString()
        holder.binding.payDate.text = paymentList.get(position).paymentDate.toString()
        holder.itemView.setOnClickListener {
            val alert = AlertDialog.Builder(holder.itemView.context)
            alert.setTitle("Delete")
            alert.setMessage("Are You Sure?")
            alert.setPositiveButton("Yes"){dialog,which->
                val PaymentTransactions = PaymentTransactions(holder.itemView.context)
                PaymentTransactions.deletePayment(paymentList.get(position).id)
                Toast.makeText(holder.itemView.context,"Deleted", Toast.LENGTH_LONG).show()
                val intent = Intent(holder.itemView.context,MainActivity::class.java)
                holder.itemView.context.startActivity(intent)

            }
            alert.setNegativeButton("No"){dialog,which->
                Toast.makeText(holder.itemView.context,"Not Deleted", Toast.LENGTH_LONG).show()
            }
            alert.show()
        }
        PaymentAdapter(paymentList).notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }
}