package ru.alex.minibank.main_screen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ru.alex.minibank.R
import ru.alex.minibank.main_screen.history.HistoryAdapter
import ru.alex.minibank.model.Payment

class HistoryDialog : DialogFragment() {

    private val gson = Gson()

    interface OnClickListener {
        fun onPositiveClick(amount: Long)
    }

    private var onClickListener: OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_history, null)
        val history = view.findViewById<TextView>(R.id.history_list)
        history.text = arguments?.getString("history") ?: "Empty"

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle("История операций")
            .setNegativeButton("Закрыть") { _, _ -> dismiss() }
            .create()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val history = view.findViewById<RecyclerView>(R.id.history_list)
//        history.adapter = HistoryAdapter(gson.fromJson(arguments?.getString("history") ?: "Empty", List::class.java) as List<Payment>)
//        history.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        history.adapter?.notifyDataSetChanged()
//    }

    fun setOnClickListener(listener: OnClickListener) {
        this.onClickListener = listener
    }
}