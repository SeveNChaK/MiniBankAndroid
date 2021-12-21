package ru.alex.minibank.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.alex.minibank.*
import ru.alex.minibank.main_screen.MainScreenFragment

class LoginFragment : Fragment() {

    private lateinit var navigator: Navigator

    private lateinit var viewModel: LoginViewModel

    private lateinit var progressView: View
    private lateinit var formLoginContainer: View
    private lateinit var inputLoginView: EditText
    private lateinit var inputPasswordView: EditText
    private lateinit var inputConfirmPasswordView: EditText
    private lateinit var changeFormView: TextView
    private lateinit var submitButton: TextView

    private var isLoginForm = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigator =
            Navigator(activity!!.supportFragmentManager) //FIXME nullable activity
        viewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressView = view.findViewById(R.id.login_progress)
        formLoginContainer = view.findViewById(R.id.form_login_container)
        inputLoginView = view.findViewById(R.id.input_login)
        inputPasswordView = view.findViewById(R.id.input_password)
        inputConfirmPasswordView = view.findViewById(R.id.input_confirm_password)
        changeFormView = view.findViewById(R.id.change_form)
        submitButton = view.findViewById(R.id.submit_button)

        progressView.visible()
        formLoginContainer.gone()

        changeForm()

        changeFormView.setOnClickListener {
            isLoginForm = !isLoginForm
            changeForm()
        }

        submitButton.setOnClickListener {
            progressView.visible()
            formLoginContainer.gone()

            val login = inputLoginView.text.toString()
            val password = inputPasswordView.text.toString()

            if (isLoginForm) {
                viewModel.signIn(login, password) { isOk ->
                    if (isOk) {
                        navigateToMain()
                    } else {
                        progressView.gone()
                        formLoginContainer.visible()
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val confirmPassword = inputConfirmPasswordView.text.toString()
                viewModel.signUp(login, password, confirmPassword) { isOk ->
                    if (isOk) {
                        navigateToMain()
                    } else {
                        progressView.gone()
                        formLoginContainer.visible()
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.tryToMain { isOk ->
            if (isOk) {
                navigateToMain()
            } else {
                progressView.gone()
                formLoginContainer.visible()
            }
        }
    }

    private fun changeForm() {
        if (isLoginForm) {
            inputConfirmPasswordView.gone()
            changeFormView.setText(R.string.create_account)
            submitButton.setText(R.string.sign_in)
        } else {
            inputConfirmPasswordView.visible()
            changeFormView.setText(R.string.already_exist_account)
            submitButton.setText(R.string.sign_up)
        }
    }

    private fun navigateToMain() {
        navigator.show(MainScreenFragment::class.java, false)
    }
}
