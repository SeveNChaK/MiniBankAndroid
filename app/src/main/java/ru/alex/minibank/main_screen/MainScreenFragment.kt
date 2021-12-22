package ru.alex.minibank.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import ru.alex.minibank.Navigator
import ru.alex.minibank.R
import ru.alex.minibank.login.LoginFragment
import ru.alex.minibank.model.User

class MainScreenFragment : Fragment() {

    private lateinit var navigator: Navigator

    private lateinit var balanceView: TextView
    private lateinit var addMoneyView: TextView
    private lateinit var getMoneyView: TextView
    private lateinit var sendMoneyView: TextView
    private lateinit var historyView: TextView
    private lateinit var signOutView: TextView

    private lateinit var viewModel: MainScreenViewModel
    private var currentActivity: FragmentActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigator =
            Navigator(activity!!.supportFragmentManager) //FIXME nullable activity
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

        val user = arguments?.getSerializable("user") as User?

        balanceView = view.findViewById(R.id.balance)
        if (user != null) {
            balanceView.text = user.balance.toString()
        }
        addMoneyView = view.findViewById(R.id.add_money)
        addMoneyView.setOnClickListener {
            AddMoneyDialog().apply {
                setOnClickListener(object : AddMoneyDialog.OnClickListener {
                    override fun onPositiveClick(amount: Long) {
                        viewModel.addMoney(amount) { user ->
                            if (user != null) {
                                balanceView.text = user.balance.toString()
                                Toast.makeText(currentActivity, "Успешное пополнение счета.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(currentActivity, "Ошибка!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                show(currentActivity!!.supportFragmentManager, "AddMoneyDialog")
            }
        }

        getMoneyView = view.findViewById(R.id.get_money)
        getMoneyView.setOnClickListener {
            GetMoneyDialog().apply {
                setOnClickListener(object : GetMoneyDialog.OnClickListener {
                    override fun onPositiveClick(amount: Long) {
                        viewModel.getMoney(amount) { user ->
                            if (user != null) {
                                balanceView.text = user.balance.toString()
                                Toast.makeText(currentActivity, "Успешное снятие со счета.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(currentActivity, "Ошибка!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                show(currentActivity!!.supportFragmentManager, "GetMoneyDialog")
            }
        }

        sendMoneyView = view.findViewById(R.id.send_money)
        sendMoneyView.setOnClickListener {
            SendMoneyDialog().apply {
                setOnClickListener(object : SendMoneyDialog.OnClickListener {
                    override fun onPositiveClick(targetName: String, amount: Long) {
                        viewModel.sendMoney(targetName, amount) { user ->
                            if (user != null) {
                                balanceView.text = user.balance.toString()
                                Toast.makeText(currentActivity, "Перевод выполнен.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(currentActivity, "Ошибка!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                show(currentActivity!!.supportFragmentManager, "SendMoneyDialog")
            }
        }

        historyView = view.findViewById(R.id.transactions)
        historyView.setOnClickListener {
            viewModel.loadHistory { history ->
                val args = Bundle().apply {
                    putString("history", history)
                }
                HistoryDialog().apply {
                    arguments = args
                    show(currentActivity!!.supportFragmentManager, "HistoryDialog")
                }
            }
        }

        signOutView = view.findViewById(R.id.sign_out)
        signOutView.setOnClickListener {
            viewModel.signOut { isOk ->
                if (isOk) {
                    navigateToLogin()
                } else {
                    Toast.makeText(requireContext(), "Ошибка!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToLogin(args: Bundle? = null) {
        navigator.show(LoginFragment::class.java, false, args = args)
    }
}
