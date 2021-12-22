package ru.alex.minibank.main_screen.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alex.minibank.R
import ru.alex.minibank.model.Payment

class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val sourceView = itemView.findViewById<TextView>(R.id.source)
    private val targetView = itemView.findViewById<TextView>(R.id.target)
    private val amountView = itemView.findViewById<TextView>(R.id.amount)

    fun bind(payment: Payment) {
        sourceView.text = payment.sourceName
        targetView.text = payment.targetName
        amountView.text = payment.amount.toString()
    }
}