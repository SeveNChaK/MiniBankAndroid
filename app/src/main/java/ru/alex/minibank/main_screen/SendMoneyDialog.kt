package ru.alex.minibank.main_screen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.alex.minibank.R

class SendMoneyDialog : DialogFragment() {
    interface OnClickListener {
        fun onPositiveClick(targetName: String, amount: Long)
    }

    private var onClickListener: OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_send_money, null)
        val inputAmount = view.findViewById<EditText>(R.id.input_amount)
        val inputName = view.findViewById<EditText>(R.id.input_name)
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle("Перевести")
            .setPositiveButton("Перевести") { _, _ ->
                val name = inputName.text.toString()
                val amount = inputAmount.text.toString().toLong()
                onClickListener?.onPositiveClick(name, amount)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> dismiss() }
            .create()
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.onClickListener = listener
    }
}
