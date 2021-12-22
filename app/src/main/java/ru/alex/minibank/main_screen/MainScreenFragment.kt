package ru.alex.minibank.main_screen

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import ru.alex.minibank.R

class MainScreenFragment : Fragment() {

    private lateinit var balanceView: TextView
    private lateinit var addMoneyView: TextView
    private lateinit var getMoneyView: TextView
    private lateinit var sendMoneyView: TextView
    private lateinit var historyView: TextView

    private lateinit var viewModel: MainScreenViewModel
    private var currentActivity: FragmentActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentActivity = activity
        viewModel = ViewModelProvider.NewInstanceFactory().create(MainScreenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        balanceView = view.findViewById(R.id.balance)
        addMoneyView = view.findViewById(R.id.add_money)
        addMoneyView.setOnClickListener {
            AddMoneyDialog().apply {
                setOnClickListener(object : AddMoneyDialog.OnClickListener {
                    override fun onPositiveClick(amount: Long) {
                        viewModel.addMoney(amount) { isOk ->
                            if (isOk) {
                                Toast.makeText(requireContext(), "Amount - $amount", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                show(currentActivity!!.supportFragmentManager, "AddMoneyDialog")
            }
        }

        getMoneyView = view.findViewById(R.id.get_money)
        getMoneyView.setOnClickListener {

        }

        sendMoneyView = view.findViewById(R.id.send_money)
        sendMoneyView.setOnClickListener {

        }

        historyView = view.findViewById(R.id.transactions)
        historyView.setOnClickListener {

        }
    }
}
